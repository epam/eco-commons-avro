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
package com.epam.eco.commons.avro.avpath;

import java.util.Objects;

import org.apache.avro.Schema;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author Andrei_Tytsik
 */
public class PathTemplate implements Comparable<PathTemplate> {

    private final String path;
    private final Schema schema;

    public PathTemplate(String path, Schema schema) {
        this.path = path;
        this.schema = schema;
    }

    public String getPath() {
        return path;
    }
    public Schema getSchema() {
        return schema;
    }

    public PathTemplate join(PathTemplate other) {
        return with(
                this.path + other.path,
                other.schema);
    }

    public boolean isElementSelector() {
        return PathUtils.isMatchAllPath(path);
    }

    public String toPlainPath() {
        return toPlainPath(".");
    }

    public String toPlainPath(String separator) {
        return PathUtils.templatePathToPlain(path, separator);
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, schema);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        PathTemplate that = (PathTemplate)obj;
        return
                Objects.equals(this.path, that.path) &&
                Objects.equals(this.schema, that.schema);
    }

    @Override
    public int compareTo(PathTemplate other) {
        int result = ObjectUtils.compare(this.path, other.path);
        if (result == 0) {
            result = ObjectUtils.compare(this.schema.getType(), other.schema.getType());
        }
        return result;
    }

    public static PathTemplate with(
            String path,
            Schema schema) {
        return new PathTemplate(path, schema);
    }

}
