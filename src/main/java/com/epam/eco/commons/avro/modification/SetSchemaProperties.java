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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.lang3.Validate;

/**
 * @author Andrei_Tytsik
 */
public class SetSchemaProperties implements SchemaModification {

    private final Map<String, Object> properties;
    private final List<Feature> features;

    public SetSchemaProperties(String key, Object value) {
        this(Collections.singletonMap(key, value), (List<Feature>)null);
    }

    public SetSchemaProperties(String key, Object value, Feature ... features) {
        this(Collections.singletonMap(key, value), features);
    }

    public SetSchemaProperties(Map<String, Object> properties, Feature ... features) {
        this(properties, features != null ? Arrays.asList(features) : null);
    }

    public SetSchemaProperties(Map<String, Object> properties, List<Feature> features) {
        Validate.notEmpty(properties, "Properties map is null or empty");
        if (features != null) {
            Validate.noNullElements(features, "Array of features has null elements");
        }

        this.properties = Collections.unmodifiableMap(new HashMap<>(properties));
        this.features =
                features != null ?
                Collections.unmodifiableList(new ArrayList<>(features)) :
                Collections.emptyList();
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public void applyToGeneric(Map<String, Object> schemaMap) {
        schemaMap.putAll(applyFeaturesToProperties());
    }

    @Override
    public String toString() {
        return String.format("%s: {properties: %s}", this.getClass().getSimpleName(), properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        SetSchemaProperties that = (SetSchemaProperties)obj;
        return
                Objects.equals(this.properties, that.properties);
    }

    private Map<String, Object> applyFeaturesToProperties() {
        if (features.isEmpty()) {
            return properties;
        }

        return applyFeaturesToMap(properties);
    }

    private Map<String, Object> applyFeaturesToMap(Map<String, Object> map) {
        if (map == null) {
            return map;
        }

        Set<Map.Entry<String, Object>> entries = map.entrySet();
        Map<String, Object> processed = new LinkedHashMap<>((int) (entries.size() / 0.75));
        for (Map.Entry<String, Object> entry : entries) {
            processed.put(
                    applyFeaturesToKey(entry.getKey()),
                    applyFeaturesToObject(entry.getValue()));
        }
        return processed;
    }

    private List<Object> applyFeaturesToList(List<Object> list) {
        if (list == null) {
            return list;
        }

        List<Object> processed = new ArrayList<>(list.size());
        for (Object listElem : list) {
            processed.add(applyFeaturesToObject(listElem));
        }
        return processed;
    }

    @SuppressWarnings("unchecked")
    private Object applyFeaturesToObject(Object value) {
        if (value == null) {
            return value;
        }

        if (value instanceof Map) {
            return applyFeaturesToMap((Map<String, Object>) value);
        } else if (value instanceof List) {
            return applyFeaturesToList((List<Object>) value);
        } else {
            return applyFeaturesToValue(value);
        }
    }

    private String applyFeaturesToKey(String key) {
        for (Feature feature : features) {
            key = feature.applyToKey(key);
        }
        return key;
    }

    private Object applyFeaturesToValue(Object value) {
        for (Feature feature : features) {
            value = feature.applyToValue(value);
        }
        return value;
    }

    public enum Feature {

        STRING_VALUES_TO_LOWER_CASE(
                Function.identity(),
                value -> {
                    if (value instanceof String) {
                        return ((String)value).toLowerCase();
                    }
                    return value;
                });

        private final Function<String, String> keyFunction;
        private final Function<Object, Object> valueFunction;

        Feature(Function<String, String> keyFunction, Function<Object, Object> valueFunction) {
            this.keyFunction = keyFunction;
            this.valueFunction = valueFunction;
        }

        public String applyToKey(String key) {
            return keyFunction.apply(key);
        }

        public Object applyToValue(Object value) {
            return valueFunction.apply(value);
        }

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Map<String, Object> properties = new HashMap<>();
        private List<Feature> features = new ArrayList<>();

        public Builder property(String key, Object value) {
            this.properties.put(key, value);
            return this;
        }
        public Builder properties(Map<String, Object> properties) {
            this.properties.putAll(properties);
            return this;
        }
        public Builder feature(Feature feature) {
            this.features.add(feature);
            return this;
        }
        public SetSchemaProperties build() {
            return new SetSchemaProperties(properties, features);
        }

    }

}
