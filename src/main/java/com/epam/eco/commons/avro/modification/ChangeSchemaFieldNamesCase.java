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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.epam.eco.commons.avro.AvroConstants;
import com.epam.eco.commons.avro.traversal.GenericSchemaTraverseListener;
import com.epam.eco.commons.avro.traversal.GenericSchemaTraverser;

/**
 * @author Andrei_Tytsik
 */
public class ChangeSchemaFieldNamesCase implements SchemaModification {

    private final Case caze;
    private final List<String> includeRegexps;
    private final List<String> excludeRegexps;

    private final List<Pattern> includePatterns;
    private final List<Pattern> excludePatterns;

    public ChangeSchemaFieldNamesCase(Case caze) {
        this(caze, null, null);
    }

    public ChangeSchemaFieldNamesCase(
            Case caze,
            Collection<String> includeRegexps,
            Collection<String> excludeRegexps) {
        Validate.notNull(caze, "Case is null");

        if (includeRegexps != null) {
            Validate.noNullElements(
                    includeRegexps,
                    "Collection of include regexps contains null elements");
        }
        if (excludeRegexps != null) {
            Validate.noNullElements(
                    excludeRegexps,
                    "Collection of exclude regexps contains null elements");
        }

        this.caze = caze;
        this.includeRegexps =
                includeRegexps != null ?
                Collections.unmodifiableList(new ArrayList<>(includeRegexps)) :
                Collections.emptyList();
        this.excludeRegexps =
                excludeRegexps != null ?
                Collections.unmodifiableList(new ArrayList<>(excludeRegexps)) :
                Collections.emptyList();

        includePatterns = this.includeRegexps.stream().
                map(Pattern::compile).
                collect(Collectors.toList());
        excludePatterns = this.excludeRegexps.stream().
                map(Pattern::compile).
                collect(Collectors.toList());
    }

    public Case getCase() {
        return caze;
    }

    public List<String> getIncludeRegexps() {
        return includeRegexps;
    }

    public List<String> getExcludeRegexps() {
        return excludeRegexps;
    }

    @Override
    public void applyToGeneric(Map<String, Object> schemaMap) {
        List<Map<String, Object>> fieldsToChange = new ArrayList<>();

        new GenericSchemaTraverser(new GenericSchemaTraverseListener() {
            @Override
            public void onSchemaField(
                    String path,
                    Map<String, Object> parentSchema,
                    Map<String, Object> field) {
                if (isFieldIncluded(path) && !isFieldExcluded(path)) {
                    fieldsToChange.add(field);
                }
            }
            @Override
            public void onSchema(String path, Object parentSchema, Object schema) {
                // do nothing
            }
        }).walk(schemaMap);

        for (Map<String, Object> field : fieldsToChange) {
            String fieldName = (String)field.get(AvroConstants.SCHEMA_KEY_FIELD_NAME);
            field.put(AvroConstants.SCHEMA_KEY_FIELD_NAME, caze.apply(fieldName));
        }
    }

    private boolean isFieldIncluded(String path) {
        if (includePatterns.isEmpty()) {
            return true;
        }
        for (Pattern pattern : includePatterns) {
            if (pattern.matcher(path).matches()) {
                return true;
            }
        }
        return false;
    }

    private boolean isFieldExcluded(String path) {
        for (Pattern pattern : excludePatterns) {
            if (pattern.matcher(path).matches()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format(
                "%s: {case: %s, includeRegexps: %s, excludeRegexps: %s}",
                this.getClass().getSimpleName(),
                caze,
                StringUtils.join(includeRegexps, ", "),
                StringUtils.join(excludeRegexps, ", "));
    }

    @Override
    public int hashCode() {
        return Objects.hash(caze, includeRegexps, excludeRegexps);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        ChangeSchemaFieldNamesCase that = (ChangeSchemaFieldNamesCase)obj;
        return
                Objects.equals(this.caze, that.caze) &&
                Objects.equals(this.includeRegexps, that.includeRegexps) &&
                Objects.equals(this.excludeRegexps, that.excludeRegexps);
    }

    public enum Case {

        UPPER(String::toUpperCase),
        LOWER(String::toLowerCase);

        private final Function<String, String> function;

        Case(Function<String, String> function) {
            this.function = function;
        }

        public String apply(String str) {
            return function.apply(str);
        }

    }

    public static ChangeSchemaFieldNamesCase with(Case caze) {
        return new ChangeSchemaFieldNamesCase(caze);
    }

    public static ChangeSchemaFieldNamesCase with(
            Case caze,
            Collection<String> includeRegexps,
            Collection<String> excludeRegexps) {
        return new ChangeSchemaFieldNamesCase(caze, includeRegexps, excludeRegexps);
    }

    public static ChangeSchemaFieldNamesCase withIncludes(
            Case caze,
            Collection<String> includeRegexps) {
        return new ChangeSchemaFieldNamesCase(caze, includeRegexps, null);
    }

    public static ChangeSchemaFieldNamesCase withExcludes(
            Case caze,
            Collection<String> excludeRegexps) {
        return new ChangeSchemaFieldNamesCase(caze, null, excludeRegexps);
    }

}
