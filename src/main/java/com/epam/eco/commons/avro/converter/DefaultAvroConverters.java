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
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.apache.avro.JsonProperties;
import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.apache.avro.generic.GenericData.EnumSymbol;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.commons.lang3.Validate;

import static com.epam.eco.commons.avro.AvroUtils.isNullable;

/**
 * @author Ihar_Karoza
 */
public class DefaultAvroConverters implements AvroConverters {

    private final Map<Schema.Type, AvroConverter> converters = new EnumMap<>(Schema.Type.class);

    public DefaultAvroConverters() {
        converters.put(Schema.Type.INT, new DefaultIntAvroConverter());
        converters.put(Schema.Type.LONG, new DefaultLongAvroConverter());
        converters.put(Schema.Type.FLOAT, new DefaultFloatAvroConverter());
        converters.put(Schema.Type.DOUBLE, new DefaultDoubleAvroConverter());
        converters.put(Schema.Type.BOOLEAN, new DefaultBooleanAvroConverter());
        converters.put(Schema.Type.STRING, new DefaultCharSequenceAvroConverter());
        converters.put(Schema.Type.ARRAY, new DefaultArrayAvroConverter());
        converters.put(Schema.Type.BYTES, new DefaultBytesAvroConverter());
        converters.put(Schema.Type.MAP, new DefaultMapAvroConverter());
        converters.put(Schema.Type.RECORD, new DefaultRecordAvroConverter());
        converters.put(Schema.Type.UNION, new DefaultUnionAvroConverter());
        converters.put(Schema.Type.ENUM, new DefaultEnumAvroConverter());
    }

    @Override
    public void register(Type type, AvroConverter converter) {
        Validate.notNull(type, "Type is null");
        Validate.notNull(converter, "Converter is null");

        converters.put(type, converter);
    }

    @Override
    public AvroConverter getForType(Type type) {
        Validate.notNull(type, "Type is null");

        AvroConverter converter = converters.get(type);
        if (converter == null) {
            throw new AvroConversionException("Cannot find converter for schema " + type.getName());
        }

        return converter;
    }

    public static class DefaultIntAvroConverter implements AvroConverter {

        @Override
        public Object toAvro(Object value, Schema schema, AvroConverters converters) {
            if (schema.getLogicalType() != null) {
                return toLogicalType(value, schema);
            } else {
                return toBaseType(value, schema);
            }
        }

        private Object toLogicalType(Object value, Schema schema) {
            if (LogicalTypes.date().equals(schema.getLogicalType())) {
                if (value instanceof Date) {
                    return (int) Instant.ofEpochMilli(((Date) value).getTime())
                                        .atZone(ZoneOffset.systemDefault())
                                        .toLocalDate()
                                        .toEpochDay();
                } else if (value instanceof LocalDateTime ldt) {
                    return this.toLogicalType(ldt.toLocalDate(), schema);
                } else if (value instanceof LocalDate) {
                    return (int) ChronoUnit.DAYS.between(LocalDate.ofEpochDay(0), ((LocalDate) value));
                } else if (value instanceof CharSequence) {
                    return (int) LocalDate.parse((CharSequence) value).toEpochDay();
                } else if (value instanceof Integer) {
                    return value;
                }
            } else if (LogicalTypes.timeMillis().equals(schema.getLogicalType())) {
                if (value instanceof Date) {
                    Instant instant = Instant.ofEpochMilli(((Date) value).getTime());
                    return LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalTime().toSecondOfDay();
                } else if (value instanceof LocalDateTime ldt) {
                    return ldt.toLocalTime().toSecondOfDay();
                } else if (value instanceof LocalTime) {
                    return ((LocalTime) value).toSecondOfDay();
                } else if (value instanceof CharSequence) {
                    return LocalTime.parse((CharSequence) value).toSecondOfDay();
                } else if (value instanceof Integer) {
                    return value;
                }
            }

            throw new AvroConversionException(value, schema);
        }

        private Object toBaseType(Object value, Schema schema) {
            if (value instanceof Integer) {
                return value;
            } else if (value instanceof Long) {
                return Math.toIntExact((Long) value);
            } else if (value instanceof String) {
                return Integer.valueOf((String) value);
            } else {
                throw new AvroConversionException(value, schema);
            }
        }
    }

