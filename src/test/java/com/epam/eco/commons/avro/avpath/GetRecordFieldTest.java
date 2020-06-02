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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.epam.eco.commons.avro.data.TestPerson;
import com.epam.eco.commons.avro.data.TestPersonDataReader;

/**
 * @author Andrei_Tytsik
 */
public class GetRecordFieldTest {

    @Test
    public void testFieldIsGot() throws Exception {
        TestPerson person = TestPersonDataReader.readSpecificTestPersons().get(0);

        GetRecordField instance = new GetRecordField("name");
        List<EvaluationResult> output = instance.eval(person);
        Assert.assertNotNull(output);
        Assert.assertEquals(1, output.size());

        Assert.assertEquals("Erich Gamma", output.get(0).getValue().toString());
        Assert.assertEquals(person, output.get(0).getContainer());

        instance = new GetRecordField("age");
        output = instance.eval(person);
        Assert.assertNotNull(output);
        Assert.assertEquals(1, output.size());

        Assert.assertEquals(55, output.get(0).getValue());
        Assert.assertEquals(person, output.get(0).getContainer());
    }

    @Test
    public void testEmptyResultIsSelectedForNullRecord() throws Exception {
        GetRecordField instance = new GetRecordField("name");

        List<EvaluationResult> output = instance.eval(null);
        Assert.assertNotNull(output);
        Assert.assertTrue(output.isEmpty());
    }

    @Test
    public void testRecordValueIsAccepted() throws Exception {
        Expression<?> instance = new GetRecordField("name");

        Assert.assertTrue(instance.accepts(new TestPerson()));
        Assert.assertFalse(instance.accepts(new Object()));
        Assert.assertFalse(instance.accepts(1L));
        Assert.assertFalse(instance.accepts(""));
        Assert.assertFalse(instance.accepts(null));
    }

}
