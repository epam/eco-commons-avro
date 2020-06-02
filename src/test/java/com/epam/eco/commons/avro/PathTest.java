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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrei_Tytsik
 */
public class PathTest {

    @Test
    public void testPathIsChanged() throws Exception {
        Path path = new Path("name1");

        path.append("name2");
        Assert.assertEquals("name1.name2", path.getPathString());

        path.append("name3");
        Assert.assertEquals("name1.name2.name3", path.getPathString());

        path.subtract();
        Assert.assertEquals("name1.name2", path.getPathString());

        path.subtract();
        Assert.assertEquals("name1", path.getPathString());

        path.subtract();
        Assert.assertEquals(null, path.getPathString());
    }

    @Test
    public void testPathStartsWithAnotherPath() throws Exception {
        Path path = new Path("name1.name2.name3");
        Path pathAnother = new Path("name1.name2");

        Assert.assertTrue(path.startsWith(pathAnother));
    }

    @Test
    public void testPathDoesntStartWithAnotherPath() throws Exception {
        Path path = new Path("name1.name2.name3");
        Path pathAnother = new Path("name4.name5");

        Assert.assertFalse(path.startsWith(pathAnother));
    }

    @Test(expected=RuntimeException.class)
    public void testFailToSubtractEmptyPath() throws Exception {
        Path path = new Path(null);
        path.subtract();
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFailToInitInvalidPath() throws Exception {
        new Path("  ");
    }

    @Test
    public void testPathTokensAreResolved() throws Exception {
        Path path = new Path("name1.name2.name3.name4");

        List<String> tokens = path.getPathTokens();

        Assert.assertNotNull(tokens);
        Assert.assertTrue(tokens.size() == 4);
        Assert.assertEquals("name1", tokens.get(0));
        Assert.assertEquals("name2", tokens.get(1));
        Assert.assertEquals("name3", tokens.get(2));
        Assert.assertEquals("name4", tokens.get(3));
    }

}
