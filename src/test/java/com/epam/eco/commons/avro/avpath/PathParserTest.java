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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrei_Tytsik
 */
public class PathParserTest {

    @Test
    public void testPathIsParsed() throws Exception {
        Expression<?> exp = new PathParser("/aa/bb[1]/_cc/dd[null]/ee[*]/ff['1']").parse();

        Assert.assertNotNull(exp);
        Assert.assertTrue(exp instanceof ExpressionChain);

        ExpressionChain chain = (ExpressionChain)exp;
        Assert.assertEquals(10, chain.getExpressions().size());

        Expression<?> chainExp = chain.getExpressions().get(0);
        Assert.assertTrue(chainExp instanceof GetRecordField);
        Assert.assertEquals("aa", ((GetRecordField)chainExp).getFieldName());

        chainExp = chain.getExpressions().get(1);
        Assert.assertTrue(chainExp instanceof GetRecordField);
        Assert.assertEquals("bb", ((GetRecordField)chainExp).getFieldName());

        chainExp = chain.getExpressions().get(2);
        Assert.assertTrue(chainExp instanceof SelectElementByKey);
        Assert.assertEquals(1, ((SelectElementByKey)chainExp).getKey());

        chainExp = chain.getExpressions().get(3);
        Assert.assertTrue(chainExp instanceof GetRecordField);
        Assert.assertEquals("_cc", ((GetRecordField)chainExp).getFieldName());

        chainExp = chain.getExpressions().get(4);
        Assert.assertTrue(chainExp instanceof GetRecordField);
        Assert.assertEquals("dd", ((GetRecordField)chainExp).getFieldName());

        chainExp = chain.getExpressions().get(5);
        Assert.assertTrue(chainExp instanceof SelectElementByKey);
        Assert.assertEquals(null, ((SelectElementByKey)chainExp).getKey());

        chainExp = chain.getExpressions().get(6);
        Assert.assertTrue(chainExp instanceof GetRecordField);
        Assert.assertEquals("ee", ((GetRecordField)chainExp).getFieldName());

        chainExp = chain.getExpressions().get(7);
        Assert.assertTrue(chainExp instanceof SelectAllElements);

        chainExp = chain.getExpressions().get(8);
        Assert.assertTrue(chainExp instanceof GetRecordField);
        Assert.assertEquals("ff", ((GetRecordField)chainExp).getFieldName());

        chainExp = chain.getExpressions().get(9);
        Assert.assertTrue(chainExp instanceof SelectElementByKey);
        Assert.assertEquals("1", ((SelectElementByKey)chainExp).getKey());
    }

    @Test
    public void testPathIsParsedMultipleTimes() throws Exception {
        PathParser parser = new PathParser("/aa/bb");
        parser.parse();
        parser.parse();
    }

    @Test(expected=PathParseException.class)
    public void testParserFailedOnIlleglaFieldBegin1() throws Exception {
        new PathParser("aa").parse();
    }

    @Test(expected=PathParseException.class)
    public void testParserFailedOnIlleglaFieldBegin2() throws Exception {
        new PathParser("/aa[]bb").parse();
    }

    @Test(expected=PathParseException.class)
    public void testParserFailedOnInvalidFieldName1() throws Exception {
        new PathParser("/1aa").parse();
    }

    @Test(expected=PathParseException.class)
    public void testParserFailedOnInvalidFieldName2() throws Exception {
        new PathParser("/aa/1bb").parse();
    }

    @Test(expected=PathParseException.class)
    public void testParserFailedOnNotEnclosedSelectCriteria1() throws Exception {
        new PathParser("/aa[12").parse();
    }

    @Test(expected=PathParseException.class)
    public void testParserFailedOnNotEnclosedSelectCriteria2() throws Exception {
        new PathParser("/aa[12]/bb['xx'").parse();
    }

    @Test(expected=PathParseException.class)
    public void testParserFailedOnInvalidSelectCriteria1() throws Exception {
        new PathParser("/1aa[ ]").parse();
    }

    @Test(expected=PathParseException.class)
    public void testParserFailedOnInvalidSelectCriteria2() throws Exception {
        new PathParser("/1aa[]").parse();
    }

    @Test(expected=PathParseException.class)
    public void testParserFailedOnInvalidSelectCriteria3() throws Exception {
        new PathParser("/1aa[aa]").parse();
    }

}
