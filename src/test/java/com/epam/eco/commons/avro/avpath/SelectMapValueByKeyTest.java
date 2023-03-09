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
package com.epam.eco.commons.avro.avpath;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.util.Utf8;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Andrei_Tytsik
 */
public class SelectMapValueByKeyTest {

    protected Expression<?> instance(Object key) {
        return new SelectMapValueByKey(key);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testMapValueIsSelectedByKey() {
        Map<Utf8, String> map = new LinkedHashMap<>() {{
            put(new Utf8("1"), "1");
            put(new Utf8("2"), "2");
            put(new Utf8("3"), "3");
            put(new Utf8("x"), null);

        }};

        List<EvaluationResult> output = ((Expression<Map<Utf8, String>>)instance("1")).eval(map);
        Assertions.assertNotNull(output);
        Assertions.assertEquals(1, output.size());
        Assertions.assertEquals("1", output.get(0).getValue());
        Assertions.assertEquals(map, output.get(0).getContainer());

        output = ((Expression<Map<Utf8, String>>)instance("2")).eval(map);
        Assertions.assertNotNull(output);
        Assertions.assertEquals(1, output.size());
        Assertions.assertEquals("2", output.get(0).getValue());
        Assertions.assertEquals(map, output.get(0).getContainer());

        output = ((Expression<Map<Utf8, String>>)instance("3")).eval(map);
        Assertions.assertNotNull(output);
        Assertions.assertEquals(1, output.size());
        Assertions.assertEquals("3", output.get(0).getValue());
        Assertions.assertEquals(map, output.get(0).getContainer());

        output = ((Expression<Map<Utf8, String>>)instance("x")).eval(map);
        Assertions.assertNotNull(output);
        Assertions.assertEquals(1, output.size());
        Assertions.assertNull(output.get(0).getValue());
        Assertions.assertEquals(map, output.get(0).getContainer());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testEmptyResultIsSelectedForEmptyOrNullMap() {
        List<EvaluationResult> output = (instance("1")).eval(null);
        Assertions.assertNotNull(output);
        Assertions.assertTrue(output.isEmpty());

        output = ((Expression<Map<Utf8, String>>)instance("1")).eval(Collections.emptyMap());
        Assertions.assertNotNull(output);
        Assertions.assertTrue(output.isEmpty());
    }

    @Test
    public void testMapValueIsAccepted() {
        Expression<?> instance = instance(null);

        Assertions.assertTrue(instance.accepts(Collections.emptyMap()));
        Assertions.assertFalse(instance.accepts(new Object()));
        Assertions.assertFalse(instance.accepts(1L));
        Assertions.assertFalse(instance.accepts(""));
        Assertions.assertFalse(instance.accepts(null));
    }

}
