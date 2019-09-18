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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.avro.Schema.Type;
import org.junit.Assert;
import org.junit.Test;

import com.epam.eco.commons.avro.AvroConstants;
import com.epam.eco.commons.avro.AvroUtils;

/**
 * @author Andrei_Tytsik
 */
public class ByNameSchemaFieldComparatorTest {

    @Test
    public void testSchemaFieldsAreAscOrdered() throws Exception {
        List<Map<String, Object>> fields = generateFieldList(100);
        List<Map<String, Object>> fieldsOfRecordOrUnknownType =
                extractFieldsOfRecordAndUnknownType(fields);

        fields.sort(ByNameSchemaFieldComparator.ASC);

        verifyFieldsOrdered(fields, fieldsOfRecordOrUnknownType, true);
    }

    @Test
    public void testSchemaFieldsAreDescOrdered() throws Exception {
        List<Map<String, Object>> fields = generateFieldList(100);
        List<Map<String, Object>> fieldsOfRecordOrUnknownType =
                extractFieldsOfRecordAndUnknownType(fields);

        fields.sort(ByNameSchemaFieldComparator.DESC);

        verifyFieldsOrdered(fields, fieldsOfRecordOrUnknownType, false);
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

    private List<Map<String, Object>> generateFieldList(int size) {
        List<Map<String, Object>> fields = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Map<String, Object> field = new HashMap<>();
            field.put(AvroConstants.SCHEMA_KEY_FIELD_NAME, "f" + i);
            if (i % 3 == 0) {
                if (new Random().nextBoolean()) {
                    field.put(AvroConstants.SCHEMA_KEY_FIELD_TYPE, Type.RECORD.name());
                } else {
                    field.put(AvroConstants.SCHEMA_KEY_FIELD_TYPE, "unknown");
                }
            } else {
                field.put(AvroConstants.SCHEMA_KEY_FIELD_TYPE, Type.STRING.name());
            }
            fields.add(field);
        }
        Collections.shuffle(fields);
        return fields;
    }

    private List<Map<String, Object>> extractFieldsOfRecordAndUnknownType(
            List<Map<String, Object>> fields) {
        return fields.stream().
                filter(this::isRecordOrUnknown).
                collect(Collectors.toList());
    }

    private boolean isRecordOrUnknown(Map<String, Object> field) {
        Type type = AvroUtils.typeOfGenericFieldOrElseNullIfUnknown(field);
        return type == null || type == Type.RECORD;
    }

}
