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
package com.epam.eco.commons.avro.converter;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;

import com.epam.eco.commons.avro.AvroUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ihar_Karoza
 */
public class DefaultAvroConvertersTest {

    private static final Schema VALUE_SCHEMA = AvroUtils.schemaFromResource("/broad_schema_with_null.avsc");

    private static final Map<String, String> AVRO_MAP = new HashMap<>();

    static {
        AVRO_MAP.put("str_field", "str_val");
        AVRO_MAP.put("str_null_field", null);

    }

    private final Map<String, Object> value = ImmutableMap.<String, Object>builder()
            .put("int_field", 1)
            .put("long_field", 1L)
            .put("float_field", (float) 0.1)
            .put("double_field", 0.1)
            .put("boolean_field", Boolean.TRUE)
            .put("string_field", "test")
            .put("bytes_field", ByteBuffer.wrap("test".getBytes()))
            .put("collection_of_string_field", Arrays.asList("e1", "e2", null))
            .put("date_field", LocalDate.of(2018, 7, 10))
            .put("date_field_int", 10000)
            .put("date_field_ldt", LocalDateTime.parse("2024-09-27T10:03:01"))
            .put("time_field", LocalTime.of(11, 30))
            .put("time_field_int", 10000)
            .put("time_field_ldt", LocalDateTime.parse("2024-09-27T10:03:01"))
            .put("datetime_field", LocalDateTime.of(2018, 7, 10, 11, 30))
            .put("datetime_field_ld", LocalDate.parse("2024-09-27"))
            .put("collection_of_map_field", Collections.singletonList(ImmutableMap.of("int_field", 1, "string_field", "test")))
            .put("map_field", ImmutableMap.<String, Object>builder()
                    .put("int_field", 1)
                    .put("long_field", 1L)
                    .put("float_field", (float) 0.1)
                    .put("double_field", 0.1)
                    .put("boolean_field", Boolean.TRUE)
                    .put("string_field", "test")
                    .put("bytes_field", ByteBuffer.wrap("test".getBytes()))
                    .put("collection_of_string_field", Arrays.asList("e1", "e2", null))
                    .put("embedded_collection_of_map_field", Collections.singletonList(ImmutableMap.of("int_field", 1, "string_field", "test")))
                    .put("embedded_map_field", ImmutableMap.of("int_field", 1, "string_field", "test"))
                    .build())
            .put("opt_avro_map", AVRO_MAP)
            .build();


    private final DefaultAvroConverters converters = new DefaultAvroConverters();

