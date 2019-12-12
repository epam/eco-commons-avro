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
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import avro.shaded.com.google.common.collect.ImmutableMap;
import org.apache.avro.Schema.Type;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

import com.epam.eco.commons.avro.AvroConstants;

/**
 * @author Andrei_Tytsik
 */
public class ByNameSchemaFieldComparatorTest {

    private static String[] primitiveTypes = new String[]{
            Type.INT.getName(), Type.LONG.getName(), Type.FLOAT.getName(), Type.DOUBLE.getName(),
            Type.BOOLEAN.getName(), Type.BYTES.getName(), Type.STRING.getName(), Type.NULL.getName()
    };

    private static String[] complexAndUnknownTypes = new String[]{
            Type.FIXED.getName(), Type.MAP.getName(), Type.RECORD.getName(), Type.UNION.getName(),
            Type.ARRAY.getName(), Type.ENUM.getName(), "unknown"
    };

    @Test
    public void testCompareAsc() {
        compare(ByNameSchemaFieldComparator.ASC, true);
    }

    @Test
    public void testCompareDesc() {
        compare(ByNameSchemaFieldComparator.DESC, false);
    }

    private void compare(ByNameSchemaFieldComparator comparator, boolean asc) {
        Assert.assertTrue(comparator.compare(
                schema("field1", Type.STRING.getName()), schema("field3", Type.STRING.getName())
        ) * (asc ? 1 : -1) < 0);

        Assert.assertTrue(comparator.compare(
                schema("field1", Type.STRING.getName()), schema("field3", Type.INT.getName())
        ) * (asc ? 1 : -1) < 0);

        Assert.assertTrue(comparator.compare(
                schema("field1", Type.BOOLEAN.getName()), schema("field3", Type.STRING.getName())
        ) * (asc ? 1 : -1) < 0);

        Assert.assertTrue(comparator.compare(
                schema("field1", Type.BYTES.getName()), schema("field3", Type.LONG.getName())
        ) * (asc ? 1 : -1) < 0);

        Assert.assertTrue(comparator.compare(
                schema("field1", Type.STRING.getName()), schema("field3", Type.RECORD.getName())
        ) > 0);

        Assert.assertTrue(comparator.compare(
                schema("field1", Type.STRING.getName()), schema("field3", Type.MAP.getName())
        ) > 0);

        Assert.assertTrue(comparator.compare(
                schema("field1", Type.INT.getName()), schema("field3", Type.UNION.getName())
        ) > 0);

        Assert.assertTrue(comparator.compare(
                schema("field3", Type.STRING.getName()),
                schema("field1", unionSchema("null", Type.STRING.getName()))
        ) * (asc ? 1 : -1) > 0);

        Assert.assertTrue(comparator.compare(
                schema("field1", Type.STRING.getName()),
                schema("field3", unionSchema("null",
                                    unionSchema("null",
                                        unionSchema("null", Type.STRING.getName()))))
        ) * (asc ? 1 : -1) < 0);

        Assert.assertTrue(comparator.compare(
                schema("field3", unionSchema("null",
                                    unionSchema("null",
                                        unionSchema("null", Type.STRING.getName())))),
                schema("field1", Type.STRING.getName())
        ) * (asc ? 1 : -1) > 0);

        Assert.assertTrue(comparator.compare(
                schema("field1", Type.STRING.getName()),
                schema("field1", unionSchema("null",
                                    unionSchema("null",
                                        unionSchema("null", Type.STRING.getName()))))
        ) * (asc ? 1 : -1) == 0);

        Assert.assertTrue(comparator.compare(
                schema("field0", Type.STRING.getName()),
                schema("field1", unionSchema("null",
                                    schema(Type.STRING.getName(), Type.ENUM.getName())))
        ) > 0);

    }

    @Test
    public void testSchemaFieldsAreAscOrdered() throws Exception {
        Pair<List<Map<String, Object>>, List<Map<String, Object>>> fields = generateFieldList(100);
        List<Map<String, Object>> fieldsToSort = fields.getLeft();
        List<Map<String, Object>> fieldsOfComplexOrUnknownType = fields.getRight();

        fieldsToSort.sort(ByNameSchemaFieldComparator.ASC);

        verifyFieldsOrdered(fieldsToSort, fieldsOfComplexOrUnknownType, true);
    }

    @Test
    public void testSchemaFieldsAreDescOrdered() throws Exception {
        Pair<List<Map<String, Object>>, List<Map<String, Object>>> fields = generateFieldList(100);
        List<Map<String, Object>> fieldsToSort = fields.getLeft();
        List<Map<String, Object>> fieldsOfComplexOrUnknownType = fields.getRight();

        fieldsToSort.sort(ByNameSchemaFieldComparator.DESC);

        verifyFieldsOrdered(fieldsToSort, fieldsOfComplexOrUnknownType, false);
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

    private Pair<List<Map<String, Object>>, List<Map<String, Object>>> generateFieldList(int size) {
        List<Integer> indexes = IntStream.range(0, size).boxed().collect(Collectors.toList());
        Collections.shuffle(indexes);

        List<Map<String, Object>> fields = new ArrayList<>();
        List<Map<String, Object>> fieldsOfComplexOrUnknownType = new ArrayList<>();
        for (int i = 0; i < indexes.size() / 3; i++) {
            String primitiveType = getRandomPrimitiveType();
            Map<String, Object> field = schema("field" + indexes.get(i), primitiveType);
            fields.add(field);
        }
        for (int i = indexes.size() / 3; i < indexes.size() / 2; i++) {
            String complexType = getRandomComplexType();
            Map<String, Object> field = schema("field" + indexes.get(i), complexType);
            fields.add(field);
            fieldsOfComplexOrUnknownType.add(field);
        }
        for (int i = indexes.size() / 2; i < indexes.size(); i++) {
            boolean isNextSchemaOfComplexType = new Random().nextBoolean();
            String nextType =  isNextSchemaOfComplexType ?  getRandomComplexType() : getRandomPrimitiveType();
            List<Object> unionSchema = getRandomUnionSchema(nextType);
            Map<String, Object> field = schema("field" + indexes.get(i), unionSchema);
            fields.add(field);
            if(isNextSchemaOfComplexType) {
                fieldsOfComplexOrUnknownType.add(field);
            }
        }

        return new ImmutablePair<>(fields, fieldsOfComplexOrUnknownType);
    }

    private Map<String, Object> schema(String name, Object type) {
        return ImmutableMap.of(
                AvroConstants.SCHEMA_KEY_FIELD_NAME, name,
                AvroConstants.SCHEMA_KEY_FIELD_TYPE, type
        );
    }

    private List<Object> unionSchema(Object... types) {
        List<Object> unionSchema = new ArrayList<>(types.length);
        for (Object type : types) {
            unionSchema.add(type);
        }
        return unionSchema;
    }

    private String getRandomPrimitiveType() {
        int nextIndex = new Random().nextInt(primitiveTypes.length);
        return primitiveTypes[nextIndex];
    }

    private String getRandomComplexType() {
        int nextIndex = new Random().nextInt(complexAndUnknownTypes.length);
        return complexAndUnknownTypes[nextIndex];
    }

    private List<Object> getRandomUnionSchema(String nextType) {
        int unionDepth = new Random().nextInt(3);
        List<Object> schema = new ArrayList<>();
        schema.add(nextType);
        schema.add(Type.NULL.getName());
        while (unionDepth > 0) {
            schema = unionSchema(schema);
            unionDepth--;
        }
        return schema;
    }

}
