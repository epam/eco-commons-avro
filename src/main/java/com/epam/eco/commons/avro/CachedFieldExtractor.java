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

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.avro.Schema;
import org.apache.commons.lang3.Validate;

/**
 * @author Andrei_Tytsik
 */
public abstract class CachedFieldExtractor {

    private static final ConcurrentMap<Schema, List<FieldInfo>> CACHE = new ConcurrentHashMap<>();

    private CachedFieldExtractor() {
    }

    public static List<FieldInfo> fromSchema(Schema schema) {
        Validate.notNull(schema, "Schema is null");

        return CACHE.computeIfAbsent(
                schema,
                key -> FieldExtractor.fromSchema(schema));
    }

}