    @Test
    @SuppressWarnings("rawtypes")
    public void convertToAvroTest() {
        GenericRecord convertedData = (GenericRecord) converters.toAvro(value, VALUE_SCHEMA);

        Assertions.assertEquals(convertedData.get("int_field"), 1);
        Assertions.assertEquals(convertedData.get("long_field"), 1L);
        Assertions.assertEquals(convertedData.get("float_field"), (float) 0.1);
        Assertions.assertEquals(convertedData.get("double_field"), 0.1);
        Assertions.assertEquals(convertedData.get("boolean_field"), Boolean.TRUE);
        Assertions.assertEquals(convertedData.get("string_field"), "test");
        Assertions.assertEquals(new String(((ByteBuffer) convertedData.get("bytes_field")).array()), "test");
        Assertions.assertEquals(convertedData.get("date_field"), (int) LocalDate.of(2018, 7, 10).toEpochDay());
        Assertions.assertEquals(convertedData.get("date_field_int"), 10000);
        Assertions.assertEquals(convertedData.get("date_field_ldt"), (int) LocalDateTime.parse("2024-09-27T10:03:01").toLocalDate().toEpochDay());
        Assertions.assertEquals(convertedData.get("time_field"), LocalTime.of(11, 30).toSecondOfDay());
        Assertions.assertEquals(convertedData.get("time_field_int"), 10000);
        Assertions.assertEquals(convertedData.get("time_field_ldt"), LocalDateTime.parse("2024-09-27T10:03:01").toLocalTime().toSecondOfDay());
        Assertions.assertEquals(convertedData.get("datetime_field"), LocalDateTime.of(2018, 7, 10, 11, 30).atOffset(ZoneOffset.UTC).toInstant().toEpochMilli());
        Assertions.assertEquals(convertedData.get("datetime_field_ld"), LocalDate.parse("2024-09-27").atStartOfDay(ZoneId.of("UTC")).toInstant().toEpochMilli());
        Assertions.assertEquals(convertedData.get("collection_of_string_field"), Arrays.asList("e1", "e2", null));

        GenericRecord collectionOfMapElement = (GenericRecord) ((List) convertedData.get("collection_of_map_field")).get(0);
        Assertions.assertEquals(collectionOfMapElement.get("int_field"), 1);
        Assertions.assertEquals(collectionOfMapElement.get("string_field"), "test");

        GenericRecord mapField = (GenericRecord) convertedData.get("map_field");
        Assertions.assertEquals(mapField.get("int_field"), 1);
        Assertions.assertEquals(mapField.get("long_field"), 1L);
        Assertions.assertEquals(mapField.get("float_field"), (float) 0.1);
        Assertions.assertEquals(mapField.get("double_field"), 0.1);
        Assertions.assertEquals(mapField.get("boolean_field"), Boolean.TRUE);
        Assertions.assertEquals(mapField.get("string_field"), "test");
        Assertions.assertEquals(new String(((ByteBuffer) mapField.get("bytes_field")).array()), "test");
        Assertions.assertEquals(mapField.get("collection_of_string_field"), Arrays.asList("e1", "e2", null));

        GenericRecord embeddedCollectionOfMapElement = (GenericRecord) ((List) mapField.get("embedded_collection_of_map_field")).get(0);
        Assertions.assertEquals(embeddedCollectionOfMapElement.get("int_field"), 1);
        Assertions.assertEquals(embeddedCollectionOfMapElement.get("string_field"), "test");

        GenericRecord embeddedMapField = (GenericRecord) mapField.get("embedded_map_field");
        Assertions.assertEquals(embeddedMapField.get("int_field"), 1);
        Assertions.assertEquals(embeddedMapField.get("string_field"), "test");

        @SuppressWarnings("unchecked")
        Map<String, Object> optAvroMap = (HashMap<String, Object>) convertedData.get("opt_avro_map");
        Assertions.assertEquals("str_val", optAvroMap.get("str_field"));
        Assertions.assertNull(optAvroMap.get("str_null_field"));
    }

    @Test
    public void convertNullToNullableTest() {
        converters.toAvro(null, SchemaBuilder.builder().nullable().intType());
    }

    @Test
    public void convertNullToNotNullableTest() {
        assertThrows(
                AvroConversionException.class,
                () -> converters.toAvro(null, SchemaBuilder.builder().intType())
        );
    }

    @Test
    public void convertToIncompatibleSchemaTest() {
        Map<String, Object> valueMap = ImmutableMap.<String, Object>builder()
                .put("field_0", "test")
                .put("field_1", "test")
                .put("field_2", "test")
                .build();

        Schema schema = SchemaBuilder.builder()
                .record("schema")
                .fields()
                .name("field_0").type().stringType().noDefault()
                .endRecord();

        assertThrows(
                AvroConversionException.class,
                () -> converters.toAvro(valueMap, schema)
        );
    }

    @Test
    public void convertsUnionOfDifferentTypes() {
        // Create schema programmatically
        Schema schema = SchemaBuilder.record("schema")
                .fields()
                .name("f1").type()
                .unionOf()
                    .intType()
                    .and()
                    .stringType()
                .endUnion()
                .noDefault()
                .endRecord();

        // Prepare test data
        Map<String, Object> valueMap = Map.of("f1", "strValue");

        // Convert and verify
        DefaultAvroConverters converters = new DefaultAvroConverters();
        GenericRecord genericRecord1 = (GenericRecord) converters.toAvro(valueMap, schema);
        Assertions.assertEquals("strValue", genericRecord1.get("f1").toString());

        Map<String, Object> valueMap1 = Map.of("f1", 1);

        GenericRecord genericRecord2 = (GenericRecord) converters.toAvro(valueMap1, schema);
        Assertions.assertEquals(1, genericRecord2.get("f1"));
    }

}
