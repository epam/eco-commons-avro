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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * @author Andrei_Tytsik
 */
public class RemoveSchemaProperties implements SchemaModification {

    private final List<String> keys;

    public RemoveSchemaProperties(Collection<String> keys) {
        Validate.notEmpty(keys, "Collection of keys is null or empty");
        Validate.noNullElements(keys, "Collection of keys has null elements");

        this.keys = Collections.unmodifiableList(new ArrayList<>(keys));
    }

    public List<String> getKeys() {
        return keys;
    }

    @Override
    public void applyToGeneric(Map<String, Object> schemaMap) {
        for (String key : keys) {
            schemaMap.remove(key);
        }
    }

    @Override
    public String toString() {
        return String.format(
                "%s: {keys: %s}",
                this.getClass().getSimpleName(), StringUtils.join(keys, ", "));
    }

    @Override
    public int hashCode() {
        return Objects.hash(keys);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        RemoveSchemaProperties that = (RemoveSchemaProperties)obj;
        return Objects.equals(this.keys, that.keys);
    }

    public static RemoveSchemaProperties with(String ... keys) {
        return new RemoveSchemaProperties(keys != null ? Arrays.asList(keys) : null);
    }

    public static RemoveSchemaProperties with(Collection<String> keys) {
        return new RemoveSchemaProperties(keys);
    }

}
