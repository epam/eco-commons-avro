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
package com.epam.eco.commons.avro.gen;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.SchemaBuilder.FieldAssembler;
import org.apache.commons.lang3.Validate;

import static com.epam.eco.commons.avro.AvroUtils.toNullable;

/**
 * @author Ihar_Karoza
 */
public class DefaultSchemaGenerators implements SchemaGenerators {

    private final Map<Class<?>, SchemaGenerator<?>> generators = new HashMap<>();

    public DefaultSchemaGenerators() {
        generators.put(Integer.class, (value, name, namaspace, provider) -> SchemaBuilder.builder().nullable().intType());
        generators.put(Long.class, (value, name, namaspace, provider) -> SchemaBuilder.builder().nullable().longType());
        generators.put(Float.class, (value, name, namaspace, provider) -> SchemaBuilder.builder().nullable().floatType());
        generators.put(Double.class, (value, name, namaspace, provider) -> SchemaBuilder.builder().nullable().doubleType());
        generators.put(Boolean.class, (value, name, namaspace, provider) -> SchemaBuilder.builder().nullable().booleanType());
        generators.put(CharSequence.class, (value, name, namaspace, provider) -> SchemaBuilder.builder().nullable().stringType());
        generators.put(ByteBuffer.class, (value, name, namaspace, provider) -> SchemaBuilder.builder().nullable().bytesType());
        generators.put(LocalDate.class, (value, name, namaspace, provider) -> toNullable(LogicalTypes.date().addToSchema(SchemaBuilder.builder().intType())));
        generators.put(LocalTime.class, (value, name, namaspace, provider) -> toNullable(LogicalTypes.timeMillis().addToSchema(SchemaBuilder.builder().intType())));
        generators.put(LocalDateTime.class, (value, name, namaspace, provider) -> toNullable(LogicalTypes.timestampMillis().addToSchema(SchemaBuilder.builder().longType())));
        generators.put(Collection.class, new DefaultCollectionSchemaGenerator());
        generators.put(Map.class, new DefaultMapSchemaGenerator());
    }

    @Override
    public <T> void register(Class<T> type, SchemaGenerator<T> generator) {
        Validate.notNull(type, "Type is null");
        Validate.notNull(generator, "Generator is null");

        generators.put(type, generator);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> SchemaGenerator<T> getForType(Class<T> type) {
        return (SchemaGenerator<T>) generators.entrySet().stream().
                filter(e -> e.getKey().isAssignableFrom(type)).
                map(Map.Entry::getValue).
                findFirst().
                orElseThrow(() -> new SchemaGenerationException("Cannot find generator for " + type));
    }

    public static class DefaultCollectionSchemaGenerator implements SchemaGenerator<Collection<Object>> {

        @Override
        public Schema createForValue(
                Collection<Object> value,
                String schemaName,
                String schemaNamespace,
                SchemaGenerators generators) {
            Object item = value.stream()
                               .findFirst()
                               .orElseThrow(() ->
                                   new SchemaGenerationException("Cannot build schema for empty collection"));

            Schema itemSchema = generators.createForValue(item, schemaName, schemaNamespace);

            return toNullable(SchemaBuilder.builder().array().items(itemSchema));
        }

    }

    public static class DefaultMapSchemaGenerator implements SchemaGenerator<Map<String, Object>> {

        @Override
        public Schema createForValue(
                Map<String, Object> value,
                String schemaName,
                String schemaNamespace,
                SchemaGenerators generators) {
            FieldAssembler<Schema> fieldAssembler = SchemaBuilder.record(schemaName)
                                                                 .namespace(schemaNamespace)
                                                                 .fields();

            for (Map.Entry<String, Object> e : value.entrySet()) {
                fieldAssembler = fieldAssembler.name(e.getKey())
                                               .type(generators.createForValue(
                                                       e.getValue(),
                                                       e.getKey(),
                                                       schemaNamespace))
                                               .noDefault();
            }

            return toNullable(fieldAssembler.endRecord());
        }

    }

}
