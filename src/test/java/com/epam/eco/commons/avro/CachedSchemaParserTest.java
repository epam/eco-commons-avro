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
package com.epam.eco.commons.avro;

import org.apache.avro.Schema;
import org.junit.Assert;
import org.junit.Test;

import com.epam.eco.commons.avro.data.TestPerson;

/**
 * @author Andrei_Tytsik
 */
public class CachedSchemaParserTest {

    @Test
    public void testSchemaIsCached() throws Exception {
        Schema schema1 = CachedSchemaParser.parse(TestPerson.SCHEMA$.toString());

        Assert.assertNotNull(schema1);

        Schema schema2 = CachedSchemaParser.parse(TestPerson.SCHEMA$.toString());

        Assert.assertNotNull(schema2);
        Assert.assertTrue(schema1 == schema2);
    }

}
