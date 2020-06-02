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

import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;
import org.junit.Assert;
import org.junit.Test;

import com.epam.eco.commons.avro.AvroConstants;
import com.epam.eco.commons.avro.AvroUtils;

/**
 * @author Andrei_Tytsik
 */
public class SortSchemaFieldsTest {

    // x_ - named fields
    // f_ - other fields
    private final static String SCHEMA_JSON =
                "{\"type\": \"record\", \"name\": \"TestA\", \"fields\": [" +
                    "{\"name\": \"f_3\", \"type\": \"int\"}," +
                    "{\"name\": \"f_4\", \"type\": \"int\"}," +
                    "{\"name\": \"x_0\", \"type\": {\"type\": \"fixed\", \"name\": \"FixedA\", \"size\": 32}}," +
                    "{\"name\": \"f_5\", \"type\": \"int\"}," +
                    "{\"name\": \"f_2\", \"type\": \"int\"}," +
                    "{\"name\": \"x_1\", \"type\": [\"null\", " +
                        "{\"type\": \"array\", \"items\": " +
                            "{\"name\": \"TestB\", \"type\": \"record\", \"fields\":[" +
                                "{\"name\": \"f_4\", \"type\": \"int\"}," +
                                "{\"name\": \"f_2\", \"type\": \"int\"}," +
                                "{\"name\": \"f_3\", \"type\": \"int\"}," +
                                "{\"name\": \"x_0\", \"type\": {\"type\": \"enum\", \"name\": \"EnumB\", \"symbols\": [\"B\"]}}," +
                                "{\"name\": \"f_5\", \"type\": \"int\"}," +
                                "{\"name\": \"x_1\", \"type\":" +
                                    "{\"type\": \"map\", \"values\":" +
                                        "{\"type\": \"record\", \"name\": \"TestC\", \"fields\":[" +
                                            "{\"name\": \"f_4\", \"type\": \"int\"}," +
                                            "{\"name\": \"f_3\", \"type\": \"int\"}," +
                                            "{\"name\": \"x_0\", \"type\": \"FixedA\"}," +
                                            "{\"name\": \"f_5\", \"type\": \"int\"}," +
                                            "{\"name\": \"f_2\", \"type\": \"int\"}," +
                                            "{\"name\": \"x_1\", \"type\": [" +
                                                "{\"type\": \"record\", \"name\": \"TestD\", \"fields\":[" +
                                                    "{\"name\": \"f_5\", \"type\": \"int\"}," +
                                                    "{\"name\": \"f_4\", \"type\": \"int\"}," +
                                                    "{\"name\": \"f_3\", \"type\": \"int\"}," +
                                                    "{\"name\": \"x_0\", \"type\": \"EnumB\"}," +
                                                    "{\"name\": \"f_2\", \"type\": \"int\"}," +
                                                    "{\"name\": \"f_1\", \"type\": \"int\"}" +
                                                "]}," +
                                                "{\"type\": \"record\", \"name\": \"TestE\", \"fields\":[" +
                                                    "{\"name\": \"x_0\", \"type\": \"FixedA\"}," +
                                                    "{\"name\": \"f_1\", \"type\": \"int\"}," +
                                                    "{\"name\": \"f_2\", \"type\": \"int\"}," +
                                                    "{\"name\": \"f_3\", \"type\": \"int\"}," +
                                                    "{\"name\": \"f_4\", \"type\": \"int\"}," +
                                                    "{\"name\": \"f_5\", \"type\": \"int\"}" +
                                                "]}" +
                                            "]}" +
                                        "]}" +
                                    "}" +
                                "}" +
                            "]}" +
                        "}" +
                    "]}" +
                "]}";

    private final static Schema SCHEMA = new Schema.Parser().parse(SCHEMA_JSON);

    @SuppressWarnings("unchecked")
    @Test
    public void testSchemaFieldsAreAscOrdered() throws Exception {
        Map<String, Object> schemaMap = (Map<String, Object>)AvroUtils.schemaToGeneric(SCHEMA);
        new SortSchemaFields(ByNameSchemaFieldComparator.ASC).applyToGeneric(schemaMap);
        for (List<Map<String, Object>> fields : SchemaModificationTestUtils.resolveAllSchemasFields(schemaMap)) {
            verifyFieldsOrdered(fields, true);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSchemaFieldsAreDescOrdered() throws Exception {
        Map<String, Object> schemaMap = (Map<String, Object>)AvroUtils.schemaToGeneric(SCHEMA);
        new SortSchemaFields(ByNameSchemaFieldComparator.DESC).applyToGeneric(schemaMap);
        for (List<Map<String, Object>> fields : SchemaModificationTestUtils.resolveAllSchemasFields(schemaMap)) {
            verifyFieldsOrdered(fields, false);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testTwoSchemasAreEqualAfterSorting() throws Exception {
        Map<String, Object> schemaMap1 = (Map<String, Object>)AvroUtils.schemaToGeneric(SCHEMA);
        new SortSchemaFields().applyToGeneric(schemaMap1);

        Map<String, Object> schemaMap2 = (Map<String, Object>)AvroUtils.schemaToGeneric(SCHEMA);
        new SortSchemaFields().applyToGeneric(schemaMap2);

        Assert.assertEquals(schemaMap1, schemaMap2);
    }

    private void verifyFieldsOrdered(List<Map<String, Object>> fields, boolean asc) {
        String prevFieldName = null;
        for (Map<String, Object> field : fields) {
            String fieldName = (String)field.get(AvroConstants.SCHEMA_KEY_FIELD_NAME);
            if (prevFieldName != null) {
                if (prevFieldName.startsWith("x_") && fieldName.startsWith("x_")) { // always same order!
                    Assert.assertTrue(prevFieldName.compareTo(fieldName) < 0);
                } else if (prevFieldName.startsWith("f_") && fieldName.startsWith("f_")) {
                    Assert.assertTrue(
                            asc ?
                            prevFieldName.compareTo(fieldName) < 0 :
                            prevFieldName.compareTo(fieldName) > 0);
                }
            }
            prevFieldName = fieldName;
        }
    }

}
