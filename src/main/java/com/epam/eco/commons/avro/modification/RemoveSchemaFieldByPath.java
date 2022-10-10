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
package com.epam.eco.commons.avro.modification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.Validate;

import com.epam.eco.commons.avro.AvroConstants;
import com.epam.eco.commons.avro.traversal.GenericSchemaTraverseListener;
import com.epam.eco.commons.avro.traversal.GenericSchemaTraverser;

/**
 * @author Andrei_Tytsik
 *
 * The class has not supported cases when we're removing fields with record definition
 * and having other fields than using this record.
 * Successfully working in this situation can be only if we remove all usage of the scheme
 * before removing the field with definition.
 * More behavior in tests RemoveSchemaFieldWithRecordDefinitionTest.java
 *
 */
public class RemoveSchemaFieldByPath implements SchemaModification {

    private final String path;
    private final boolean strict;

    public RemoveSchemaFieldByPath(String path) {
        this(path, true);
    }

    public RemoveSchemaFieldByPath(String path, boolean strict) {
        Validate.notBlank(path, "Field path is null");

        this.path = path;
        this.strict = strict;
    }

    public String getPath() {
        return path;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void applyToGeneric(Map<String, Object> schemaMap) {
        List<Map<String, Object>> typesToRemoveFieldFrom = new ArrayList<>();
        List<Object> fieldsToRemove = new ArrayList<>();

        new GenericSchemaTraverser(new GenericSchemaTraverseListener() {
            @Override
            public void onSchemaField(
                    String path,
                    Map<String, Object> parentSchema,
                    Map<String, Object> field) {
                if (RemoveSchemaFieldByPath.this.path.equals(path)) {
                    typesToRemoveFieldFrom.add(parentSchema);
                    fieldsToRemove.add(field);
                }
            }
            @Override
            public void onSchema(String path, Object parentSchema, Object schema) {
                // do nothing
            }
        }).walk(schemaMap, null);

        int removedCount = 0;
        for (int i = 0; i < typesToRemoveFieldFrom.size(); i++) {
            List<Object> fields =
                    (List<Object>)typesToRemoveFieldFrom.get(i).get(AvroConstants.SCHEMA_KEY_FIELDS);
            Object field = fieldsToRemove.get(i);
            if (fields.remove(field)) {
                removedCount++;
            }
        }

        if (strict && removedCount == 0) {
            throw new RuntimeException(String.format("Field not found at '%s'", path));
        }
    }

    @Override
    public String toString() {
        return String.format("%s: {path: %s}", this.getClass().getSimpleName(), path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, strict);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        RemoveSchemaFieldByPath that = (RemoveSchemaFieldByPath)obj;
        return
                Objects.equals(this.path, that.path) &&
                        Objects.equals(this.strict, that.strict);
    }

    public static RemoveSchemaFieldByPath with(String path) {
        return new RemoveSchemaFieldByPath(path);
    }

}
