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

import org.apache.avro.Schema.Type;

import com.epam.eco.commons.avro.AvroConstants;

/**
 * @author Andrei_Tytsik
 */
public class SchemaModificationTestUtils {

    @SuppressWarnings("unchecked")
    public static List<List<Map<String, Object>>> resolveAllSchemasFields(Object genericSchema) {
        List<List<Map<String, Object>>> schemasFields = new ArrayList<>();
        if (genericSchema instanceof Map) {
            Map<String, Object> schemaMap = (Map<String, Object>)genericSchema;
            Object type = schemaMap.get(AvroConstants.SCHEMA_KEY_TYPE);
            if (type instanceof String) {
                if (Type.RECORD.getName().equals(type)) {
                    List<Map<String, Object>> fields =
                            (List<Map<String, Object>>)schemaMap.get(AvroConstants.SCHEMA_KEY_FIELDS);
                    if (fields != null) {
                        schemasFields.add(fields);
                        for (Map<String, Object> field : fields) {
                            schemasFields.addAll(resolveAllSchemasFields(field));
                        }
                    }
                } else if (Type.ARRAY.getName().equals(type)) {
                    schemasFields.addAll(resolveAllSchemasFields(schemaMap.get("items")));
                } else if (Type.MAP.getName().equals(type)) {
                    schemasFields.addAll(resolveAllSchemasFields(schemaMap.get("values")));
                }
            } else {
                schemasFields.addAll(resolveAllSchemasFields(type));
            }
        } else if (genericSchema instanceof List) {
            List<Object> schemaList = (List<Object>)genericSchema;
            for (Object schemaListItem : schemaList) {
                schemasFields.addAll(resolveAllSchemasFields(schemaListItem));
            }
        }
        return schemasFields;
    }

}
