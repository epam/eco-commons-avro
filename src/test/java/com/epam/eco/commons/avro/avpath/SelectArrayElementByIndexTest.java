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
public class SelectArrayElementByIndexTest {

    protected Expression<?> instance(int index) {
        return new SelectArrayElementByIndex(index);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testArrayElementIsSelectedByIndex() throws Exception {
        List<String> array = Arrays.asList("1", "2", "3", null);

        List<EvaluationResult> output = ((Expression<List<String>>)instance(0)).eval(array);
        Assert.assertNotNull(output);
        Assert.assertEquals(1, output.size());
        Assert.assertEquals("1", output.get(0).getValue());
        Assert.assertEquals(array, output.get(0).getContainer());

        output = ((Expression<List<String>>)instance(1)).eval(array);
        Assert.assertNotNull(output);
        Assert.assertEquals(1, output.size());
        Assert.assertEquals("2", output.get(0).getValue());
        Assert.assertEquals(array, output.get(0).getContainer());

        output = ((Expression<List<String>>)instance(2)).eval(array);
        Assert.assertNotNull(output);
        Assert.assertEquals(1, output.size());
        Assert.assertEquals("3", output.get(0).getValue());
        Assert.assertEquals(array, output.get(0).getContainer());

        output = ((Expression<List<String>>)instance(3)).eval(array);
        Assert.assertNotNull(output);
        Assert.assertEquals(1, output.size());
        Assert.assertEquals(null, output.get(0).getValue());
        Assert.assertEquals(array, output.get(0).getContainer());
    }

    @Test
    public void testEmptyResultIsSelectedForNullArray() throws Exception {
        List<EvaluationResult> output = (instance(0)).eval(null);
        Assert.assertNotNull(output);
        Assert.assertTrue(output.isEmpty());
    }

    @Test
    public void testListValueIsAccepted() throws Exception {
        Expression<?> instance = instance(0);

        Assert.assertTrue(instance.accepts(Collections.emptyList()));
        Assert.assertFalse(instance.accepts(new Object()));
        Assert.assertFalse(instance.accepts(1L));
        Assert.assertFalse(instance.accepts(""));
        Assert.assertFalse(instance.accepts(null));
    }

}
