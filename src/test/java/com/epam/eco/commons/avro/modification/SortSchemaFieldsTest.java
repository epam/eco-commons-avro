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

import java.util.Comparator;
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

    private final static String SCHEMA_JSON =
                "{\"type\": \"record\", \"name\": \"TestA\", \"fields\": [" +
                    "{\"name\": \"f3\", \"type\": \"int\"}," +
                    "{\"name\": \"f4\", \"type\": \"int\"}," +
                    "{\"name\": \"f5\", \"type\": \"int\"}," +
                    "{\"name\": \"f2\", \"type\": \"int\"}," +
                    "{\"name\": \"f1\", \"type\": [\"null\", " +
                        "{\"type\": \"array\", \"items\": " +
                            "{\"name\": \"TestB\", \"type\": \"record\", \"fields\":[" +
                                "{\"name\": \"f4\", \"type\": \"int\"}," +
                                "{\"name\": \"f2\", \"type\": \"int\"}," +
                                "{\"name\": \"f3\", \"type\": \"int\"}," +
                                "{\"name\": \"f5\", \"type\": \"int\"}," +
                                "{\"name\": \"f1\", \"type\":" +
                                    "{\"type\": \"map\", \"values\":" +
                                        "{\"type\": \"record\", \"name\": \"TestC\", \"fields\":[" +
                                            "{\"name\": \"f4\", \"type\": \"int\"}," +
                                            "{\"name\": \"f3\", \"type\": \"int\"}," +
                                            "{\"name\": \"f5\", \"type\": \"int\"}," +
                                            "{\"name\": \"f2\", \"type\": \"int\"}," +
                                            "{\"name\": \"f1\", \"type\": [" +
                                                "{\"type\": \"record\", \"name\": \"TestD\", \"fields\":[" +
                                                    "{\"name\": \"f5\", \"type\": \"int\"}," +
                                                    "{\"name\": \"f4\", \"type\": \"int\"}," +
                                                    "{\"name\": \"f3\", \"type\": \"int\"}," +
                                                    "{\"name\": \"f2\", \"type\": \"int\"}," +
                                                    "{\"name\": \"f1\", \"type\": \"int\"}" +
                                                "]}," +
                                                "{\"type\": \"record\", \"name\": \"TestE\", \"fields\":[" +
                                                    "{\"name\": \"f1\", \"type\": \"int\"}," +
                                                    "{\"name\": \"f2\", \"type\": \"int\"}," +
                                                    "{\"name\": \"f3\", \"type\": \"int\"}," +
                                                    "{\"name\": \"f4\", \"type\": \"int\"}," +
                                                    "{\"name\": \"f5\", \"type\": \"int\"}" +
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
        new SortSchemaFields(SimpleByNameSchemaFieldComparator.ASC).applyToGeneric(schemaMap);
        for (List<Map<String, Object>> fields : SchemaModificationTestUtils.resolveAllSchemasFields(schemaMap)) {
            verifyFieldsOrdered(fields, true);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSchemaFieldsAreDescOrdered() throws Exception {
        Map<String, Object> schemaMap = (Map<String, Object>)AvroUtils.schemaToGeneric(SCHEMA);
        new SortSchemaFields(SimpleByNameSchemaFieldComparator.DESC).applyToGeneric(schemaMap);
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
                Assert.assertTrue(
                        asc ?
                        prevFieldName.compareTo(fieldName) <= 0 :
                        prevFieldName.compareTo(fieldName) >= 0);
            }
            prevFieldName = fieldName;
        }
    }

}

class SimpleByNameSchemaFieldComparator implements Comparator<Map<String, Object>> {

    static final SimpleByNameSchemaFieldComparator ASC = new SimpleByNameSchemaFieldComparator(true);
    static final SimpleByNameSchemaFieldComparator DESC = new SimpleByNameSchemaFieldComparator(false);

    private final boolean asc;

    private SimpleByNameSchemaFieldComparator(boolean asc) {
        this.asc = asc;
    }

    @Override
    public int compare(Map<String, Object> field1, Map<String, Object> field2) {
        String fieldName1 = AvroUtils.nameOfGenericField(field1);
        String fieldName2 = AvroUtils.nameOfGenericField(field2);
        return (!asc ? -1 : 1) * fieldName1.compareTo(fieldName2);
    }
}
