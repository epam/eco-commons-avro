/*
 * Copyright 2020 EPAM Systems
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
package com.epam.eco.commons.avro.traversal;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.avro.Schema.Type;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.epam.eco.commons.avro.AvroConstants;
import com.epam.eco.commons.avro.AvroUtils;
import com.epam.eco.commons.avro.Path;

/**
 * @author Andrei_Tytsik
 */
public class GenericSchemaTraverser {

    // Map of handled named types. Used as cache. Helps to avoid circular dependencies.
    private final static ThreadLocal<Map<String, Object>> NAMED_TYPES_HANDLED = ThreadLocal.withInitial(LinkedHashMap::new);

    private final static ThreadLocal<Stack<String>> NAMESPACE_CONTEXT = ThreadLocal.withInitial(Stack::new);

    private final static ThreadLocal<Path> CURRENT_PATH = ThreadLocal.withInitial(() -> new Path(null));

    private final GenericSchemaTraverseListener listener;

    public GenericSchemaTraverser(GenericSchemaTraverseListener listener) {
        Validate.notNull(listener, "Listener is null");

        this.listener = listener;
    }

    public void walk(Object schema) {
        walk(schema, null);
    }

    public void walk(Object schema, String desiredPath) {
        Validate.notNull(schema, "Schema is null");

        try {
            doWalk(
                    desiredPath != null ? new Path(desiredPath) : null,
                    null,
                    schema);
        } finally {
            NAMED_TYPES_HANDLED.remove();
            NAMESPACE_CONTEXT.remove();
            CURRENT_PATH.remove();
        }
    }

    @SuppressWarnings("unchecked")
    private void doWalk(Path desiredPath, Object parentSchema, Object schema) {
        if (!isCurrentPathTraversable(desiredPath)) {
            return;
        }

        Type type = AvroUtils.typeOfGenericSchemaOrElseNullIfUnknown(schema);
        if (type == null && schema instanceof String) {
            schema = getHandledNamedTypeFor((String)schema);
            type = AvroUtils.typeOfGenericSchema(schema);
        }

        listener.onSchema(getCurrentPathString(), parentSchema, schema);

        if (Type.ARRAY == type) {
            doWalk(
                    desiredPath,
                    schema,
                    ((Map<String, Object>)schema).get(AvroConstants.SCHEMA_KEY_ARRAY_ITEMS));
        } else if (Type.MAP == type) {
            doWalk(
                    desiredPath,
                    schema,
                    ((Map<String, Object>)schema).get(AvroConstants.SCHEMA_KEY_MAP_VALUES));
        } else if (
                Type.ENUM == type ||
                Type.FIXED == type
                ) {
            storeNamedTypeAsHandled((Map<String, Object>)schema);
        } else if (Type.RECORD == type) {
            Map<String, Object> schemaMap = (Map<String, Object>)schema;
            doInNamespaceContext(schemaMap, ()->{
                if (!storeNamedTypeAsHandled(schemaMap)) {
                    return;
                }

                List<Map<String, Object>> fields =
                        (List<Map<String, Object>>)schemaMap.get(AvroConstants.SCHEMA_KEY_FIELDS);
                for (Map<String, Object> field : fields) {
                    try {
                        appendToCurrentPath((String)field.get(AvroConstants.SCHEMA_KEY_NAME));
                        if (!isCurrentPathTraversable(desiredPath)) {
                            continue;
                        }
                        listener.onSchemaField(getCurrentPathString(), schemaMap, field);
                        doWalk(desiredPath, schemaMap, field.get(AvroConstants.SCHEMA_KEY_TYPE));
                    } finally {
                        subtractFromCurrentPath();
                    }
                }
            });
        } else if (Type.UNION == type) {
            for (Object unionItemSchema : (List<Object>)schema) {
                doWalk(desiredPath, schema, unionItemSchema);
            }
        }
    }

    private String getCurrentPathString() {
        return CURRENT_PATH.get().getPathString();
    }

    private boolean isCurrentPathTraversable(Path desiredPath) {
        return desiredPath == null || desiredPath.startsWith(CURRENT_PATH.get());
    }

    private void appendToCurrentPath(String path) {
        CURRENT_PATH.get().append(path);
    }

    private void subtractFromCurrentPath() {
        CURRENT_PATH.get().subtract();
    }

    private Object getHandledNamedTypeFor(String name) {
        String fullname = buildFullNameFor(name, null);
        return NAMED_TYPES_HANDLED.get().get(fullname);
    }

    private boolean storeNamedTypeAsHandled(Map<String, Object> schema) {
        String fullname = buildFullNameForSchema(schema);
        return NAMED_TYPES_HANDLED.get().putIfAbsent(fullname, schema) == null;
    }

    private void doInNamespaceContext(Map<String, Object> schema, Runnable function) {
        String namespace = (String)schema.get(AvroConstants.SCHEMA_KEY_NAMESPACE);
        try {
            if (StringUtils.isNotEmpty(namespace)) {
                NAMESPACE_CONTEXT.get().push(namespace);
            }
            function.run();
        } finally {
            if (StringUtils.isNotEmpty(namespace)) {
                NAMESPACE_CONTEXT.get().pop();
            }
        }
    }

    private String buildFullNameForSchema(Map<String, Object> schema) {
        return buildFullNameFor(
                (String)schema.get(AvroConstants.SCHEMA_KEY_NAME),
                (String)schema.get(AvroConstants.SCHEMA_KEY_NAMESPACE));
    }

    private String buildFullNameFor(String name, String namespace) {
        if (StringUtils.isEmpty(namespace) && !StringUtils.contains(name, '.')) {
            namespace = !NAMESPACE_CONTEXT.get().isEmpty() ? NAMESPACE_CONTEXT.get().peek() : null;
        }
        return StringUtils.isNotEmpty(namespace) ? String.format("%s.%s", namespace, name) : name;
    }

}
