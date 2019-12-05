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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.util.Utf8;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author tytsik
 */
public abstract class AvroUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Pattern FIELD_NAME_PATTERN = Pattern.compile("^[_a-zA-Z][_a-zA-Z0-9]*$");

    private static final Map<String, Type> TYPES;
    private static final Map<String, Type> TYPES_CASE_INSENSITIVE;
    static {
        Map<String, Type> types = new HashMap<>((int) (Type.values().length / 0.75));
        for (Type type : Type.values()) {
            types.put(type.getName(), type);
        }
        TYPES = Collections.unmodifiableMap(types);
        TYPES_CASE_INSENSITIVE = Collections.unmodifiableMap(new CaseInsensitiveMap<>(types));
    }

    private AvroUtils() {
    }

    public static Map<String, Object> encodeRecordToMap(GenericRecord record) {
        try {
            String json = encodeRecordToJson(record);
            return MAPPER.readerFor(Map.class).readValue(json);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static String encodeRecordToJson(GenericRecord record) {
        Validate.notNull(record, "Record is null");

        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();

            Encoder encoder = EncoderFactory.get().jsonEncoder(record.getSchema(), out);
            DatumWriter<Object> writer;
            if (record instanceof SpecificRecord) {
                writer = new SpecificDatumWriter<>(record.getSchema());
            } else {
                writer = new GenericDatumWriter<>(record.getSchema());
            }

            writer.write(record, encoder);
            encoder.flush();

            return new String(out.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    public static byte[] encodeRecordToBinary(GenericRecord record) {
        Validate.notNull(record, "Record is null");

        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();

            Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            DatumWriter<Object> writer;
            if (record instanceof SpecificRecord) {
                writer = new SpecificDatumWriter<>(record.getSchema());
            } else {
                writer = new GenericDatumWriter<>(record.getSchema());
            }

            writer.write(record, encoder);
            encoder.flush();

            return out.toByteArray();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    public static GenericRecord decodeRecordFromBinary(
            byte[] recordBytes,
            Schema schema,
            boolean specific) {
        Validate.notNull(recordBytes, "Record bytes array is null");
        Validate.notNull(schema, "Schema is null");

        try {
            Decoder decoder = DecoderFactory.get().binaryDecoder(recordBytes, null);
            DatumReader<Object> reader;
            if (specific) {
                reader = new SpecificDatumReader<>(schema);
            } else {
                reader = new GenericDatumReader<>(schema);
            }

            return (GenericRecord)reader.read(null, decoder);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static GenericRecord decodeRecordFromMap(
            Map<String, Object> recordMap,
            Schema schema,
            boolean specific) {
        Validate.notNull(recordMap, "Record map is null");

        try {
            String json = MAPPER.writeValueAsString(recordMap);
            return decodeRecordFromJson(json, schema, specific);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static GenericRecord decodeRecordFromJson(
            String recordJson,
            Schema schema,
            boolean specific) {
        Validate.notBlank(recordJson, "Record JSON is blank");
        Validate.notNull(schema, "Schema is null");

        try {
            Decoder decoder = DecoderFactory.get().jsonDecoder(schema, recordJson);
            DatumReader<Object> reader;
            if (specific) {
                reader = new SpecificDatumReader<>(schema);
            } else {
                reader = new GenericDatumReader<>(schema);
            }

            return (GenericRecord) reader.read(null, decoder);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static Schema schemaFromFile(String path) {
        Validate.notBlank(path, "Path is blank");

        return schemaFromFile(new File(path));
    }

    public static Schema schemaFromFile(File file) {
        Validate.notNull(file, "File is null");

        try {
            return new Schema.Parser().parse(file);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static Schema schemaFromResource(String name) {
        Validate.notBlank(name, "Name is blank");

        try (InputStream inputStream = AvroUtils.class.getResourceAsStream(name)) {
            return new Schema.Parser().parse(inputStream);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static Schema schemaFromJson(String schemaJson) {
        Validate.notBlank(schemaJson, "Schema json is blank");

        return new Schema.Parser().parse(schemaJson);
    }

    public static String schemaToJson(Schema schema) {
        Validate.notNull(schema, "Schema is null");

        return schema.toString();
    }

    public static Schema schemaFromGeneric(Object genericSchema) {
        Validate.notNull(genericSchema, "Generic schema is null");

        try {
            String json = MAPPER.writeValueAsString(genericSchema);
            return schemaFromJson(json);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static Object schemaToGeneric(Schema schema) {
        Validate.notNull(schema, "Schema is null");

        try {
            String json = schemaToJson(schema);
            return MAPPER.readerFor(Object.class).readValue(json);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static Field fieldFromJson(String fieldJson) {
        Validate.notBlank(fieldJson, "Field json is blank");

        Schema adoptedSchema = schemaFromJson(
                String.format("{\"type\":\"record\",\"name\":\"x\",\"fields\":[%s]}", fieldJson));

        return adoptedSchema.getFields().get(0);
    }

    public static Field fieldFromGeneric(Map<String, Object> genericField) {
        Validate.notNull(genericField, "Generic field is null");

        try {
            String json = MAPPER.writeValueAsString(genericField);
            return fieldFromJson(json);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static Map<String, Object> fieldToGeneric(Field field) {
        Validate.notNull(field, "Field is null");

        try {
            String json = fieldToJson(field);
            return MAPPER.readerFor(Map.class).readValue(json);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @SuppressWarnings("unchecked")
    public static String fieldToJson(Field field) {
        Validate.notNull(field, "Field is null");

        try {
            field = cloneField(field, true, true);
            Schema adoptedSchema = Schema.createRecord(Collections.singletonList(field));
            Map<String, Object> genericSchema = (Map<String, Object>) schemaToGeneric(adoptedSchema);
            Object genericField = ((List<Object>) genericSchema.get(AvroConstants.SCHEMA_KEY_FIELDS)).get(0);
            return MAPPER.writeValueAsString(genericField);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @SuppressWarnings("unchecked")
    public static Type typeOfGenericSchema(Object genericSchema) throws UnknownTypeException {
        Validate.notNull(genericSchema, "Generic schema is null");

        if (genericSchema instanceof Map) {
            return typeForName(
                    ((Map<String, Object>)genericSchema).get(AvroConstants.SCHEMA_KEY_TYPE).toString());
        } else if (genericSchema instanceof List) {
            return Type.UNION;
        } else {
            return typeForName(genericSchema.toString());
        }
    }

    public static Type typeOfGenericSchemaOrElseNullIfUnknown(Object genericSchema) {
        try {
            return typeOfGenericSchema(genericSchema);
        } catch (UnknownTypeException ute) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static Type effectiveTypeOfGenericSchema(Object genericSchema) throws UnknownTypeException {
        Validate.notNull(genericSchema, "Generic schema is null");

        if (genericSchema instanceof Map) {
            return typeForName(
                    ((Map<String, Object>)genericSchema).get(AvroConstants.SCHEMA_KEY_TYPE).toString());
        } else if (genericSchema instanceof List) {
            return effectiveTypeOfGenericUnionSchema((List<Object>)genericSchema);
        } else {
            return typeForName(genericSchema.toString());
        }
    }

    public static Type effectiveTypeOfGenericSchemaOrElseNullIfUnknown(Object genericSchema) {
        try {
            return effectiveTypeOfGenericSchema(genericSchema);
        } catch (UnknownTypeException ute) {
            return null;
        }
    }

    public static Type effectiveTypeOfGenericUnionSchema(List<Object> genericUnionSchema) throws UnknownTypeException {
        Validate.notEmpty(genericUnionSchema, "Generic union schema is null or empty");

        if (genericUnionSchema.size() == 1) {
            return effectiveTypeOfGenericSchema(genericUnionSchema.get(0));
        } else if (genericUnionSchema.size() == 2) {
            Object first = genericUnionSchema.get(0);
            Object second = genericUnionSchema.get(1);
            if (Type.NULL.getName().equals(first)) {
                return effectiveTypeOfGenericSchema(second);
            } else if (Type.NULL.getName().equals(second)) {
                return effectiveTypeOfGenericSchema(first);
            }
        }
        return Type.UNION;
    }

    public static Type typeOfGenericField(Map<String, Object> genericField) throws UnknownTypeException {
        Validate.notNull(genericField, "Generic field is null");

        return typeOfGenericSchema(genericField.get(AvroConstants.SCHEMA_KEY_FIELD_TYPE));
    }

    public static Type typeOfGenericFieldOrElseNullIfUnknown(Map<String, Object> genericField) {
        try {
            return typeOfGenericField(genericField);
        } catch (UnknownTypeException ute) {
            return null;
        }
    }

    public static Type effectiveTypeOfGenericField(Map<String, Object> genericField) throws UnknownTypeException {
        Validate.notNull(genericField, "Generic field is null");

        return effectiveTypeOfGenericSchema(genericField.get(AvroConstants.SCHEMA_KEY_FIELD_TYPE));
    }

    public static Type effectiveTypeOfGenericFieldOrElseNullIfUnknown(Map<String, Object> genericField) {
        try {
            return effectiveTypeOfGenericField(genericField);
        } catch (UnknownTypeException ute) {
            return null;
        }
    }

    public static String nameOfGenericField(Map<String, Object> genericField) {
        Validate.notNull(genericField, "Generic field is null");

        return (String)genericField.get(AvroConstants.SCHEMA_KEY_FIELD_NAME);
    }

    public static boolean isFieldNameValid(String fieldName) {
        Validate.notNull(fieldName, "Field name is null");

        return FIELD_NAME_PATTERN.matcher(fieldName).matches();
    }

    public static Object avroPrimitiveToJava(Object value) {
        if (value instanceof Utf8) {
            return value.toString();
        }
        return value;
    }

    public static Object javaPrimitiveToAvro(Object value) {
        if (value instanceof String) {
            return new Utf8((String)value);
        }
        return value;
    }

    public static Schema toNullable(Schema schema) {
        Validate.notNull(schema, "Schema is null");

        return SchemaBuilder.nullable().type(schema);
    }

    public static Field cloneField(Field field, boolean cloneProps, boolean cloneAliases) {
        Validate.notNull(field, "Field is null");

        Field cloned = new Field(
                field.name(),
                field.schema(),
                field.doc(),
                field.defaultVal(),
                field.order());

        if (cloneProps && field.getObjectProps() != null) {
            field.getObjectProps().forEach(cloned::addProp);
        }

        if (cloneAliases && field.aliases() != null) {
            field.aliases().forEach(cloned::addAlias);
        }

        return cloned;
    }

    public static Schema resolveAnyNonNullable(Schema schema) {
        Validate.notNull(schema, "Schema is null");

        if (Type.UNION == schema.getType()) {
            return schema.getTypes().stream().
                    filter(s -> Schema.Type.NULL != s.getType()).
                    findAny().
                    orElseThrow(() -> new IllegalArgumentException("Can not resolve non nullable union type"));
        }
        return schema;
    }

    public static boolean isNullable(Schema schema) {
        Validate.notNull(schema, "Schema is null");

        if (Type.UNION == schema.getType()) {
            return schema.getTypes().stream()
                    .anyMatch(s -> Type.NULL == s.getType());
        }
        return false;
    }

    public static boolean isOfComplexType(Schema schema) {
        Validate.notNull(schema, "Schema is null");

        return isComplex(schema.getType());
    }

    public static boolean isComplex(Type type) {
        Validate.notNull(type, "Type is null");

        return
                type == Type.RECORD ||
                type == Type.ENUM ||
                type == Type.ARRAY ||
                type == Type.MAP ||
                type == Type.UNION ||
                type == Type.FIXED;
    }

    public static boolean isOfPrimitiveType(Schema schema) {
        Validate.notNull(schema, "Schema is null");

        return isPrimitive(schema.getType());
    }

    public static boolean isPrimitive(Type type) {
        Validate.notNull(type, "Type is null");

        return
                type == Type.NULL ||
                type == Type.BOOLEAN ||
                type == Type.INT ||
                type == Type.LONG ||
                type == Type.FLOAT ||
                type == Type.DOUBLE ||
                type == Type.BYTES ||
                type == Type.STRING;
    }

    public static boolean isOfNamedType(Schema schema) {
        Validate.notNull(schema, "Schema is null");

        return isNamed(schema.getType());
    }

    public static boolean isNamed(Type type) {
        Validate.notNull(type, "Type is null");

        return
                type == Type.RECORD ||
                type == Type.ENUM ||
                type == Type.FIXED;
    }

    public static boolean isOfParametrizedType(Schema schema) {
        Validate.notNull(schema, "Schema is null");

        return isParametrized(schema.getType());
    }

    public static boolean isParametrized(Type type) {
        Validate.notNull(type, "Type is null");

        return
                type == Type.UNION ||
                type == Type.ARRAY ||
                type == Type.MAP;
    }

    public static Type typeForName(String name) throws UnknownTypeException {
        Validate.notBlank(name, "Name is blank");

        Type type = TYPES.get(name);
        if (type == null) {
            throw new UnknownTypeException(name);
        }
        return type;
    }

    public static Type typeForNameIgnoreCase(String name) throws UnknownTypeException {
        Validate.notBlank(name, "Name is blank");

        Type type = TYPES_CASE_INSENSITIVE.get(name);
        if (type == null) {
            throw new UnknownTypeException(name);
        }
        return type;
    }

}
