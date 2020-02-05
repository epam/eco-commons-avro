/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.epam.eco.commons.avro.avpath;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.apache.avro.generic.GenericRecord;
import org.apache.commons.lang3.Validate;

import com.epam.eco.commons.avro.AvroUtils;
import com.epam.eco.commons.avro.traversal.SchemaTraverseListener;
import com.epam.eco.commons.avro.traversal.SchemaTraverser;

/**
 * @author Andrei_Tytsik
 */
public class AvPath {

    public static final AvPath INSTANCE = new AvPath();

    private static final PathTemplate EMPTY_PATH_TEMPLATE = new PathTemplate("", null);

    private final ConcurrentMap<String, Expression<?>> expsCache = new ConcurrentHashMap<>();
    private final ConcurrentMap<Schema, List<PathTemplate>> pathTemplatesCache = new ConcurrentHashMap<>();

    public <T> List<T> select(Object object, String path, Class<T> type) {
        Validate.notNull(object, "Object is null");
        Validate.notBlank(path, "Path is blank");
        Validate.notNull(type, "Type is null");

        List<EvaluationResult> output = eval(object, path);
        return toResult(output, type);
    }

    public <T> T selectOne(Object object, String path, Class<T> type) {
        List<T> result = select(object, path, type);
        if (result.size() > 1) {
            throw new RuntimeException(
                    String.format(
                            "More than one value found at path '%s'. Object: %s",
                            path, object));
        }
        return result.isEmpty() ? null : result.get(0);
    }

    public int update(Object object, String path, Object value) {
        return update(object, path, oldValue -> value);
    }

    public int update(Object object, String path, Function<Object, Object> update) {
        Validate.notNull(object, "Object is null");
        Validate.notBlank(path, "Path is blank");
        Validate.notNull(update, "Update function is null");

        List<EvaluationResult> output = eval(object, path);
        return operateUpdate(output, update);
    }

    public boolean updateOne(Object object, String path, Object value) {
        return updateOne(object, path, oldValue -> value);
    }

    public boolean updateOne(Object object, String path, Function<Object, Object> update) {
        Validate.notNull(object, "Object is null");
        Validate.notBlank(path, "Path is blank");
        Validate.notNull(update, "Update function is null");

        List<EvaluationResult> output = eval(object, path);
        if (output.size() > 1) {
            throw new RuntimeException(
                    String.format(
                            "More than one value found at path '%s'. Object: %s",
                            path, object));
        }
        return operateUpdate(output, update) == 1;
    }

    public List<PathTemplate> getPathTemplates(Schema schema) {
        Validate.notNull(schema, "Schema is null");

        return getCachedPathTemplatesOrBuildIfAbsent(schema);
    }

    private List<PathTemplate> getCachedPathTemplatesOrBuildIfAbsent(Schema schema) {
        return pathTemplatesCache.computeIfAbsent(schema, key -> bildPathTemplates(schema));
    }

