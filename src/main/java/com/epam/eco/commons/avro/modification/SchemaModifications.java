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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

/**
 * @author Andrei_Tytsik
 */
public class SchemaModifications implements SchemaModification {

    protected final List<SchemaModification> modifications;

    public SchemaModifications(List<SchemaModification> modifications) {
        Validate.notEmpty(modifications, "Collection of schema modifications is null or empty");
        Validate.noNullElements(
                modifications,
                "Collection of schema modifications contains null elements");

        this.modifications = Collections.unmodifiableList(new ArrayList<>(modifications));
    }

    @Override
    public void applyToGeneric(Map<String, Object> schemaMap) {
        Validate.notNull(schemaMap, "Schema is null");

        for (SchemaModification modification : modifications) {
            modification.applyToGeneric(schemaMap);
        }
    }

    public static SchemaModification of(SchemaModification ... modifications) {
        return new SchemaModifications(
                modifications != null ? Arrays.asList(modifications) : null);
    }

    public static SchemaModification of(List<SchemaModification> modifications) {
        return new SchemaModifications(modifications);
    }

}
