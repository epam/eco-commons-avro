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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.eco.commons.avro.data.TestPerson;
import com.epam.eco.commons.avro.data.TestPersonDataReader;

import static org.junit.jupiter.api.Assertions.assertThrows;


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
    public void testGenericRecordIsDecodedFromBytes() {
        TestPersonDataReader.readBinaryTestPersons().forEach((bytes)->{
            GenericRecord record = AvroUtils.decodeRecordFromBinary(bytes, TestPerson.SCHEMA$, false);
            Assertions.assertNotNull(record);
            LOGGER.info("Generic person decoded from bytes={}", record);
        });
    }

    @Test
    public void testSpecificRecordIsDecodedFromBytes() {
        TestPersonDataReader.readBinaryTestPersons().forEach((bytes)->{
            TestPerson record = (TestPerson)AvroUtils.decodeRecordFromBinary(bytes, TestPerson.SCHEMA$, true);
            Assertions.assertNotNull(record);
            LOGGER.info("Specific person decoded from bytes={}", record);
        });
    }

    @Test
    public void testGenericRecordIsDecodedFromJson() {
        TestPersonDataReader.readJsonTestPersons().forEach((json)->{
            GenericRecord record = AvroUtils.decodeRecordFromJson(json, TestPerson.SCHEMA$, false);
            Assertions.assertNotNull(record);
            LOGGER.info("Generic person decoded from json={}", record);
        });
    }

    @Test
    public void testSpecificRecordIsDecodedFromJson() {
        TestPersonDataReader.readJsonTestPersons().forEach((json)->{
            TestPerson record = (TestPerson)AvroUtils.decodeRecordFromJson(json, TestPerson.SCHEMA$, true);
            Assertions.assertNotNull(record);
            LOGGER.info("Specific person decoded from json={}", record);
        });
    }

    @Test
    public void testGenericRecordIsEncodedToJson() {
        TestPersonDataReader.readGenericTestPersons().forEach((person)->{
            String json = AvroUtils.encodeRecordToJson(person);
            Assertions.assertNotNull(json);
            LOGGER.info("Generic person encoded to JSON={}", json);
        });
    }

    @Test
    public void testSpecificRecordIsEncodedToJson() {
        TestPersonDataReader.readSpecificTestPersons().forEach((person)->{
            String json = AvroUtils.encodeRecordToJson(person);
            Assertions.assertNotNull(json);
            LOGGER.info("Specific person encoded to JSON={}", json);
        });
    }

    @Test
    public void testGenericRecordIsEncodedToBinary() {
        TestPersonDataReader.readGenericTestPersons().forEach((person)->{
            byte[] bytes = AvroUtils.encodeRecordToBinary(person);
            Assertions.assertNotNull(person);
            LOGGER.info("Generic person encoded to bytes={}", bytes.length);
        });
    }

    @Test
    public void testSpecificRecordIsEncodedToBinary() {
        TestPersonDataReader.readSpecificTestPersons().forEach((person)->{
            byte[] bytes = AvroUtils.encodeRecordToBinary(person);
            Assertions.assertNotNull(person);
            LOGGER.info("Specific person encoded to bytes={}", bytes.length);
        });
    }

    @Test
    public void testGenericRecordIsEncodedToMap() {
        TestPersonDataReader.readGenericTestPersons().forEach((person)->{
            Map<String, Object> map = AvroUtils.encodeRecordToMap(person);
            Assertions.assertNotNull(map);
            LOGGER.info("Generic person encoded to map={}", map);
        });
    }

    @Test
    public void testSpecificRecordIsEncodedToMap() {
        TestPersonDataReader.readSpecificTestPersons().forEach((person)->{
            Map<String, Object> map = AvroUtils.encodeRecordToMap(person);
            Assertions.assertNotNull(map);
            LOGGER.info("Specific person encoded to map={}", map);
        });
    }

    @Test
    public void testFieldIsConvertedToJson() {
        String fieldJson = AvroUtils.fieldToJson(TEST_FIELD);

        Assertions.assertNotNull(fieldJson);
        Assertions.assertTrue(fieldJson.contains(TEST_FIELD_SCHEMA_JSON));
        Assertions.assertTrue(fieldJson.contains(TEST_FIELD_DOC));
        Assertions.assertTrue(fieldJson.contains(TEST_FIELD_NAME));
        Assertions.assertTrue(fieldJson.contains(TEST_FIELD_ORDER.name().toLowerCase()));
    }

    @Test
    public void testJsonIsConvertedToField() {
        Field field = AvroUtils.fieldFromJson(TEST_FIELD_JSON);

        Assertions.assertNotNull(field);
        Assertions.assertEquals(TEST_FIELD_NAME, field.name());
        Assertions.assertEquals(TEST_FIELD_DOC, field.doc());
        Assertions.assertEquals(TEST_FIELD_ORDER, field.order());
        Assertions.assertEquals(TEST_FIELD_SCHEMA, field.schema());
    }

    @Test
    public void testFieldIsConvertedToGeneric() {
        Map<String, Object> genericField = AvroUtils.fieldToGeneric(TEST_FIELD);

        Assertions.assertNotNull(genericField);
        Assertions.assertEquals(TEST_FIELD_NAME, genericField.get(AvroConstants.SCHEMA_KEY_FIELD_NAME));
        Assertions.assertEquals(TEST_FIELD_DOC, genericField.get(AvroConstants.SCHEMA_KEY_FIELD_DOC));
        Assertions.assertEquals(TEST_FIELD_ORDER, Order.valueOf(((String)genericField.get(AvroConstants.SCHEMA_KEY_FIELD_ORDER)).toUpperCase()));
        Assertions.assertNotNull(genericField.get(AvroConstants.SCHEMA_KEY_TYPE));
    }

    @Test
    public void testFieldIsCloned() {
        Field cloned = AvroUtils.cloneField(TEST_FIELD, true, true);

        Assertions.assertNotNull(cloned);
        Assertions.assertEquals(-1, cloned.pos());
        Assertions.assertEquals(TEST_FIELD.name(), cloned.name());
        Assertions.assertEquals(TEST_FIELD.doc(), cloned.doc());
        Assertions.assertEquals(TEST_FIELD.order(), cloned.order());
        Assertions.assertEquals(TEST_FIELD.schema(), cloned.schema());
        Assertions.assertEquals(TEST_FIELD.aliases(), cloned.aliases());
        Assertions.assertEquals(TEST_FIELD.getObjectProps(), cloned.getObjectProps());
    }

    @Test
    public void testFieldNameIsValid() {
        Assertions.assertTrue(AvroUtils.isFieldNameValid("a_B_c_1_2_3"));
        Assertions.assertTrue(AvroUtils.isFieldNameValid("_a"));
    }

    @Test
    public void testFieldNameIsNoValid() {
        Assertions.assertFalse(AvroUtils.isFieldNameValid("1a"));
        Assertions.assertFalse(AvroUtils.isFieldNameValid("a_!"));
    }

    @Test
    public void testAvroPrimitiveIsConvertedToJava() {
        Assertions.assertEquals("value", AvroUtils.avroPrimitiveToJava(new Utf8("value")));
        Assertions.assertEquals(1L, AvroUtils.avroPrimitiveToJava(1L));
    }

    @Test
    public void testJavaPrimitiveIsConvertedToAvro() {
        Assertions.assertEquals(new Utf8("value"), AvroUtils.javaPrimitiveToAvro("value"));
        Assertions.assertEquals(1L, AvroUtils.javaPrimitiveToAvro(1L));
    }

    @Test
    public void testGenericSchemaTypeIsResolved() {
        for (Field field : TestPerson.SCHEMA$.getFields()) {
            Object genericSchema = AvroUtils.schemaToGeneric(field.schema());
            Type type = AvroUtils.typeOfGenericSchema(genericSchema);

            Assertions.assertEquals(field.schema().getType(), type);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testNullGenericSchemaTypeIsResolvedIfTypeIsUnknown() {
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

            Assertions.assertNull(type);
        }
    }

    @Test
    public void testGenericFieldTypeIsResolved() {
        for (Field field : TestPerson.SCHEMA$.getFields()) {
            Map<String, Object> genericField = AvroUtils.fieldToGeneric(field);
            Type type = AvroUtils.typeOfGenericField(genericField);

            Assertions.assertEquals(field.schema().getType(), type);
        }
    }

    @Test
    public void testNullGenericFieldTypeIsResolvedIfTypeIsUnknown() {
        for (Field field : TestPerson.SCHEMA$.getFields()) {
            Map<String, Object> genericField = AvroUtils.fieldToGeneric(field);
            genericField.put(AvroConstants.SCHEMA_KEY_FIELD_TYPE, "unknown");
            Type type = AvroUtils.typeOfGenericFieldOrElseNullIfUnknown(genericField);

            Assertions.assertNull(type);
        }
    }

    @Test
    public void testTypeResolvedForName() {
        Assertions.assertEquals(Type.ARRAY, AvroUtils.typeForName("array"));
        Assertions.assertEquals(Type.BOOLEAN, AvroUtils.typeForName("boolean"));
        Assertions.assertEquals(Type.BYTES, AvroUtils.typeForName("bytes"));
        Assertions.assertEquals(Type.DOUBLE, AvroUtils.typeForName("double"));
        Assertions.assertEquals(Type.ENUM, AvroUtils.typeForName("enum"));
        Assertions.assertEquals(Type.FIXED, AvroUtils.typeForName("fixed"));
        Assertions.assertEquals(Type.FLOAT, AvroUtils.typeForName("float"));
        Assertions.assertEquals(Type.INT, AvroUtils.typeForName("int"));
        Assertions.assertEquals(Type.LONG, AvroUtils.typeForName("long"));
        Assertions.assertEquals(Type.MAP, AvroUtils.typeForName("map"));
        Assertions.assertEquals(Type.NULL, AvroUtils.typeForName("null"));
        Assertions.assertEquals(Type.RECORD, AvroUtils.typeForName("record"));
        Assertions.assertEquals(Type.STRING, AvroUtils.typeForName("string"));
        Assertions.assertEquals(Type.UNION, AvroUtils.typeForName("union"));
    }

    @Test
    public void testTypeNotResolvedForName() {
        assertThrows(
                UnknownTypeException.class,
                () -> AvroUtils.typeForName("unknown")
        );
    }

    @Test
    public void testTypeResolvedForNameIgnoreCase() {
        Assertions.assertEquals(Type.ARRAY, AvroUtils.typeForNameIgnoreCase("aRraY"));
        Assertions.assertEquals(Type.BOOLEAN, AvroUtils.typeForNameIgnoreCase("bOOlean"));
        Assertions.assertEquals(Type.BYTES, AvroUtils.typeForNameIgnoreCase("bytEs"));
        Assertions.assertEquals(Type.DOUBLE, AvroUtils.typeForNameIgnoreCase("dOUble"));
        Assertions.assertEquals(Type.ENUM, AvroUtils.typeForNameIgnoreCase("EnuM"));
        Assertions.assertEquals(Type.FIXED, AvroUtils.typeForNameIgnoreCase("fIXEd"));
        Assertions.assertEquals(Type.FLOAT, AvroUtils.typeForNameIgnoreCase("flOat"));
        Assertions.assertEquals(Type.INT, AvroUtils.typeForNameIgnoreCase("inT"));
        Assertions.assertEquals(Type.LONG, AvroUtils.typeForNameIgnoreCase("Long"));
        Assertions.assertEquals(Type.MAP, AvroUtils.typeForNameIgnoreCase("mAP"));
        Assertions.assertEquals(Type.NULL, AvroUtils.typeForNameIgnoreCase("nulL"));
        Assertions.assertEquals(Type.RECORD, AvroUtils.typeForNameIgnoreCase("RecorD"));
        Assertions.assertEquals(Type.STRING, AvroUtils.typeForNameIgnoreCase("sTRing"));
        Assertions.assertEquals(Type.UNION, AvroUtils.typeForNameIgnoreCase("UNION"));
    }

    @Test
    public void testTypeNotResolvedForNameIgnoreCase() {
        assertThrows(
                UnknownTypeException.class,
                () -> AvroUtils.typeForNameIgnoreCase("UnKnOwN")
        );
    }

    @Test
    public void testEffectiveTypeOfGenericSchemaResolved() {
        Object schema = Type.INT.getName();
        Assertions.assertEquals(Type.INT, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.recordSchema("Test");
        Assertions.assertEquals(Type.RECORD, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.arraySchema(Type.STRING.getName());
        Assertions.assertEquals(Type.STRING, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.arraySchema(GenericSchemaDataGen.unionSchema(Type.FIXED.getName()));
        Assertions.assertEquals(Type.FIXED, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.arraySchema(GenericSchemaDataGen.unionSchema(Type.NULL.getName(), Type.BYTES.getName()));
        Assertions.assertEquals(Type.BYTES, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.arraySchema(GenericSchemaDataGen.unionSchema(Type.STRING.getName(), Type.DOUBLE.getName()));
        Assertions.assertEquals(Type.UNION, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.mapSchema(Type.BOOLEAN.getName());
        Assertions.assertEquals(Type.BOOLEAN, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.mapSchema(GenericSchemaDataGen.unionSchema(Type.FIXED.getName()));
        Assertions.assertEquals(Type.FIXED, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.mapSchema(GenericSchemaDataGen.unionSchema(Type.NULL.getName(), Type.BYTES.getName()));
        Assertions.assertEquals(Type.BYTES, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.mapSchema(GenericSchemaDataGen.unionSchema(Type.STRING.getName(), Type.DOUBLE.getName()));
        Assertions.assertEquals(Type.UNION, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.unionSchema(Type.FLOAT.getName());
        Assertions.assertEquals(Type.FLOAT, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.unionSchema(Type.NULL.getName(), Type.BOOLEAN.getName());
        Assertions.assertEquals(Type.BOOLEAN, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.unionSchema(Type.STRING.getName(), Type.BOOLEAN.getName());
        Assertions.assertEquals(Type.UNION, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.unionSchema(Type.STRING.getName(), Type.BOOLEAN.getName(), Type.BYTES.getName());
        Assertions.assertEquals(Type.UNION, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.unionSchema(
                GenericSchemaDataGen.arraySchema(GenericSchemaDataGen.recordSchema("Test")));
        Assertions.assertEquals(Type.RECORD, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.unionSchema(
                Type.NULL.getName(),
                GenericSchemaDataGen.mapSchema(GenericSchemaDataGen.arraySchema(Type.BYTES.getName())));
        Assertions.assertEquals(Type.BYTES, AvroUtils.effectiveTypeOfGenericSchema(schema));

        schema = GenericSchemaDataGen.unionSchema(
                GenericSchemaDataGen.arraySchema(Type.BOOLEAN.getName()),
                GenericSchemaDataGen.mapSchema(Type.ENUM.getName()));
        Assertions.assertEquals(Type.UNION, AvroUtils.effectiveTypeOfGenericSchema(schema));
    }

}