    private List<PathTemplate> bildPathTemplates(Schema schema) {
        List<PathTemplate> finalPathTemplates = new ArrayList<>();
        Map<String, List<PathTemplate>> templatesBySchemaPaths = new HashMap<>();
        new SchemaTraverser(
                new SchemaTraverseListener() {
                    @Override
                    public void onSchemaField(String path, Schema parentSchema, Field field) {
                        int sepIdx = path.lastIndexOf('.');
                        String prefixPath = sepIdx > 0 ? path.substring(0, sepIdx) : null;

                        List<PathTemplate> prefixPathTemplates =
                                prefixPath != null ?
                                templatesBySchemaPaths.get(prefixPath) :
                                Collections.singletonList(EMPTY_PATH_TEMPLATE);

                        List<PathTemplate> fieldPathTemplates =
                                buildPathTemplatesForPath(field.name(), field.schema());

                        List<PathTemplate> pathTemplates = new ArrayList<>();
                        for (PathTemplate prefixPathTemplate : prefixPathTemplates) {
                            for (PathTemplate fieldPathTemplate : fieldPathTemplates) {
                                PathTemplate pathTemplate = prefixPathTemplate.join(fieldPathTemplate);
                                if (pathTemplate.getSchema().getType() == Type.RECORD) {
                                    pathTemplates.add(pathTemplate);
                                } else {
                                    finalPathTemplates.add(pathTemplate);
                                }
                            }
                        }
                        templatesBySchemaPaths.put(path, pathTemplates);
                    }
                    @Override
                    public void onSchema(String path, Schema parentSchema, Schema schema) {
                    }
                }).walk(schema);

        return Stream.concat(
                    finalPathTemplates.stream(),
                    templatesBySchemaPaths.values().stream().
                            flatMap(Collection::stream)).
                sorted().
                collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        Collections::unmodifiableList));
    }

    private List<PathTemplate> buildPathTemplatesForPath(String path, Schema schema) {
        List<PathTemplate> templates = new ArrayList<>();
        if (schema.getType() == Type.NULL) {
            // ignore
        } else if (schema.getType() == Type.UNION) {
            for (Schema unionType : schema.getTypes()) {
                templates.addAll(buildPathTemplatesForPath(path, unionType));
            }
        } else {
            if (schema.getType() == Type.ARRAY) {
                templates.add(
                        PathTemplate.with(
                                "/" + path + PathUtils.ELEMENT_SELECTOR_MATCH_ALL,
                                schema.getElementType()));
            } else if (schema.getType() == Type.MAP) {
                templates.add(
                        PathTemplate.with(
                                "/" + path + PathUtils.ELEMENT_SELECTOR_MATCH_ALL,
                                schema.getValueType()));
            }
            templates.add(
                    PathTemplate.with(
                            "/" + path,
                            schema));
        }
        return templates;
    }

    @SuppressWarnings("unchecked")
    private List<EvaluationResult> eval(Object object, String path) {
        Expression<?> exp = getCachedPathExpOrBuildIfAbsent(path);
        return ((Expression<Object>)exp).eval(object);
    }

    private int operateUpdate(List<EvaluationResult> output, Function<Object, Object> update) {
        int updateCount = 0;
        for (EvaluationResult evalResult : output) {
            boolean updated;
            if (evalResult instanceof GetRecordFieldResult) {
                updated = operateUpdateForRecordField((GetRecordFieldResult)evalResult, update);
            } else if (evalResult instanceof SelectArrayElementResult) {
                updated = operateUpdateForArrayElement((SelectArrayElementResult)evalResult, update);
            } else if (evalResult instanceof SelectMapValueResult) {
                updated = operateUpdateForMapValue((SelectMapValueResult)evalResult, update);
            } else {
                throw new RuntimeException(
                        String.format(
                                "Can't operate update for container object = %s",
                                evalResult.getContainer()));
            }
            updateCount = updated ? updateCount + 1 : updateCount;
        }
        return updateCount;
    }

    private boolean operateUpdateForRecordField(
            GetRecordFieldResult evalResult,
            Function<Object, Object> update) {
        if (!evalResult.isValidSchemaField()) {
            return false;
        }

        GenericRecord record = evalResult.getContainerRecord();
        String fieldName = evalResult.getFieldName();
        Object oldValue = AvroUtils.avroPrimitiveToJava(evalResult.getValue());
        Object newValue = update.apply(oldValue);
        record.put(fieldName, newValue);
        return true;
    }

    @SuppressWarnings("unchecked")
    private boolean operateUpdateForArrayElement(
            SelectArrayElementResult evalResult,
            Function<Object, Object> update) {
        List<Object> array = (List<Object>)evalResult.getContainerArray();
        int index = evalResult.getIndex();
        Object oldElement = AvroUtils.avroPrimitiveToJava(evalResult.getValue());
        Object newElement = update.apply(oldElement);
        array.set(index, newElement);
        return true;
    }

    @SuppressWarnings("unchecked")
    private boolean operateUpdateForMapValue(
            SelectMapValueResult evalResult,
            Function<Object, Object> update) {
        Map<Object, Object> map = (Map<Object, Object>)evalResult.getContainerMap();
        Object key = AvroUtils.javaPrimitiveToAvro(evalResult.getKey());
        Object oldValue = AvroUtils.avroPrimitiveToJava(evalResult.getValue());
        Object newValue = update.apply(oldValue);
        map.put(key, newValue);
        return true;
    }

    private <T> List<T> toResult(List<EvaluationResult> output, Class<T> type) {
        return output.stream().
                map(er -> type.cast(AvroUtils.avroPrimitiveToJava(er.getValue()))).
                collect(Collectors.toList());
    }

    private Expression<?> getCachedPathExpOrBuildIfAbsent(String path) {
        return expsCache.computeIfAbsent(path, key -> buildPathExp(path));
    }

    private Expression<?> buildPathExp(String path) {
        return new PathParser(path).parse();
    }

}
