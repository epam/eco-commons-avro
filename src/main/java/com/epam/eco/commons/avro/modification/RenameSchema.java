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

import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.Validate;

import com.epam.eco.commons.avro.AvroConstants;

/**
 * @author Andrei_Tytsik
 */
public class RenameSchema implements SchemaModification {

    private final String name;
    private final String namespace;

    public RenameSchema(String name, String namespace) {
        Validate.isTrue(name != null || namespace != null, "Name and Namespace are blank");

        this.name = name;
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }
    public String getNamespace() {
        return namespace;
    }

    @Override
    public void applyToGeneric(Map<String, Object> schemaMap) {
        if (name != null) {
            schemaMap.put(AvroConstants.SCHEMA_KEY_NAME, name);
        }
        if (namespace != null) {
            schemaMap.put(AvroConstants.SCHEMA_KEY_NAMESPACE, namespace);
        }
    }

    @Override
    public String toString() {
        return String.format(
                "%s: {name: %s, namespace: %s}",
                this.getClass().getSimpleName(), name, namespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, namespace);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        RenameSchema that = (RenameSchema)obj;
        return
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.namespace, that.namespace);
    }

    public static RenameSchema withName(String name) {
        return new RenameSchema(name, null);
    }

    public static RenameSchema withNamespace(String namespace) {
        return new RenameSchema(null, namespace);
    }

    public static RenameSchema with(String name, String namespace) {
        return new RenameSchema(name, namespace);
    }

}
