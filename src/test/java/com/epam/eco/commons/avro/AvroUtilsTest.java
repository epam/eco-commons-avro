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
package com.epam.eco.commons.avro;

import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Field.Order;
import org.apache.avro.Schema.Type;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.util.Utf8;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.eco.commons.avro.data.TestPerson;
import com.epam.eco.commons.avro.data.TestPersonDataReader;


/**
 * @author tytsik
 */
public class AvroUtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvroUtilsTest.class);

    private static final String TEST_FIELD_SCHEMA_JSON = "{\"type\":\"record\",\"name\":\"TestRecord\",\"fields\":[{\"name\":\"testFieldInt\",\"type\":[\"null\",\"int\"]}]}";
    private static final Schema TEST_FIELD_SCHEMA = new Schema.Parser().parse(TEST_FIELD_SCHEMA_JSON);
    private static final String TEST_FIELD_NAME = "testName";
    private static final String TEST_FIELD_DOC = "testDoc";
    private static final Order TEST_FIELD_ORDER = Order.IGNORE;
    private static final Field TEST_FIELD = new Schema.Field(TEST_FIELD_NAME, TEST_FIELD_SCHEMA, TEST_FIELD_DOC, (Object)null, TEST_FIELD_ORDER);
    private static final String TEST_FIELD_JSON =
            "{\"name\":\"" + TEST_FIELD_NAME +
            "\",\"type\":" + TEST_FIELD_SCHEMA_JSON +
            ",\"doc\":\"" + TEST_FIELD_DOC +
            "\",\"order\":\"" + TEST_FIELD_ORDER.name().toLowerCase() + "\"}";

    @Test
    public void testGenericRecordIsDecodedFromBytes() throws Exception {
        TestPersonDataReader.readBinaryTestPersons().forEach((bytes)->{
            GenericRecord record = AvroUtils.decodeRecordFromBinary(bytes, TestPerson.SCHEMA$, false);
            Assert.assertNotNull(record);
            LOGGER.info("Generic person decoded from bytes={}", record);
        });
    }

    @Test
    public void testSpecificRecordIsDecodedFromBytes() throws Exception {
        TestPersonDataReader.readBinaryTestPersons().forEach((bytes)->{
            TestPerson record = (TestPerson)AvroUtils.decodeRecordFromBinary(bytes, TestPerson.SCHEMA$, true);
            Assert.assertNotNull(record);
            LOGGER.info("Specific person decoded from bytes={}", record);
        });
    }

    @Test
    public void testGenericRecordIsDecodedFromJson() throws Exception {
        TestPersonDataReader.readJsonTestPersons().forEach((json)->{
            GenericRecord record = AvroUtils.decodeRecordFromJson(json, TestPerson.SCHEMA$, false);
            Assert.assertNotNull(record);
            LOGGER.info("Generic person decoded from json={}", record);
        });
    }

    @Test
    public void testSpecificRecordIsDecodedFromJson() throws Exception {
        TestPersonDataReader.readJsonTestPersons().forEach((json)->{
            TestPerson record = (TestPerson)AvroUtils.decodeRecordFromJson(json, TestPerson.SCHEMA$, true);
            Assert.assertNotNull(record);
            LOGGER.info("Specific person decoded from json={}", record);
        });
    }

    @Test
    public void testGenericRecordIsEncodedToJson() throws Exception {
        TestPersonDataReader.readGenericTestPersons().forEach((person)->{
            String json = AvroUtils.encodeRecordToJson(person);
            Assert.assertNotNull(json);
            LOGGER.info("Generic person encoded to JSON={}", json);
        });
    }

    @Test
    public void testSpecificRecordIsEncodedToJson() throws Exception {
        TestPersonDataReader.readSpecificTestPersons().forEach((person)->{
            String json = AvroUtils.encodeRecordToJson(person);
            Assert.assertNotNull(json);
            LOGGER.info("Specific person encoded to JSON={}", json);
        });
    }

    @Test
    public void testGenericRecordIsEncodedToBinary() throws Exception {
        TestPersonDataReader.readGenericTestPersons().forEach((person)->{
            byte[] bytes = AvroUtils.encodeRecordToBinary(person);
            Assert.assertNotNull(person);
            LOGGER.info("Generic person encoded to bytes={}", bytes.length);
        });
    }

    @Test
    public void testSpecificRecordIsEncodedToBinary() throws Exception {
        TestPersonDataReader.readSpecificTestPersons().forEach((person)->{
            byte[] bytes = AvroUtils.encodeRecordToBinary(person);
            Assert.assertNotNull(person);
            LOGGER.info("Specific person encoded to bytes={}", bytes.length);
        });
    }

    @Test
    public void testGenericRecordIsEncodedToMap() throws Exception {
        TestPersonDataReader.readGenericTestPersons().forEach((person)->{
            Map<String, Object> map = AvroUtils.encodeRecordToMap(person);
            Assert.assertNotNull(map);
            LOGGER.info("Generic person encoded to map={}", map);
        });
    }

    @Test
    public void testSpecificRecordIsEncodedToMap() throws Exception {
        TestPersonDataReader.readSpecificTestPersons().forEach((person)->{
            Map<String, Object> map = AvroUtils.encodeRecordToMap(person);
            Assert.assertNotNull(map);
            LOGGER.info("Specific person encoded to map={}", map);
        });
    }

    @Test
    public void testFieldIsConvertedToJson() throws Exception {
        String fieldJson = AvroUtils.fieldToJson(TEST_FIELD);

        Assert.assertNotNull(fieldJson);
        Assert.assertTrue(fieldJson.contains(TEST_FIELD_SCHEMA_JSON));
        Assert.assertTrue(fieldJson.contains(TEST_FIELD_DOC));
        Assert.assertTrue(fieldJson.contains(TEST_FIELD_NAME));
        Assert.assertTrue(fieldJson.contains(TEST_FIELD_ORDER.name().toLowerCase()));
    }

    @Test
    public void testJsonIsConvertedToField() throws Exception {
        Field field = AvroUtils.fieldFromJson(TEST_FIELD_JSON);

        Assert.assertNotNull(field);
        Assert.assertEquals(TEST_FIELD_NAME, field.name());
        Assert.assertEquals(TEST_FIELD_DOC, field.doc());
        Assert.assertEquals(TEST_FIELD_ORDER, field.order());
        Assert.assertEquals(TEST_FIELD_SCHEMA, field.schema());
    }

    @Test
    public void testFieldIsConvertedToGeneric() throws Exception {
        Map<String, Object> genericField = AvroUtils.fieldToGeneric(TEST_FIELD);

        Assert.assertNotNull(genericField);
        Assert.assertEquals(TEST_FIELD_NAME, genericField.get(AvroConstants.SCHEMA_KEY_FIELD_NAME));
        Assert.assertEquals(TEST_FIELD_DOC, genericField.get(AvroConstants.SCHEMA_KEY_FIELD_DOC));
        Assert.assertEquals(TEST_FIELD_ORDER, Order.valueOf(((String)genericField.get(AvroConstants.SCHEMA_KEY_FIELD_ORDER)).toUpperCase()));
        Assert.assertNotNull(genericField.get(AvroConstants.SCHEMA_KEY_TYPE));
    }

    @Test
    public void testFieldIsCloned() throws Exception {
        Field cloned = AvroUtils.cloneField(TEST_FIELD, true, true);

        Assert.assertNotNull(cloned);
        Assert.assertEquals(-1, cloned.pos());
        Assert.assertEquals(TEST_FIELD.name(), cloned.name());
        Assert.assertEquals(TEST_FIELD.doc(), cloned.doc());
        Assert.assertEquals(TEST_FIELD.order(), cloned.order());
        Assert.assertEquals(TEST_FIELD.schema(), cloned.schema());
        Assert.assertEquals(TEST_FIELD.aliases(), cloned.aliases());
        Assert.assertEquals(TEST_FIELD.getObjectProps(), cloned.getObjectProps());
    }

    @Test
    public void testFieldNameIsValid() throws Exception {
        Assert.assertTrue(AvroUtils.isFieldNameValid("a_B_c_1_2_3"));
        Assert.assertTrue(AvroUtils.isFieldNameValid("_a"));
    }

    @Test
    public void testFieldNameIsNoValid() throws Exception {
        Assert.assertFalse(AvroUtils.isFieldNameValid("1a"));
        Assert.assertFalse(AvroUtils.isFieldNameValid("a_!"));
    }

    @Test
    public void testAvroPrimitiveIsConvertedToJava() throws Exception {
        Assert.assertEquals("value", AvroUtils.avroPrimitiveToJava(new Utf8("value")));
        Assert.assertEquals(1L, AvroUtils.avroPrimitiveToJava(1L));
    }

    @Test
    public void testJavaPrimitiveIsConvertedToAvro() throws Exception {
        Assert.assertEquals(new Utf8("value"), AvroUtils.javaPrimitiveToAvro("value"));
        Assert.assertEquals(1L, AvroUtils.javaPrimitiveToAvro(1L));
    }

    @Test
    public void testGenericSchemaTypeIsResolved() throws Exception {
        for (Field field : TestPerson.SCHEMA$.getFields()) {
            Object genericSchema = AvroUtils.schemaToGeneric(field.schema());
            Type type = AvroUtils.typeOfGenericSchema(genericSchema);

            Assert.assertEquals(field.schema().getType(), type);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testNullGenericSchemaTypeIsResolvedIfTypeIsUnknown() throws Exception {
        for (Field field : TestPerson.SCHEMA$.getFields()) {
            Object genericSchema = AvroUtils.schemaToGeneric(field.schema());
            if (genericSchema instanceof Map) {
                ((Map<String, Object>)genericSchema).put(AvroConstants.SCHEMA_KEY_TYPE, "unknown");
            } else if (genericSchema instanceof String) {
                genericSchema = "unknown";
            } else {
                continue;
            }

            Type type = AvroUtils.typeOfGenericSchemaOrElseNullIfUnknown(genericSchema);

            Assert.assertNull(type);
        }
    }

    @Test
    public void testGenericFieldTypeIsResolved() throws Exception {
        for (Field field : TestPerson.SCHEMA$.getFields()) {
            Map<String, Object> genericField = AvroUtils.fieldToGeneric(field);
            Type type = AvroUtils.typeOfGenericField(genericField);

            Assert.assertEquals(field.schema().getType(), type);
        }
    }

    @Test
    public void testNullGenericFieldTypeIsResolvedIfTypeIsUnknown() throws Exception {
        for (Field field : TestPerson.SCHEMA$.getFields()) {
            Map<String, Object> genericField = AvroUtils.fieldToGeneric(field);
            genericField.put(AvroConstants.SCHEMA_KEY_FIELD_TYPE, "unknown");
            Type type = AvroUtils.typeOfGenericFieldOrElseNullIfUnknown(genericField);

            Assert.assertNull(type);
        }
    }

    @Test
    public void testTypeResolvedForName() throws Exception {
        Assert.assertEquals(Type.ARRAY, AvroUtils.typeForName("array"));
        Assert.assertEquals(Type.BOOLEAN, AvroUtils.typeForName("boolean"));
        Assert.assertEquals(Type.BYTES, AvroUtils.typeForName("bytes"));
        Assert.assertEquals(Type.DOUBLE, AvroUtils.typeForName("double"));
        Assert.assertEquals(Type.ENUM, AvroUtils.typeForName("enum"));
        Assert.assertEquals(Type.FIXED, AvroUtils.typeForName("fixed"));
        Assert.assertEquals(Type.FLOAT, AvroUtils.typeForName("float"));
        Assert.assertEquals(Type.INT, AvroUtils.typeForName("int"));
        Assert.assertEquals(Type.LONG, AvroUtils.typeForName("long"));
        Assert.assertEquals(Type.MAP, AvroUtils.typeForName("map"));
        Assert.assertEquals(Type.NULL, AvroUtils.typeForName("null"));
        Assert.assertEquals(Type.RECORD, AvroUtils.typeForName("record"));
        Assert.assertEquals(Type.STRING, AvroUtils.typeForName("string"));
        Assert.assertEquals(Type.UNION, AvroUtils.typeForName("union"));
    }

    @Test(expected = UnknownTypeException.class)
    public void testTypeNotResolvedForName() throws Exception {
        AvroUtils.typeForName("unknown");
    }

    @Test
    public void testTypeResolvedForNameIgnoreCase() throws Exception {
        Assert.assertEquals(Type.ARRAY, AvroUtils.typeForNameIgnoreCase("aRraY"));
        Assert.assertEquals(Type.BOOLEAN, AvroUtils.typeForNameIgnoreCase("bOOlean"));
        Assert.assertEquals(Type.BYTES, AvroUtils.typeForNameIgnoreCase("bytEs"));
        Assert.assertEquals(Type.DOUBLE, AvroUtils.typeForNameIgnoreCase("dOUble"));
        Assert.assertEquals(Type.ENUM, AvroUtils.typeForNameIgnoreCase("EnuM"));
        Assert.assertEquals(Type.FIXED, AvroUtils.typeForNameIgnoreCase("fIXEd"));
        Assert.assertEquals(Type.FLOAT, AvroUtils.typeForNameIgnoreCase("flOat"));
        Assert.assertEquals(Type.INT, AvroUtils.typeForNameIgnoreCase("inT"));
        Assert.assertEquals(Type.LONG, AvroUtils.typeForNameIgnoreCase("Long"));
        Assert.assertEquals(Type.MAP, AvroUtils.typeForNameIgnoreCase("mAP"));
        Assert.assertEquals(Type.NULL, AvroUtils.typeForNameIgnoreCase("nulL"));
        Assert.assertEquals(Type.RECORD, AvroUtils.typeForNameIgnoreCase("RecorD"));
        Assert.assertEquals(Type.STRING, AvroUtils.typeForNameIgnoreCase("sTRing"));
        Assert.assertEquals(Type.UNION, AvroUtils.typeForNameIgnoreCase("UNION"));
    }

    @Test(expected = UnknownTypeException.class)
    public void testTypeNotResolvedForNameIgnoreCase() throws Exception {
        AvroUtils.typeForNameIgnoreCase("UnKnOwN");
    }

    @Test
    public void testEffectiveTypeOfGenericSchemaResolved() throws Exception {
        Object schema = Type.INT.getName();
        Assert.assertEquals(Type.INT, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.recordSchema("Test");
        Assert.assertEquals(Type.RECORD, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.arraySchema(Type.STRING.getName());
        Assert.assertEquals(Type.STRING, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.arraySchema(GenericSchemaDataGen.unionSchema(Type.FIXED.getName()));
        Assert.assertEquals(Type.FIXED, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.arraySchema(GenericSchemaDataGen.unionSchema(Type.NULL.getName(), Type.BYTES.getName()));
        Assert.assertEquals(Type.BYTES, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.arraySchema(GenericSchemaDataGen.unionSchema(Type.STRING.getName(), Type.DOUBLE.getName()));
        Assert.assertEquals(Type.UNION, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.mapSchema(Type.BOOLEAN.getName());
        Assert.assertEquals(Type.BOOLEAN, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.mapSchema(GenericSchemaDataGen.unionSchema(Type.FIXED.getName()));
        Assert.assertEquals(Type.FIXED, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.mapSchema(GenericSchemaDataGen.unionSchema(Type.NULL.getName(), Type.BYTES.getName()));
        Assert.assertEquals(Type.BYTES, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.mapSchema(GenericSchemaDataGen.unionSchema(Type.STRING.getName(), Type.DOUBLE.getName()));
        Assert.assertEquals(Type.UNION, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.unionSchema(Type.FLOAT.getName());
        Assert.assertEquals(Type.FLOAT, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.unionSchema(Type.NULL.getName(), Type.BOOLEAN.getName());
        Assert.assertEquals(Type.BOOLEAN, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.unionSchema(Type.STRING.getName(), Type.BOOLEAN.getName());
        Assert.assertEquals(Type.UNION, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.unionSchema(Type.STRING.getName(), Type.BOOLEAN.getName(), Type.BYTES.getName());
        Assert.assertEquals(Type.UNION, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.unionSchema(
                GenericSchemaDataGen.arraySchema(GenericSchemaDataGen.recordSchema("Test")));
        Assert.assertEquals(Type.RECORD, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.unionSchema(
                Type.NULL.getName(),
                GenericSchemaDataGen.mapSchema(GenericSchemaDataGen.arraySchema(Type.BYTES.getName())));
        Assert.assertEquals(Type.BYTES, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.unionSchema(
                GenericSchemaDataGen.arraySchema(Type.BOOLEAN.getName()),
                GenericSchemaDataGen.mapSchema(Type.ENUM.getName()));
        Assert.assertEquals(Type.UNION, AvroUtils.effectiveTypeOfGenericSchema(schema));
    }

}
