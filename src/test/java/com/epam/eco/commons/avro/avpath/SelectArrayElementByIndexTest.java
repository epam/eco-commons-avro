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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Andrei_Tytsik
 */
public class SelectArrayElementByIndexTest {

    protected Expression<?> instance(int index) {
        return new SelectArrayElementByIndex(index);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testArrayElementIsSelectedByIndex() {
        List<String> array = Arrays.asList("1", "2", "3", null);

        List<EvaluationResult> output = ((Expression<List<String>>)instance(0)).eval(array);
        Assertions.assertNotNull(output);
        Assertions.assertEquals(1, output.size());
        Assertions.assertEquals("1", output.get(0).getValue());
        Assertions.assertEquals(array, output.get(0).getContainer());

        output = ((Expression<List<String>>)instance(1)).eval(array);
        Assertions.assertNotNull(output);
        Assertions.assertEquals(1, output.size());
        Assertions.assertEquals("2", output.get(0).getValue());
        Assertions.assertEquals(array, output.get(0).getContainer());

        output = ((Expression<List<String>>)instance(2)).eval(array);
        Assertions.assertNotNull(output);
        Assertions.assertEquals(1, output.size());
        Assertions.assertEquals("3", output.get(0).getValue());
        Assertions.assertEquals(array, output.get(0).getContainer());

        output = ((Expression<List<String>>)instance(3)).eval(array);
        Assertions.assertNotNull(output);
        Assertions.assertEquals(1, output.size());
        Assertions.assertNull(output.get(0).getValue());
        Assertions.assertEquals(array, output.get(0).getContainer());
    }

    @Test
    public void testEmptyResultIsSelectedForNullArray() {
        List<EvaluationResult> output = (instance(0)).eval(null);
        Assertions.assertNotNull(output);
        Assertions.assertTrue(output.isEmpty());
    }

    @Test
    public void testListValueIsAccepted() {
        Expression<?> instance = instance(0);

        Assertions.assertTrue(instance.accepts(Collections.emptyList()));
        Assertions.assertFalse(instance.accepts(new Object()));
        Assertions.assertFalse(instance.accepts(1L));
        Assertions.assertFalse(instance.accepts(""));
        Assertions.assertFalse(instance.accepts(null));
    }

}
