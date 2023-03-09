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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.epam.eco.commons.avro.data.TestPerson;
import com.epam.eco.commons.avro.data.TestPersonDataReader;

/**
 * @author Andrei_Tytsik
 */
public class GetRecordFieldTest {

    @Test
    public void testFieldIsGot() {
        TestPerson person = TestPersonDataReader.readSpecificTestPersons().get(0);

        GetRecordField instance = new GetRecordField("name");
        List<EvaluationResult> output = instance.eval(person);
        Assertions.assertNotNull(output);
        Assertions.assertEquals(1, output.size());

        Assertions.assertEquals("Erich Gamma", output.get(0).getValue().toString());
        Assertions.assertEquals(person, output.get(0).getContainer());

        instance = new GetRecordField("age");
        output = instance.eval(person);
        Assertions.assertNotNull(output);
        Assertions.assertEquals(1, output.size());

        Assertions.assertEquals(55, output.get(0).getValue());
        Assertions.assertEquals(person, output.get(0).getContainer());
    }

    @Test
    public void testEmptyResultIsSelectedForNullRecord() {
        GetRecordField instance = new GetRecordField("name");

        List<EvaluationResult> output = instance.eval(null);
        Assertions.assertNotNull(output);
        Assertions.assertTrue(output.isEmpty());
    }

    @Test
    public void testRecordValueIsAccepted() {
        Expression<?> instance = new GetRecordField("name");

        Assertions.assertTrue(instance.accepts(new TestPerson()));
        Assertions.assertFalse(instance.accepts(new Object()));
        Assertions.assertFalse(instance.accepts(1L));
        Assertions.assertFalse(instance.accepts(""));
        Assertions.assertFalse(instance.accepts(null));
    }

}
