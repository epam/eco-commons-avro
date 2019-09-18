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
package com.epam.eco.commons.avro.modification;

import java.util.Map;

import org.apache.avro.Schema;

import com.epam.eco.commons.avro.AvroUtils;

/**
 * @author Andrei_Tytsik
 */
public interface SchemaModification {

    @SuppressWarnings("unchecked")
    default Schema applyTo(Schema schema) {
        Object genericSchema = AvroUtils.schemaToGeneric(schema);
        if (!(genericSchema instanceof Map)) {
            throw new RuntimeException(
                    String.format("Schema of type '%s' can't be modified", schema.getClass()));
        }

        Map<String, Object> genericSchemaMap = (Map<String, Object>)genericSchema;
        applyToGeneric(genericSchemaMap);
        return AvroUtils.schemaFromGeneric(genericSchemaMap);
    }

    void applyToGeneric(Map<String, Object> schemaMap);

}
