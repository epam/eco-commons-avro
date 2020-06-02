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

import org.apache.avro.Schema;

/**
 * @author Ihar_Karoza
 */
public interface SchemaGenerators extends SchemaGenerator<Object> {

    <T> void register(Class<T> type, SchemaGenerator<T> generator);

    <T> SchemaGenerator<T> getForType(Class<T> type);

    @SuppressWarnings("unchecked")
    @Override
    default Schema createForValue(
            Object value,
            String schemaName,
            String schemaNamespace,
            SchemaGenerators generators) {
        return ((SchemaGenerator<Object>)getForType(value.getClass())).
                createForValue(value, schemaName, schemaNamespace, generators);
    }

    @SuppressWarnings("unchecked")
    default Schema createForValue(
            Object value,
            String schemaName,
            String schemaNamespace) {
        return ((SchemaGenerator<Object>)getForType(value.getClass())).
                createForValue(value, schemaName, schemaNamespace, this);
    }

    static SchemaGenerators createDefault() {
        return new DefaultSchemaGenerators();
    }

}
