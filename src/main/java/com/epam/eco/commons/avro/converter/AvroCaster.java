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
package com.epam.eco.commons.avro.converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.apache.avro.generic.GenericContainer;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericEnumSymbol;
import org.apache.avro.generic.GenericRecord;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

/**
 * @author Andrei_Tytsik
 */
public abstract class AvroCaster {

    public enum Feature {
        IGNORE_FIELD_NAMES_CASE
    }

    private AvroCaster() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends GenericContainer> T cast(
            T value,
            Schema toSchema,
            Feature ... features) {
        Validate.notNull(value, "Value is null");
        Validate.notNull(toSchema, "Cast schema is null");

        Set<Feature> featureSet =
                !ArrayUtils.isEmpty(features) ?
                EnumSet.copyOf(Arrays.asList(features)) :
                Collections.emptySet();

        return (T)castValue(value, value.getSchema(), toSchema, featureSet);
    }

    @SuppressWarnings("unchecked")
    private static Object castValue(
            Object value,
            Schema valueSchema,
            Schema valueCastSchema,
            Set<Feature> features) {
        if (value == null || valueSchema.getType() != valueCastSchema.getType()) {
            return null;
        }
        if (Type.ARRAY == valueSchema.getType()) {
            return castArray((List<Object>) value, valueSchema, valueCastSchema, features);
        } else if (Type.MAP == valueSchema.getType()) {
            return castMap((Map<CharSequence, Object>)value, valueSchema, valueCastSchema, features);
        } else if (Type.RECORD == valueSchema.getType()) {
            return castRecord((GenericRecord)value, valueSchema, valueCastSchema, features);
        } else if (Type.UNION == valueSchema.getType()) {
            return castUnion(value, valueSchema, valueCastSchema, features);
        } else if (Type.ENUM == valueSchema.getType()) {
            return castEnumSymbol((GenericEnumSymbol) value, valueCastSchema, features);
        } else {
            return value;
        }
    }

    private static GenericRecord castRecord(
            GenericRecord record,
            Schema recordSchema,
            Schema recordCastSchema,
            Set<Feature> features) {
        Map<String, Field> recordCastFieldMap;
        List<Field> recordCastSchemaFields = recordCastSchema.getFields();
        if (features.contains(Feature.IGNORE_FIELD_NAMES_CASE)) {
            recordCastFieldMap = new CaseInsensitiveMap<>((int) (recordCastSchemaFields.size() / 0.75));
        } else {
            recordCastFieldMap = new HashMap<>((int) (recordCastSchemaFields.size() / 0.75));
        }
        for (Field recordCastField : recordCastSchemaFields) {
            recordCastFieldMap.put(recordCastField.name(), recordCastField);
        }

        GenericRecord castedRecord = new GenericData.Record(recordCastSchema);
        for (Field recordField : recordSchema.getFields()) {
            Field castedRecordField = recordCastFieldMap.get(recordField.name());
            if (castedRecordField == null) {
                continue;
            }

            Object fieldValue = record.get(recordField.name());
            Object castedFieldValue = castValue(
                    fieldValue,
                    recordField.schema(),
                    castedRecordField.schema(),
                    features);
            if (fieldValue == null || castedFieldValue != null) {
                castedRecord.put(castedRecordField.name(), castedFieldValue);
            }
        }
        return castedRecord;
    }

    private static GenericData.Array<Object> castArray(
            List<Object> array,
            Schema arraySchema,
            Schema arrayCastSchema,
            Set<Feature> features) {
        GenericData.Array<Object> castedArray =
                new GenericData.Array<>(array.size(), arrayCastSchema);
        for (Object arrayItem : array) {
            Object castedArrayItem = castValue(
                    arrayItem,
                    arraySchema.getElementType(),
                    arrayCastSchema.getElementType(),
                    features);
            if (arrayItem == null || castedArrayItem != null) {
                castedArray.add(castedArrayItem);
            }
        }
        return castedArray;
    }

    private static GenericData.EnumSymbol castEnumSymbol(
            GenericEnumSymbol enumSymbol,
            Schema enumCastSchema,
            Set<Feature> features) {
        return new GenericData.EnumSymbol(enumCastSchema, enumSymbol);
    }

    private static Map<CharSequence, Object> castMap(
            Map<CharSequence, Object> map,
            Schema mapSchema,
            Schema mapCastSchema,
            Set<Feature> features) {
        Map<CharSequence, Object> castedMap = new LinkedHashMap<>(map.size());
        for (Entry<CharSequence, Object> mapEntry : map.entrySet()) {
            Object castedMapValue = castValue(
                    mapEntry.getValue(),
                    mapSchema.getValueType(),
                    mapCastSchema.getValueType(),
                    features);
            if (mapEntry.getValue() == null || castedMapValue != null) {
                castedMap.put(mapEntry.getKey(), castedMapValue);
            }
        }
        return castedMap;
    }

    private static Object castUnion(
            Object value,
            Schema unionSchema,
            Schema unionCastSchema,
            Set<Feature> features) {
        int schemaPos = GenericData.get().resolveUnion(unionSchema, value);
        if (schemaPos >= unionCastSchema.getTypes().size()) {
            return null;
        }

        Schema valueSchema = unionSchema.getTypes().get(schemaPos);
        Schema valueCastSchema = unionCastSchema.getTypes().get(schemaPos);
        return castValue(value, valueSchema, valueCastSchema, features);
    }

}
