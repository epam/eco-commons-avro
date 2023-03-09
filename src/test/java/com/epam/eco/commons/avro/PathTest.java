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

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Andrei_Tytsik
 */
public class PathTest {

    @Test
    public void testPathIsChanged() {
        Path path = new Path("name1");

        path.append("name2");
        Assertions.assertEquals("name1.name2", path.getPathString());

        path.append("name3");
        Assertions.assertEquals("name1.name2.name3", path.getPathString());

        path.subtract();
        Assertions.assertEquals("name1.name2", path.getPathString());

        path.subtract();
        Assertions.assertEquals("name1", path.getPathString());

        path.subtract();
        Assertions.assertNull(path.getPathString());
    }

    @Test
    public void testPathStartsWithAnotherPath() {
        Path path = new Path("name1.name2.name3");
        Path pathAnother = new Path("name1.name2");

        Assertions.assertTrue(path.startsWith(pathAnother));
    }

    @Test
    public void testPathDoesntStartWithAnotherPath() {
        Path path = new Path("name1.name2.name3");
        Path pathAnother = new Path("name4.name5");

        Assertions.assertFalse(path.startsWith(pathAnother));
    }

    @Test
    public void testFailToSubtractEmptyPath() {
        assertThrows(
                RuntimeException.class,
                () -> {
                    Path path = new Path(null);
                    path.subtract();
                }
        );
    }

    @Test
    public void testFailToInitInvalidPath() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Path("  ")
        );
    }

    @Test
    public void testPathTokensAreResolved() {
        Path path = new Path("name1.name2.name3.name4");

        List<String> tokens = path.getPathTokens();

        Assertions.assertNotNull(tokens);
        Assertions.assertEquals(4, tokens.size());
        Assertions.assertEquals("name1", tokens.get(0));
        Assertions.assertEquals("name2", tokens.get(1));
        Assertions.assertEquals("name3", tokens.get(2));
        Assertions.assertEquals("name4", tokens.get(3));
    }

}