    public static class DefaultLongAvroConverter implements AvroConverter {

        @Override
        public Object toAvro(Object value, Schema schema, AvroConverters converters) {
            if (schema.getLogicalType() != null) {
                return toLogicalType(value, schema);
            } else {
                return toBaseType(value, schema);
            }
        }

        private Object toLogicalType(Object value, Schema schema) {
            if (LogicalTypes.timestampMillis().equals(schema.getLogicalType())) {
                if (value instanceof Date) {
                    return ((Date) value).getTime();
                } else if (value instanceof LocalDateTime) {
                    return ((LocalDateTime) value).toInstant(ZoneOffset.UTC)
                                                  .toEpochMilli();
                } else if (value instanceof CharSequence) {
                    return LocalDateTime.parse((CharSequence) value)
                                        .toInstant(ZoneOffset.UTC)
                                        .toEpochMilli();
                } else if (value instanceof Long  || value instanceof Integer) {
                    return value;
                }
            }

            throw new AvroConversionException(value, schema);
        }

        private Object toBaseType(Object value, Schema schema) {
            if (value instanceof Long || value instanceof Integer) {
                return value;
            } else if (value instanceof String) {
                return Long.valueOf((String) value);
            } else {
                throw new AvroConversionException(value, schema);
            }
        }

    }

    public static class DefaultFloatAvroConverter implements AvroConverter {

        @Override
        public Object toAvro(Object value, Schema schema, AvroConverters converters) {
            if (value instanceof Float) {
                return value;
            } else if (value instanceof Integer) {
                return ((Integer) value).floatValue();
            } else if (value instanceof Long) {
                return ((Long) value).floatValue();
            } else if (value instanceof Double) {
                return ((Double) value).floatValue();
            } else if (value instanceof String) {
                return Float.parseFloat((String) value);
            } else {
                throw new AvroConversionException(value, schema);
            }
        }

    }

    public static class DefaultDoubleAvroConverter implements AvroConverter {

        @Override
        public Object toAvro(Object value, Schema schema, AvroConverters converters) {
            if (value instanceof Double) {
                return value;
            } else if (value instanceof Integer) {
                return ((Integer) value).doubleValue();
            } else if (value instanceof Long) {
                return ((Long) value).doubleValue();
            } else if (value instanceof Float) {
                return ((Float) value).doubleValue();
            } else if (value instanceof String) {
                return Double.parseDouble((String) value);
            } else {
                throw new AvroConversionException(value, schema);
            }
        }
    }

    public static class DefaultBooleanAvroConverter implements AvroConverter {

        @Override
        public Object toAvro(Object value, Schema schema, AvroConverters converters) {
            if (value instanceof Boolean) {
                return value;
            } else if (value instanceof String) {
                return Boolean.valueOf((String) value);
            } else {
                throw new AvroConversionException(value, schema);
            }
        }

    }

    public static class DefaultCharSequenceAvroConverter implements AvroConverter {

        @Override
        public Object toAvro(Object value, Schema schema, AvroConverters converters) {
            if (value instanceof CharSequence) {
                return value.toString();
            } else {
                throw new AvroConversionException(value, schema);
            }
        }

    }

    public static class DefaultBytesAvroConverter implements AvroConverter {

        @Override
        public Object toAvro(Object value, Schema schema, AvroConverters converters) {
            if (value instanceof ByteBuffer) {
                return value;
            } else {
                throw new AvroConversionException(value, schema);
            }
        }
    }


    public static class DefaultEnumAvroConverter implements AvroConverter {

        @Override
        public Object toAvro(Object value, Schema schema, AvroConverters converters) {
            String strValue = value == null ? null : value.toString();
            List<String> validSymbols = schema.getEnumSymbols();
            if (validSymbols == null || validSymbols.isEmpty() || !validSymbols.contains(strValue)) {
                throw new AvroConversionException(value, schema);
            }
            return new EnumSymbol(schema, strValue);
        }
    }


    public static class DefaultUnionAvroConverter implements AvroConverter {

