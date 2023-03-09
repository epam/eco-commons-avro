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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.epam.eco.commons.avro.modification.SetSchemaProperties.Feature;

/**
 * @author Andrei_Tytsik
 */
public class AvroSetPropertiesTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testStringValuesAreLowerCased() {
        Map<String, Object> properties = new LinkedHashMap<>();
        properties.put("keyStr", "VaLue");
        properties.put("keyInt", 1);
        properties.put("keyObj", new Object());
        properties.put("keyNull", null);
        properties.put("keyList", Arrays.asList("ValUe", null, new Object(), 1));

        Map<String, Object> subMap = new LinkedHashMap<>();
        subMap.put("keyStr", "VaLue");
        subMap.put("keyInt", 1);
        subMap.put("keyObj", new Object());
        subMap.put("keyNull", null);
        subMap.put("keyList", Arrays.asList("ValUe", null, new Object(), 1));

        properties.put("keySubMap", subMap);

        SetSchemaProperties setProperties = SetSchemaProperties.builder().
                properties(properties).
                feature(Feature.STRING_VALUES_TO_LOWER_CASE).
                build();

        Map<String, Object> genericSchema = new LinkedHashMap<>();

        setProperties.applyToGeneric(genericSchema);

        Assertions.assertEquals(genericSchema.get("keyStr"), "value");
        Assertions.assertEquals(((List<Object>)genericSchema.get("keyList")).get(0), "value");
        Assertions.assertEquals(((Map<String, Object>)genericSchema.get("keySubMap")).get("keyStr"), "value");
        Assertions.assertEquals(((List<Object>)((Map<String, Object>)genericSchema.get("keySubMap")).get("keyList")).get(0), "value");
    }

}
