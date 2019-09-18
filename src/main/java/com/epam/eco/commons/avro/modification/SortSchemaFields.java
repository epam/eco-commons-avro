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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.avro.Schema.Type;

import com.epam.eco.commons.avro.AvroConstants;
import com.epam.eco.commons.avro.AvroUtils;
import com.epam.eco.commons.avro.traversal.GenericSchemaTraverseListener;
import com.epam.eco.commons.avro.traversal.GenericSchemaTraverser;

/**
 * @author Andrei_Tytsik
 */
public class SortSchemaFields implements SchemaModification {

    private final Comparator<Map<String, Object>> comparator;

    public SortSchemaFields() {
        this(ByNameSchemaFieldComparator.ASC);
    }

    public SortSchemaFields(Comparator<Map<String, Object>> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Map<String, Object>> getComparator() {
        return comparator;
    }

    @Override
    public void applyToGeneric(Map<String, Object> schemaMap) {
        List<List<Map<String, Object>>> schemasFields = new ArrayList<>();

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
                if (AvroUtils.typeOfGenericSchema(schema) == Type.RECORD) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> fields =
                            (List<Map<String, Object>>)((Map<String, Object>)schema).get(
                                    AvroConstants.SCHEMA_KEY_FIELDS);
                    schemasFields.add(fields);
                }
            }
        }).walk(schemaMap);

        for (List<Map<String, Object>> fields : schemasFields) {
            if (fields == null || fields.isEmpty()) {
                continue;
            }
            fields.sort(comparator);
        }
    }

    @Override
    public String toString() {
        return String.format(
                "%s: {comparator: %s}",
                this.getClass().getSimpleName(),
                comparator.getClass().getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(comparator.getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        SortSchemaFields that = (SortSchemaFields)obj;
        return
                Objects.equals(this.comparator.getClass(), that.comparator.getClass());
    }

    public static SortSchemaFields withDefault() {
        return new SortSchemaFields();
    }

    public static SortSchemaFields with(Comparator<Map<String, Object>> comparator) {
        return new SortSchemaFields(comparator);
    }

}
