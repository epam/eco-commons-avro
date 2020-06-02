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

import java.util.ArrayList;
import java.util.Arrays;
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
public class ExpressionChainTest {

    @SuppressWarnings("serial")
    @Test
    public void testAllChainExpressionsEvaluateInput() throws Exception {
        ExpressionChain instance = new ExpressionChain(Arrays.asList(
                new SelectAllArrayElements(),
                new SelectMapValueByKey("100")));

        Map<Utf8, String> map = new LinkedHashMap<Utf8, String>() {{
            put(new Utf8("1"), "1");
            put(new Utf8("2"), "2");
            put(new Utf8("3"), "3");
            put(new Utf8("100"), "100");

        }};

        List<Map<Utf8, String>> array = new ArrayList<>();
        array.add(map);

        List<EvaluationResult> output = instance.eval(array);
        Assert.assertNotNull(output);
        Assert.assertEquals(1, output.size());

        Assert.assertEquals("100", output.get(0).getValue());
        Assert.assertEquals(map, output.get(0).getContainer());
    }

    @Test
    public void testEmptyResultIsSelectedForNullObject() throws Exception {
        ExpressionChain instance = new ExpressionChain(Arrays.asList(
                new SelectAllArrayElements(),
                new SelectMapValueByKey("100")));

        List<EvaluationResult> output = instance.eval(null);
        Assert.assertNotNull(output);
        Assert.assertTrue(output.isEmpty());
    }

    @Test
    public void testArrayValueIsAccepted() throws Exception {
        ExpressionChain instance = new ExpressionChain(Arrays.asList(
                new SelectAllArrayElements(),
                new SelectMapValueByKey("100")));

        Assert.assertTrue(instance.accepts(Collections.emptyList()));
        Assert.assertFalse(instance.accepts(Collections.emptyMap()));
        Assert.assertFalse(instance.accepts(1L));
        Assert.assertFalse(instance.accepts(""));
        Assert.assertFalse(instance.accepts(null));
    }

}
