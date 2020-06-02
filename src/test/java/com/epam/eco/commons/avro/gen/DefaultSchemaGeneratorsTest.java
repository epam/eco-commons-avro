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
package com.epam.eco.commons.avro.gen;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.apache.avro.Schema;
import org.junit.Assert;
import org.junit.Test;

import com.epam.eco.commons.avro.AvroUtils;

import avro.shaded.com.google.common.collect.ImmutableMap;

/**
 * @author Ihar_Karoza
 */
public class DefaultSchemaGeneratorsTest {

    private static final Schema VALUE_SCHEMA = AvroUtils.schemaFromResource("/broad_schema.avsc");

    private Map<String, Object> value = ImmutableMap.<String, Object>builder()
                                                    .put("int_field", 1)
                                                    .put("long_field", 1L)
                                                    .put("float_field", (float) 0.1)
                                                    .put("double_field", 0.1)
                                                    .put("boolean_field", Boolean.TRUE)
                                                    .put("string_field", "test")
                                                    .put("bytes_field", ByteBuffer.wrap("test".getBytes()))
                                                    .put("date_field", LocalDate.now())
                                                    .put("time_field", LocalTime.now())
                                                    .put("datetime_field", LocalDateTime.now())
                                                    .put("collection_of_string_field", Arrays.asList("e1", "e2"))
                                                    .put("collection_of_map_field", Collections.singletonList(ImmutableMap.of("int_field", 1, "string_field", "test")))
                                                    .put("map_field", ImmutableMap.<String, Object>builder()
                                                                                  .put("int_field", 1)
                                                                                  .put("long_field", 1L)
                                                                                  .put("float_field", (float) 0.1)
                                                                                  .put("double_field", 0.1)
                                                                                  .put("boolean_field", Boolean.TRUE)
                                                                                  .put("string_field", "test")
                                                                                  .put("bytes_field", ByteBuffer.wrap("test".getBytes()))
                                                                                  .put("collection_of_string_field", Arrays.asList("e1", "e2"))
                                                                                  .put("embedded_collection_of_map_field", Collections.singletonList(ImmutableMap.of("int_field", 1, "string_field", "test")))
                                                                                  .put("embedded_map_field", ImmutableMap.of("int_field", 1, "string_field", "test"))
                                                                                  .build())
                                                    .build();

    @Test
    public void buildSchemaTest() {
        Schema genSchema = new DefaultSchemaGenerators().createForValue(value, "BroadSchema", "test.commons.avro");
        Schema resolvedGenSchema = AvroUtils.resolveAnyNonNullable(genSchema);
        Assert.assertEquals(resolvedGenSchema, VALUE_SCHEMA);
    }

}
