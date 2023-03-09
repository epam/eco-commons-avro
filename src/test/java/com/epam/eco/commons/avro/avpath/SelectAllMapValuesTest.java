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
public class SelectAllMapValuesTest {

    protected Expression<?> instance() {
        return new SelectAllMapValues();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAllMapValuesAreSelected() {
        Map<String, String> map = new LinkedHashMap<>() {{
            put("1", "1");
            put("2", "2");
            put("3", "3");
            put("x", null);

        }};

        List<EvaluationResult> output = ((Expression<Map<String, String>>)instance()).eval(map);

        Assertions.assertNotNull(output);
        Assertions.assertEquals(4, output.size());

        Assertions.assertEquals("1", output.get(0).getValue());
        Assertions.assertEquals(map, output.get(0).getContainer());

        Assertions.assertEquals("2", output.get(1).getValue());
        Assertions.assertEquals(map, output.get(1).getContainer());

        Assertions.assertEquals("3", output.get(2).getValue());
        Assertions.assertEquals(map, output.get(2).getContainer());

        Assertions.assertNull(output.get(3).getValue());
        Assertions.assertEquals(map, output.get(3).getContainer());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testEmptyResultIsSelectedForEmptyOrNullMap() {
        Expression<?> instance = instance();

        List<EvaluationResult> output = (instance).eval(null);
        Assertions.assertNotNull(output);
        Assertions.assertTrue(output.isEmpty());

        output = ((Expression<Map<Utf8, String>>)instance).eval(Collections.emptyMap());
        Assertions.assertNotNull(output);
        Assertions.assertTrue(output.isEmpty());
    }

    @Test
    public void testMapValueIsAccepted() {
        Expression<?> instance = instance();

        Assertions.assertTrue(instance.accepts(Collections.emptyMap()));
        Assertions.assertFalse(instance.accepts(new Object()));
        Assertions.assertFalse(instance.accepts(1L));
        Assertions.assertFalse(instance.accepts(""));
        Assertions.assertFalse(instance.accepts(null));
    }

}
