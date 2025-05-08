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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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

    private static final Schema LOCAL_TIMESTAMP_VALUE_TEST_SCHEMA = AvroUtils.schemaFromResource("/local_timestamp_mills_test.avsc");
    private static final Schema LOCAL_TIMESTAMP_MICROS_TEST_SCHEMA = AvroUtils.schemaFromResource("/local_timestamp_micros_test.avsc");

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


    @Test
    public void testLocalTimestampMillis() {
        // given
        DefaultAvroConverters converters = new DefaultAvroConverters();

        LocalDateTime ldtExample = LocalDateTime.of(2023, 9, 1, 12, 34, 56);
        long ldtMillis = ldtExample.toInstant(ZoneOffset.UTC).toEpochMilli();
        OffsetDateTime odtExample = OffsetDateTime.of(2023, 9, 1, 12, 34, 56, 0, ZoneOffset.UTC);
        long odtMillis = odtExample.toInstant().toEpochMilli();
        String odtString = "2023-09-01T12:34:56Z";
        long odtStringMillis = Instant.parse(odtString).toEpochMilli();
        String ldtString = "2023-09-01T12:34:56";
        long ldtStringMillis = LocalDateTime.parse(ldtString).toInstant(ZoneOffset.UTC).toEpochMilli();
        long numericMillis = 1693281600000L; // e.g., 2023-08-29
        long dateMillis = 1693281600000L;    // same time as numericMillis

        Map<String, Object> recordData = new HashMap<>();
        recordData.put("local_timestamp_millis_ldt", ldtExample);
        recordData.put("local_timestamp_millis_odt", odtExample);
        recordData.put("local_timestamp_millis_str_odt", odtString);
        recordData.put("local_timestamp_millis_str_ldt", ldtString);
        recordData.put("local_timestamp_millis_num", numericMillis);
        recordData.put("local_timestamp_millis_date", new Date(dateMillis));

        // when
        GenericRecord result = (GenericRecord) converters.toAvro(recordData, LOCAL_TIMESTAMP_VALUE_TEST_SCHEMA);

        // then - verify each field matches the expected epoch milli value:
        Assertions.assertEquals(ldtMillis, result.get("local_timestamp_millis_ldt"),
                "Should convert LocalDateTime to epoch millis");
        Assertions.assertEquals(odtMillis, result.get("local_timestamp_millis_odt"),
                "Should convert OffsetDateTime to epoch millis");
        Assertions.assertEquals(odtStringMillis, result.get("local_timestamp_millis_str_odt"),
                "Should parse offset-style string to epoch millis");
        Assertions.assertEquals(ldtStringMillis, result.get("local_timestamp_millis_str_ldt"),
                "Should parse local-style string to epoch millis");
        Assertions.assertEquals(numericMillis, result.get("local_timestamp_millis_num"),
                "Should accept numeric epoch millis");
        Assertions.assertEquals(dateMillis, result.get("local_timestamp_millis_date"),
                "Should accept Date object and convert it to epoch millis");
    }


    @Test
    public void testLocalTimestampMicros() {
        // given
        DefaultAvroConverters converters = new DefaultAvroConverters();

        LocalDateTime ldt = LocalDateTime.of(2023, 9, 2, 10, 30, 0);

        long ldtMicros = ChronoUnit.MICROS.between(Instant.EPOCH, ldt.toInstant(ZoneOffset.UTC));

        OffsetDateTime odt = OffsetDateTime.of(2023, 9, 2, 10, 30, 0, 0, ZoneOffset.UTC);
        long odtMicros = ChronoUnit.MICROS.between(Instant.EPOCH, odt.toInstant());

        // Strings parseable as offset or local
        String odtString = "2023-09-02T10:30:00Z";
        long odtStringMicros = ChronoUnit.MICROS.between(Instant.EPOCH, Instant.parse(odtString));
        String ldtString = "2023-09-02T10:30:00";
        long ldtStringMicros = ChronoUnit.MICROS.between(
                Instant.EPOCH,
                LocalDateTime.parse(ldtString).toInstant(ZoneOffset.UTC)
        );

        long numericMicros = 1693644600000000L; // e.g., "2023-09-02T10:30:00Z" in microseconds
        // Date: note that this date is in ms, so to get micros from it in the converter,
        // it will do date.getTime() * 1000
        Date dateValue = new Date(numericMicros / 1000);

        Map<String, Object> recordData = new HashMap<>();
        recordData.put("local_timestamp_micros_ldt", ldt);
        recordData.put("local_timestamp_micros_odt", odt);
        recordData.put("local_timestamp_micros_str_odt", odtString);
        recordData.put("local_timestamp_micros_str_ldt", ldtString);
        recordData.put("local_timestamp_micros_num", numericMicros);
        recordData.put("local_timestamp_micros_date", dateValue);

        // when
        GenericRecord record = (GenericRecord) converters.toAvro(recordData, LOCAL_TIMESTAMP_MICROS_TEST_SCHEMA);

        // then - verify each field matched the correct microsecond value:
        Assertions.assertNotNull(record, "Converted record should not be null");
        Assertions.assertEquals(ldtMicros, record.get("local_timestamp_micros_ldt"));
        Assertions.assertEquals(odtMicros, record.get("local_timestamp_micros_odt"));
        Assertions.assertEquals(odtStringMicros, record.get("local_timestamp_micros_str_odt"));
        Assertions.assertEquals(ldtStringMicros, record.get("local_timestamp_micros_str_ldt"));
        Assertions.assertEquals(numericMicros, record.get("local_timestamp_micros_num"));
        Assertions.assertEquals(numericMicros, record.get("local_timestamp_micros_date"));
    }

}
