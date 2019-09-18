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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrei_Tytsik
 */
public class SelectAllArrayElementsTest {

    protected Expression<?> instance() {
        return new SelectAllArrayElements();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAllArrayElementsAreSelected() throws Exception {
        List<String> array = Arrays.asList("1", "2", "3", null);

        List<EvaluationResult> output = ((Expression<List<String>>)instance()).eval(array);

        Assert.assertNotNull(output);
        Assert.assertEquals(4, output.size());

        Assert.assertEquals("1", output.get(0).getValue());
        Assert.assertEquals(array, output.get(0).getContainer());

        Assert.assertEquals("2", output.get(1).getValue());
        Assert.assertEquals(array, output.get(1).getContainer());

        Assert.assertEquals("3", output.get(2).getValue());
        Assert.assertEquals(array, output.get(2).getContainer());

        Assert.assertEquals(null, output.get(3).getValue());
        Assert.assertEquals(array, output.get(3).getContainer());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testEmptyResultIsSelectedForEmptyOrNullArray() throws Exception {
        Expression<?> instance = instance();

        List<EvaluationResult> output = (instance).eval(null);
        Assert.assertNotNull(output);
        Assert.assertTrue(output.isEmpty());

        output = ((Expression<List<String>>)instance).eval(Collections.emptyList());
        Assert.assertNotNull(output);
        Assert.assertTrue(output.isEmpty());
    }

    @Test
    public void testListValueIsAccepted() throws Exception {
        Expression<?> instance = instance();

        Assert.assertTrue(instance.accepts(Collections.emptyList()));
        Assert.assertFalse(instance.accepts(new Object()));
        Assert.assertFalse(instance.accepts(1L));
        Assert.assertFalse(instance.accepts(""));
        Assert.assertFalse(instance.accepts(null));
    }

}
