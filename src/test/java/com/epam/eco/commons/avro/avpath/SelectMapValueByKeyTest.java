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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.util.Utf8;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrei_Tytsik
 */
public class SelectMapValueByKeyTest {

    protected Expression<?> instance(Object key) {
        return new SelectMapValueByKey(key);
    }

    @SuppressWarnings({ "serial", "unchecked" })
    @Test
    public void testMapValueIsSelectedByKey() throws Exception {
        Map<Utf8, String> map = new LinkedHashMap<Utf8, String>() {{
            put(new Utf8("1"), "1");
            put(new Utf8("2"), "2");
            put(new Utf8("3"), "3");
            put(new Utf8("x"), null);

        }};

        List<EvaluationResult> output = ((Expression<Map<Utf8, String>>)instance("1")).eval(map);
        Assert.assertNotNull(output);
        Assert.assertEquals(1, output.size());
        Assert.assertEquals("1", output.get(0).getValue());
        Assert.assertEquals(map, output.get(0).getContainer());

        output = ((Expression<Map<Utf8, String>>)instance("2")).eval(map);
        Assert.assertNotNull(output);
        Assert.assertEquals(1, output.size());
        Assert.assertEquals("2", output.get(0).getValue());
        Assert.assertEquals(map, output.get(0).getContainer());

        output = ((Expression<Map<Utf8, String>>)instance("3")).eval(map);
        Assert.assertNotNull(output);
        Assert.assertEquals(1, output.size());
        Assert.assertEquals("3", output.get(0).getValue());
        Assert.assertEquals(map, output.get(0).getContainer());

        output = ((Expression<Map<Utf8, String>>)instance("x")).eval(map);
        Assert.assertNotNull(output);
        Assert.assertEquals(1, output.size());
        Assert.assertEquals(null, output.get(0).getValue());
        Assert.assertEquals(map, output.get(0).getContainer());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testEmptyResultIsSelectedForEmptyOrNullMap() throws Exception {
        List<EvaluationResult> output = (instance("1")).eval(null);
        Assert.assertNotNull(output);
        Assert.assertTrue(output.isEmpty());

        output = ((Expression<Map<Utf8, String>>)instance("1")).eval(Collections.emptyMap());
        Assert.assertNotNull(output);
        Assert.assertTrue(output.isEmpty());
    }

    @Test
    public void testMapValueIsAccepted() throws Exception {
        Expression<?> instance = instance(null);

        Assert.assertTrue(instance.accepts(Collections.emptyMap()));
        Assert.assertFalse(instance.accepts(new Object()));
        Assert.assertFalse(instance.accepts(1L));
        Assert.assertFalse(instance.accepts(""));
        Assert.assertFalse(instance.accepts(null));
    }

}
