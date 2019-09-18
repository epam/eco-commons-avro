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

import java.util.Objects;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;

/**
 * @author Andrei_Tytsik
 */
public class FieldInfo implements Comparable<FieldInfo> {

    private final Schema parent;
    private final Field field;
    private final String path;

    public FieldInfo(Schema parent, Field field, String path) {
        Validate.notNull(parent, "Parent is null");
        Validate.notNull(field, "Field is null");
        Validate.notBlank(path, "Path is blank");

        this.parent = parent;
        this.field = field;
        this.path = path;
    }

    public Schema getParent() {
        return parent;
    }
    public Field getField() {
        return field;
    }
    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, field, path);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        FieldInfo that = (FieldInfo)obj;
        return
                Objects.equals(this.parent, that.parent) &&
                Objects.equals(this.field, that.field) &&
                Objects.equals(this.path, that.path);
    }

    @Override
    public int compareTo(FieldInfo other) {
        int result = ObjectUtils.compare(this.path, other.path);
        if (result == 0) {
            result = ObjectUtils.compare(this.field.schema().getType(), other.field.schema().getType());
        }
        return result;
    }

    public static FieldInfo with(Schema parent, Field field, String path) {
        return new FieldInfo(parent, field, path);
    }

}
