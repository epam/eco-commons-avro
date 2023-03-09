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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Andrei_Tytsik
 */
public class PathTemplateTest {

    @Test
    public void testRecognizedAsElementSelector() {
        Assertions.assertFalse(PathTemplate.with(null, null).isElementSelector());
        Assertions.assertFalse(PathTemplate.with("/job", null).isElementSelector());
        Assertions.assertFalse(PathTemplate.with("/hobby[*]/description", null).isElementSelector());
        Assertions.assertTrue(PathTemplate.with("/hobby[*]", null).isElementSelector());
    }

    @Test
    public void testFormattedToPlainPath() {
        String plain = PathTemplate.with(null, null).toPlainPath();
        Assertions.assertNull(plain);

        plain = PathTemplate.with("", null).toPlainPath();
        Assertions.assertEquals("", plain);

        plain = PathTemplate.with("/job", null).toPlainPath();
        Assertions.assertEquals("job", plain);

        plain = PathTemplate.with("/job/description", null).toPlainPath();
        Assertions.assertEquals("job.description", plain);

        plain = PathTemplate.with("/hobby[*]", null).toPlainPath();
        Assertions.assertEquals("hobby", plain);

        plain = PathTemplate.with("/hobby[*]/description", null).toPlainPath();
        Assertions.assertEquals("hobby.description", plain);

        plain = PathTemplate.with("/hobby[*]/description[*]", null).toPlainPath();
        Assertions.assertEquals("hobby.description", plain);

        plain = PathTemplate.with("/job[*]/description", null).toPlainPath("$");
        Assertions.assertEquals("job$description", plain);
    }

}
