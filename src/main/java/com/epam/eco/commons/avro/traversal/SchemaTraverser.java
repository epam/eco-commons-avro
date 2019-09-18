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
package com.epam.eco.commons.avro.traversal;

import java.util.HashSet;
import java.util.Set;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.apache.commons.lang3.Validate;

import com.epam.eco.commons.avro.Path;

/**
 * @author Andrei_Tytsik
 */
public class SchemaTraverser {

    // Set of already handled record types. Helps to avoid circular dependencies.
    private static final ThreadLocal<Set<String>> RECORD_TYPES_HANDLED = ThreadLocal.withInitial(HashSet::new);

    private static final ThreadLocal<Path> CURRENT_PATH = ThreadLocal.withInitial(() -> new Path(null));

    private final SchemaTraverseListener listener;

    public SchemaTraverser(SchemaTraverseListener listener) {
        Validate.notNull(listener, "Listener is null");

        this.listener = listener;
    }

    public void walk(Schema schema) {
        walk(schema, (String)null);
    }

    public void walk(Schema schema, String desiredPath) {
        Validate.notNull(schema, "Schema is null");

        try {
            doWalk(
                    desiredPath != null ? new Path(desiredPath) : null,
                    null,
                    schema);
        } finally {
            RECORD_TYPES_HANDLED.remove();
            CURRENT_PATH.remove();
        }
    }

    private void doWalk(Path desiredPath, Schema parentSchema, Schema schema) {
        if (!isCurrentPathTraversable(desiredPath)) {
            return;
        }

        listener.onSchema(getCurrentPathString(), parentSchema, schema);
        if (Type.ARRAY == schema.getType()) {
            doWalk(desiredPath, schema, schema.getElementType());
        } else if (Type.MAP == schema.getType()) {
            doWalk(desiredPath, schema, schema.getValueType());
        } else if (Type.RECORD == schema.getType()) {
            if (!storeRecordTypeAsHandled(schema)) {
                return;
            }
            for (Field field : schema.getFields()) {
                try {
                    appendToCurrentPath(field.name());
                    if (!isCurrentPathTraversable(desiredPath)) {
                        continue;
                    }
                    listener.onSchemaField(getCurrentPathString(), schema, field);
                    doWalk(desiredPath, schema, field.schema());
                } finally {
                    subtractFromCurrentPath();
                }
            }
        } else if (Type.UNION == schema.getType()) {
            for (Schema unionItemSchema : schema.getTypes()) {
                doWalk(desiredPath, schema, unionItemSchema);
            }
        }
    }

    private String getCurrentPathString() {
        return CURRENT_PATH.get().getPathString();
    }

    private boolean storeRecordTypeAsHandled(Schema schema) {
        return RECORD_TYPES_HANDLED.get().add(schema.getFullName());
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

}
