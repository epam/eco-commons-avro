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
package com.epam.eco.commons.avro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.avro.Schema.Type;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Andrei_Tytsik
 */
public abstract class GenericSchemaDataGen {

    private static final List<Type> PRIMITIVE_TYPES = new ArrayList<>(AvroUtils.PRIMITIVE_TYPES);
    static {
        PRIMITIVE_TYPES.remove(Type.NULL);
    }

    public static List<Map<String, Object>> randomFields(int size) {
        List<Map<String, Object>> fields = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fields.add(randomField("f" + i));
        }
        Collections.shuffle(fields);
        return fields;
    }

    public static Map<String, Object> randomField() {
        return randomField(null);
    }

    public static Map<String, Object> randomField(String name) {
        Map<String, Object> field = new HashMap<>();

        name = !StringUtils.isBlank(name) ? name : RandomStringUtils.randomAlphabetic(20);
        field.put(AvroConstants.SCHEMA_KEY_FIELD_NAME, name);

        field.put(AvroConstants.SCHEMA_KEY_FIELD_TYPE, randomSchema());

        return field;
    }

    public static Object randomSchema() {
        return randomSchema(false);
    }

    public static Object randomSchema(boolean excludeUnion) {
        int random = ThreadLocalRandom.current().nextInt(100);
        if (random > 80) {
            return randomRecordSchema();
        } else if (random > 60) {
            return randomMapSchema();
        } else if (random > 40) {
            return randomArraySchema();
        } else if (random > 20 && !excludeUnion) {
            return randomUnionSchema();
        } else {
            return randomPrimitiveSchema();
        }
    }

    public static Object randomPrimitiveSchema() {
        return PRIMITIVE_TYPES.get(ThreadLocalRandom.current().nextInt(PRIMITIVE_TYPES.size())).getName();
    }

    public static Object randomMapSchema() {
        return mapSchema(randomSchema());
    }

    public static Object randomArraySchema() {
        return arraySchema(randomSchema());
    }

    public static Object randomRecordSchema() {
        return recordSchema();
    }

    public static Object randomUnionSchema() {
        int random = ThreadLocalRandom.current().nextInt(100);
        if (random > 80) { // single
            return unionSchema(randomSchema(true));
        } else if (random > 50) { // nullable
            return unionSchema(Type.NULL.getName(), randomSchema(true));
        } else { // non-nullable
            return unionSchema(randomSchema(true), randomSchema(true));
        }
    }

    public static Object unionSchema(Object ... types) {
        List<Object> unionSchema = new ArrayList<>(types.length);
        for (Object type : types) {
            unionSchema.add(type);
        }
        return unionSchema;
    }

    public static Object mapSchema(Object type) {
        Map<String, Object> mapSchema = new HashMap<>();
        mapSchema.put(AvroConstants.SCHEMA_KEY_TYPE, Type.MAP.getName());
        mapSchema.put(AvroConstants.SCHEMA_KEY_MAP_VALUES, type);
        return mapSchema;
    }

    public static Object arraySchema(Object type) {
        Map<String, Object> mapSchema = new HashMap<>();
        mapSchema.put(AvroConstants.SCHEMA_KEY_TYPE, Type.ARRAY.getName());
        mapSchema.put(AvroConstants.SCHEMA_KEY_ARRAY_ITEMS, type);
        return mapSchema;
    }

    public static Object recordSchema() {
        Map<String, Object> recordSchema = new HashMap<>();
        recordSchema.put(AvroConstants.SCHEMA_KEY_TYPE, Type.RECORD.getName());
        recordSchema.put(AvroConstants.SCHEMA_KEY_NAME, "TestRecord"); // not unique!
        recordSchema.put(AvroConstants.SCHEMA_KEY_FIELDS, Collections.emptyList());
        return recordSchema;
    }

    public static void main(String[] args) {
        System.out.println(randomFields(10));
    }

}
