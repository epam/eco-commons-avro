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
package com.epam.eco.commons.avro.modification;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ihar_Karoza
 */
public class RemoveSchemaPropertiesTest {

    @Test
    public void testEquals() {
        String[] schemaProps1 = new String[]{"prop1", "prop2", "prop3"};
        String[] schemaProps2 = new String[]{"prop1", "prop2", "prop3"};
        Assert.assertEquals(
                RemoveSchemaProperties.with(schemaProps1),
                RemoveSchemaProperties.with(schemaProps2));
    }

}