        @Override
        public Object toAvro(Object value, Schema schema, AvroConverters converters) {
            if (value == null)
                return handleNullValue(schema);

            return schema.getTypes().stream()
                    .filter(s -> Schema.Type.NULL != s.getType())
                         .map(s -> tryConvert(value, s, converters))
                         .filter(Optional::isPresent)
                         .findFirst()
                         .orElseThrow(() -> new AvroConversionException(value, schema))
                         .get();
        }

        private Object handleNullValue(Schema schema) {
            if (isNullable(schema)) {
                return null;
            } else {
                throw new AvroConversionException("Schema must be nullable to accept null values");
            }
        }

        private Optional<Object> tryConvert(
                Object value,
                Schema unionSchema,
                AvroConverters converters) {
            try {
                return Optional.of(
                        converters.getForSchema(unionSchema).toAvro(value, unionSchema, converters));
            } catch (AvroConversionException e) {
                return Optional.empty();
            }
        }

    }

    public static class DefaultArrayAvroConverter implements AvroConverter {

        @Override
        @SuppressWarnings("unchecked")
        public Object toAvro(Object value, Schema schema, AvroConverters converters) {
            if (value instanceof Collection) {
                Schema elementsSchema = schema.getElementType();
                return ((Collection<Object>) value).stream().
                        map(e -> converters.getForSchema(elementsSchema).toAvro(e, elementsSchema, converters)).
                        collect(Collectors.toList());
            } else {
                throw new AvroConversionException(value, schema);
            }
        }

    }

    public static class DefaultMapAvroConverter implements AvroConverter {

        @Override
        @SuppressWarnings("unchecked")
        public Object toAvro(Object value, Schema schema, AvroConverters converters) {
            if (value instanceof Map) {
                Schema mapValuesSchema = schema.getValueType();
                
                Map<String, Object> convertedMap = new HashMap<>();
                
                BiConsumer<String, Object> enrichMap = (k, v) -> convertedMap.put(
                    k,
                    converters.getForSchema(mapValuesSchema).
                        toAvro(v, mapValuesSchema, converters)
                );
                
                ((Map<String, Object>) value).forEach(enrichMap);
                
                return convertedMap;
            }

            throw new AvroConversionException(value, schema);
        }
    }

    public static class DefaultRecordAvroConverter implements AvroConverter {

        @Override
        @SuppressWarnings("unchecked")
        public Object toAvro(Object value, Schema schema, AvroConverters converters) {
            if (value instanceof Map) {
                Map<String, Object> valueMap = (Map<String, Object>) value;

                validate(valueMap, schema);

                GenericRecordBuilder recordBuilder = new GenericRecordBuilder(schema);

                for (Map.Entry<String, Object> e: valueMap.entrySet()) {
                    Field field = schema.getField(e.getKey());
                    recordBuilder.set(
                            e.getKey(),
                            converters.getForSchema(field.schema()).
                                toAvro(e.getValue(), field.schema(), converters));
                }

                return recordBuilder.build();
            } else {
                throw new AvroConversionException(value, schema);
            }
        }



        private void validate(Map<String, Object> value, Schema schema) {
            List<Field> schemaFields = schema.getFields();

            List<String> fieldsDisjunction = new ArrayList<>();
            for (Field schemaField : schemaFields) {
                if (!value.containsKey(schemaField.name()) && !isUnionWithNull(schemaField)) {
                    fieldsDisjunction.add(schemaField.name());
                }
            }

            List<String> schemaFieldsNames = schemaFields.stream()
                    .map(Field::name)
                    .toList();
            for (String valueName : value.keySet()) {
                if (!schemaFieldsNames.contains(valueName)) {
                    fieldsDisjunction.add(valueName);
                }
            }

            if (!fieldsDisjunction.isEmpty()) {
                String message = String.join(", ", fieldsDisjunction);
                throw new AvroConversionException(value, schema, "Fields disjunction: " + message);
            }
        }

        private boolean isUnionWithNull(Field schemaField) {
            if (schemaField.schema().getType() != Schema.Type.UNION || schemaField.schema().getTypes().isEmpty()) {
                return false;
            }
            //null only on first position working in our case
            return schemaField.schema().getTypes().get(0).getType() == Schema.Type.NULL &&
                    Objects.equals(schemaField.defaultVal(), JsonProperties.NULL_VALUE);
        }

    }

}
