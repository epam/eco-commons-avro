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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.avro.Schema.Type;
import org.junit.Assert;
import org.junit.Test;

import com.epam.eco.commons.avro.AvroConstants;
import com.epam.eco.commons.avro.AvroUtils;
import com.epam.eco.commons.avro.GenericSchemaDataGen;

/**
 * @author Andrei_Tytsik
 */
public class ByNameSchemaFieldComparatorTest {

    @Test
    public void testSchemaFieldsAreAscOrdered() throws Exception {
        List<Map<String, Object>> fields = GenericSchemaDataGen.randomFields(300);
        List<Map<String, Object>> fieldsOfRecordOrAmbiguousType =
                extractFieldsOfRecordOrAmbiguousType(fields);

        fields.sort(ByNameSchemaFieldComparator.ASC);

        verifyFieldsValid(fields);
        verifyFieldsOrdered(fields, fieldsOfRecordOrAmbiguousType, true);
    }

    @Test
    public void testSchemaFieldsAreDescOrdered() throws Exception {
        List<Map<String, Object>> fields = GenericSchemaDataGen.randomFields(300);
        List<Map<String, Object>> fieldsOfRecordOrAmbiguousType =
                extractFieldsOfRecordOrAmbiguousType(fields);

        fields.sort(ByNameSchemaFieldComparator.DESC);

        verifyFieldsValid(fields);
        verifyFieldsOrdered(fields, fieldsOfRecordOrAmbiguousType, false);
    }

    private void verifyFieldsOrdered(
            List<Map<String, Object>> fields,
            List<Map<String, Object>> unorderedFieldsAtBeginning,
            boolean asc) {
        for (int i = 0; i < unorderedFieldsAtBeginning.size(); i++) {
            Assert.assertEquals(
                    unorderedFieldsAtBeginning.get(i),
                    fields.get(i));
        }

        String prevFieldName = null;
        for (int i = unorderedFieldsAtBeginning.size(); i < fields.size(); i++) {
            Map<String, Object> field = fields.get(i);
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

    private void verifyFieldsValid(List<Map<String, Object>> fields) {
        Object recordSchema = GenericSchemaDataGen.recordSchema("Test", fields);
        AvroUtils.schemaFromGeneric(recordSchema); //should not fail
    }

    private List<Map<String, Object>> extractFieldsOfRecordOrAmbiguousType(
            List<Map<String, Object>> fields) {
        return fields.stream().
                filter(this::isOfNamedOrAmbiguousType).
                collect(Collectors.toList());
    }

    private boolean isOfNamedOrAmbiguousType(Map<String, Object> field) {
        Type type = AvroUtils.effectiveTypeOfGenericFieldOrElseNullIfUnknown(field);
        return
                type == null ||
                type == Type.UNION ||
                AvroUtils.isNamed(type);
    }

}
