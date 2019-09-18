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
package com.epam.eco.commons.avro.modification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.apache.commons.lang3.Validate;

import com.epam.eco.commons.avro.AvroConstants;
import com.epam.eco.commons.avro.AvroUtils;
import com.epam.eco.commons.avro.traversal.GenericSchemaTraverseListener;
import com.epam.eco.commons.avro.traversal.GenericSchemaTraverser;

/**
 * @author Andrei_Tytsik
 */
public class AddSchemaFieldToPath implements SchemaModification {

    private final Field field;
    private final String path;

    public AddSchemaFieldToPath(Field field, String path) {
        Validate.notNull(field, "Field is null");

        this.field = field;
        this.path = path;
    }

    public Field getField() {
        return field;
    }

    public String getPath() {
        return path;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void applyToGeneric(Map<String, Object> schemaMap) {
        List<Map<String, Object>> typesToAddFieldTo = new ArrayList<>();

        new GenericSchemaTraverser(new GenericSchemaTraverseListener() {
            @Override
            public void onSchemaField(
                    String path,
                    Map<String, Object> parentSchema,
                    Map<String, Object> field) {
                // do nothing
            }
            @Override
            public void onSchema(String path, Object parentSchema, Object schema) {
                if (
                        AvroUtils.typeOfGenericSchema(schema) == Type.RECORD &&
                        Objects.equals(AddSchemaFieldToPath.this.path, path)) {
                    typesToAddFieldTo.add((Map<String, Object>)schema);
                }
            }
        }).walk(schemaMap, path);

        if (typesToAddFieldTo.isEmpty()) {
            throw new RuntimeException(
                    String.format(
                            "Field '%s' can't be added to path '%s': no corresponding parent type found",
                            field.name(), path));
        }

        Map<String, Object> genericField = AvroUtils.fieldToGeneric(field);

        for (Map<String, Object> typeToAddFieldTo : typesToAddFieldTo) {
            List<Map<String, Object>> fields =
                    (List<Map<String, Object>>) typeToAddFieldTo.get(AvroConstants.SCHEMA_KEY_FIELDS);

            Set<String> fieldNames = fields.stream().
                    map(m -> (String) m.get(AvroConstants.SCHEMA_KEY_FIELD_NAME)).
                    collect(Collectors.toSet());
            if (fieldNames.contains(field.name())) {
                throw new RuntimeException(
                        String.format(
                                "Field '%s' can't be added to path '%s': name already exists",
                                field.name(), path));
            }

            fields.add(genericField);
        }
    }

    @Override
    public String toString() {
        return String.format("%s: {field: %s, path: %s}", this.getClass().getSimpleName(), field, path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, path);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        AddSchemaFieldToPath that = (AddSchemaFieldToPath)obj;
        return
                Objects.equals(this.field, that.field) &&
                Objects.equals(this.path, that.path);
    }

    public static AddSchemaFieldToPath with(Field field, String path) {
        return new AddSchemaFieldToPath(field, path);
    }

}
