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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.avro.Schema;
import org.junit.Assert;
import org.junit.Test;

import com.epam.eco.commons.avro.AvroConstants;
import com.epam.eco.commons.avro.AvroUtils;
import com.epam.eco.commons.avro.modification.ChangeSchemaFieldNamesCase.Case;

/**
 * @author Andrei_Tytsik
 */
public class ChangeSchemaFieldNamesCaseTest {

    private final static String SCHEMA_JSON =
            "{\"type\": \"record\", \"name\": \"TestA\", \"fields\": [" +
                "{\"name\": \"fIeLd1_1\", \"type\":" +
                    "{\"type\": \"record\", \"name\": \"TestB\", \"fields\":[" +
                        "{\"name\": \"fIeLd2_1\", \"type\": \"int\"}," +
                        "{\"name\": \"fIeLd2_2\", \"type\": \"int\"}," +
                        "{\"name\": \"fIeLd2_3\", \"type\": \"int\"}" +
                    "]}"+
                "}," +
                "{\"name\": \"fIeLd1_2\", \"type\": \"int\"}," +
                "{\"name\": \"fIeLd1_3\", \"type\": [\"null\", " +
                    "{\"type\": \"array\", \"items\": " +
                        "{\"name\": \"TestC\", \"type\": \"record\", \"fields\":[" +
                            "{\"name\": \"fIeLd2_4\", \"type\": \"int\"}," +
                            "{\"name\": \"fIeLd2_5\", \"type\": \"int\"}," +
                            "{\"name\": \"fIeLd2_6\", \"type\":" +
                                "{\"type\": \"map\", \"values\":" +
                                    "{\"type\": \"record\", \"name\": \"TestD\", \"fields\":[" +
                                        "{\"name\": \"fIeLd3_1\", \"type\": \"int\"}," +
                                        "{\"name\": \"fIeLd3_2\", \"type\": \"int\"}," +
                                        "{\"name\": \"fIeLd3_3\", \"type\": [" +
                                            "{\"type\": \"record\", \"name\": \"TestE\", \"fields\":[" +
                                                "{\"name\": \"fIeLd4_1\", \"type\": \"int\"}," +
                                                "{\"name\": \"fIeLd4_2\", \"type\": \"int\"}," +
                                                "{\"name\": \"fIeLd4_3\", \"type\": \"int\"}" +
                                            "]}," +
                                            "{\"type\": \"record\", \"name\": \"TestF\", \"fields\":[" +
                                                "{\"name\": \"fIeLd4_4\", \"type\": \"int\"}," +
                                                "{\"name\": \"fIeLd4_5\", \"type\": \"int\"}," +
                                                "{\"name\": \"fIeLd4_6\", \"type\": \"int\"}" +
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
    public void testSchemaFieldNamesAreUppercased() throws Exception {
        Map<String, Object> schemaMap = (Map<String, Object>)AvroUtils.schemaToGeneric(SCHEMA);
        new ChangeSchemaFieldNamesCase(Case.UPPER).applyToGeneric(schemaMap);
        for (List<Map<String, Object>> fields : SchemaModificationTestUtils.resolveAllSchemasFields(schemaMap)) {
            verifyFieldNamesCaseChanged(fields, null, true);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSchemaFieldNamesAreUppercasedWithIncludes() throws Exception {
        Map<String, Object> schemaMap = (Map<String, Object>)AvroUtils.schemaToGeneric(SCHEMA);

        Set<String> include = new HashSet<>();
        include.add("^(\\..+)*fIeLd1_3(\\..+)*$");

        new ChangeSchemaFieldNamesCase(Case.UPPER, include, null).applyToGeneric(schemaMap);

        Set<String> ignoredFields = Stream.
                of(
                        "fIeLd1_1",
                        "fIeLd2_1", "fIeLd2_2", "fIeLd2_3",
                        "fIeLd1_2").
                collect(Collectors.toSet());

        for (List<Map<String, Object>> fields : SchemaModificationTestUtils.resolveAllSchemasFields(schemaMap)) {
            verifyFieldNamesCaseChanged(fields, ignoredFields, true);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSchemaFieldNamesAreUppercasedWithExcludes() throws Exception {
        Map<String, Object> schemaMap = (Map<String, Object>)AvroUtils.schemaToGeneric(SCHEMA);

        Set<String> exclude = new HashSet<>();
        exclude.add("^(\\..+)*fIeLd1_1(\\..+)*$");

        new ChangeSchemaFieldNamesCase(Case.UPPER, null, exclude).applyToGeneric(schemaMap);

        Set<String> ignoredFields = Stream.
                of(
                        "fIeLd1_1",
                        "fIeLd2_1", "fIeLd2_2", "fIeLd2_3").
                collect(Collectors.toSet());

        for (List<Map<String, Object>> fields : SchemaModificationTestUtils.resolveAllSchemasFields(schemaMap)) {
            verifyFieldNamesCaseChanged(fields, ignoredFields, true);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSchemaFieldNamesAreUppercasedWithIncludesAndExcludes() throws Exception {
        Map<String, Object> schemaMap = (Map<String, Object>)AvroUtils.schemaToGeneric(SCHEMA);

        Set<String> include = new HashSet<>();
        include.add("^(\\..+)*fIeLd1_3(\\..+)*$");

        Set<String> exclude = new HashSet<>();
        exclude.add("fIeLd4_4");
        exclude.add("fIeLd4_5");
        exclude.add("fIeLd4_6");

        new ChangeSchemaFieldNamesCase(Case.UPPER, include, exclude).applyToGeneric(schemaMap);

        Set<String> ignoredFields = Stream.
                of(
                        "fIeLd1_1",
                        "fIeLd2_1", "fIeLd2_2", "fIeLd2_3",
                        "fIeLd1_2",
                        "fIeLd4_4", "fIeLd4_5", "fIeLd4_6").
                collect(Collectors.toSet());

        for (List<Map<String, Object>> fields : SchemaModificationTestUtils.resolveAllSchemasFields(schemaMap)) {
            verifyFieldNamesCaseChanged(fields, ignoredFields, true);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSchemaFieldNamesAreLowercased() throws Exception {
        Map<String, Object> schemaMap = (Map<String, Object>)AvroUtils.schemaToGeneric(SCHEMA);
        new ChangeSchemaFieldNamesCase(Case.LOWER).applyToGeneric(schemaMap);
        for (List<Map<String, Object>> fields : SchemaModificationTestUtils.resolveAllSchemasFields(schemaMap)) {
            verifyFieldNamesCaseChanged(fields, null, false);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSchemaFieldNamesAreLowercasedWithIncludes() throws Exception {
        Map<String, Object> schemaMap = (Map<String, Object>)AvroUtils.schemaToGeneric(SCHEMA);

        Set<String> include = new HashSet<>();
        include.add("^(\\..+)*fIeLd1_3(\\..+)*$");

        new ChangeSchemaFieldNamesCase(Case.LOWER, include, null).applyToGeneric(schemaMap);

        Set<String> ignoredFields = Stream.
                of(
                        "fIeLd1_1",
                        "fIeLd2_1", "fIeLd2_2", "fIeLd2_3",
                        "fIeLd1_2").
                collect(Collectors.toSet());

        for (List<Map<String, Object>> fields : SchemaModificationTestUtils.resolveAllSchemasFields(schemaMap)) {
            verifyFieldNamesCaseChanged(fields, ignoredFields, false);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSchemaFieldNamesAreLowercasedWithExcludes() throws Exception {
        Map<String, Object> schemaMap = (Map<String, Object>)AvroUtils.schemaToGeneric(SCHEMA);

        Set<String> exclude = new HashSet<>();
        exclude.add("^(\\..+)*fIeLd1_1(\\..+)*$");

        new ChangeSchemaFieldNamesCase(Case.LOWER, null, exclude).applyToGeneric(schemaMap);

        Set<String> ignoredFields = Stream.
                of(
                        "fIeLd1_1",
                        "fIeLd2_1", "fIeLd2_2", "fIeLd2_3").
                collect(Collectors.toSet());

        for (List<Map<String, Object>> fields : SchemaModificationTestUtils.resolveAllSchemasFields(schemaMap)) {
            verifyFieldNamesCaseChanged(fields, ignoredFields, false);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSchemaFieldNamesAreLowercasedWithIncludesAndExcludes() throws Exception {
        Map<String, Object> schemaMap = (Map<String, Object>)AvroUtils.schemaToGeneric(SCHEMA);

        Set<String> include = new HashSet<>();
        include.add("^(\\..+)*fIeLd1_3(\\..+)*$");

        Set<String> exclude = new HashSet<>();
        exclude.add("fIeLd4_4");
        exclude.add("fIeLd4_5");
        exclude.add("fIeLd4_6");

        new ChangeSchemaFieldNamesCase(Case.LOWER, include, exclude).applyToGeneric(schemaMap);

        Set<String> ignoredFields = Stream.
                of(
                        "fIeLd1_1",
                        "fIeLd2_1", "fIeLd2_2", "fIeLd2_3",
                        "fIeLd1_2",
                        "fIeLd4_4", "fIeLd4_5", "fIeLd4_6").
                collect(Collectors.toSet());

        for (List<Map<String, Object>> fields : SchemaModificationTestUtils.resolveAllSchemasFields(schemaMap)) {
            verifyFieldNamesCaseChanged(fields, ignoredFields, false);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testTwoSchemasAreEqualAfterChangingFieldNamesCase() throws Exception {
        Map<String, Object> schemaMap1 = (Map<String, Object>)AvroUtils.schemaToGeneric(SCHEMA);
        new ChangeSchemaFieldNamesCase(Case.UPPER).applyToGeneric(schemaMap1);

        Map<String, Object> schemaMap2 = (Map<String, Object>)AvroUtils.schemaToGeneric(SCHEMA);
        new ChangeSchemaFieldNamesCase(Case.UPPER).applyToGeneric(schemaMap2);

        Assert.assertEquals(schemaMap1, schemaMap2);
    }

    private void verifyFieldNamesCaseChanged(
            List<Map<String, Object>> fields,
            Set<String> ignoredFields,
            boolean upper) {
        for (Map<String, Object> field : fields) {
            String fieldName = (String)field.get(AvroConstants.SCHEMA_KEY_FIELD_NAME);
            if (ignoredFields != null && ignoredFields.contains(fieldName)) {
                continue;
            }
            Assert.assertEquals(
                    upper ? fieldName.toUpperCase() : fieldName.toLowerCase(),
                    fieldName);
        }
    }

}
