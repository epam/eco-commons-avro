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
package com.epam.eco.commons.avro.modification;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.avro.Schema;
import org.apache.commons.lang3.Validate;

/**
 * @author Andrei_Tytsik
 */
public class CachedSchemaModifications extends SchemaModifications {

    private static final ConcurrentMap<Key, Schema> CACHE = new ConcurrentHashMap<>();

    public CachedSchemaModifications(List<SchemaModification> modifications) {
        super(modifications);
    }

    @Override
    public Schema applyTo(Schema schema) {
        Validate.notNull(schema, "Schema is null");

        Key key = new Key(schema, modifications);
        return CACHE.computeIfAbsent(
                key,
                k -> super.applyTo(schema));
    }

    private static class Key {

        private final Schema schema;
        private final List<SchemaModification> modifications;

        public Key(Schema schema, List<SchemaModification> modifications) {
            this.schema = schema;
            this.modifications = modifications;
        }

        @Override
        public int hashCode() {
            return Objects.hash(schema, modifications);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            Key that = (Key)obj;
            return
                    Objects.equals(this.schema, that.schema) &&
                    Objects.equals(this.modifications, that.modifications);
        }

    }

    public static SchemaModification of(SchemaModification ... modifications) {
        return new CachedSchemaModifications(
                modifications != null ? Arrays.asList(modifications) : null);
    }

    public static SchemaModification of(List<SchemaModification> modifications) {
        return new CachedSchemaModifications(modifications);
    }

}
