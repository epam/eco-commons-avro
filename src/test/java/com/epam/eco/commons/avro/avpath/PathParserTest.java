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

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Andrei_Tytsik
 */
public class PathParserTest {

    @Test
    public void testPathIsParsed() {
        Expression<?> exp = new PathParser("/aa/bb[1]/_cc/dd[null]/ee[*]/ff['1']").parse();

        Assertions.assertNotNull(exp);
        Assertions.assertTrue(exp instanceof ExpressionChain);

        ExpressionChain chain = (ExpressionChain)exp;
        Assertions.assertEquals(10, chain.getExpressions().size());

        Expression<?> chainExp = chain.getExpressions().get(0);
        Assertions.assertTrue(chainExp instanceof GetRecordField);
        Assertions.assertEquals("aa", ((GetRecordField)chainExp).getFieldName());

        chainExp = chain.getExpressions().get(1);
        Assertions.assertTrue(chainExp instanceof GetRecordField);
        Assertions.assertEquals("bb", ((GetRecordField)chainExp).getFieldName());

        chainExp = chain.getExpressions().get(2);
        Assertions.assertTrue(chainExp instanceof SelectElementByKey);
        Assertions.assertEquals(1, ((SelectElementByKey)chainExp).getKey());

        chainExp = chain.getExpressions().get(3);
        Assertions.assertTrue(chainExp instanceof GetRecordField);
        Assertions.assertEquals("_cc", ((GetRecordField)chainExp).getFieldName());

        chainExp = chain.getExpressions().get(4);
        Assertions.assertTrue(chainExp instanceof GetRecordField);
        Assertions.assertEquals("dd", ((GetRecordField)chainExp).getFieldName());

        chainExp = chain.getExpressions().get(5);
        Assertions.assertTrue(chainExp instanceof SelectElementByKey);
        Assertions.assertNull(((SelectElementByKey) chainExp).getKey());

        chainExp = chain.getExpressions().get(6);
        Assertions.assertTrue(chainExp instanceof GetRecordField);
        Assertions.assertEquals("ee", ((GetRecordField)chainExp).getFieldName());

        chainExp = chain.getExpressions().get(7);
        Assertions.assertTrue(chainExp instanceof SelectAllElements);

        chainExp = chain.getExpressions().get(8);
        Assertions.assertTrue(chainExp instanceof GetRecordField);
        Assertions.assertEquals("ff", ((GetRecordField)chainExp).getFieldName());

        chainExp = chain.getExpressions().get(9);
        Assertions.assertTrue(chainExp instanceof SelectElementByKey);
        Assertions.assertEquals("1", ((SelectElementByKey)chainExp).getKey());
    }

    @Test
    public void testPathIsParsedMultipleTimes() {
        PathParser parser = new PathParser("/aa/bb");
        parser.parse();
        parser.parse();
    }

    @Test
    public void testParserFailedOnIlleglaFieldBegin1() {
        assertThrows(PathParseException.class, () -> new PathParser("aa").parse());
    }

    @Test
    public void testParserFailedOnIlleglaFieldBegin2() {
        assertThrows(PathParseException.class, () -> new PathParser("/aa[]bb").parse());
    }

    @Test
    public void testParserFailedOnInvalidFieldName1() {
        assertThrows(PathParseException.class, () -> new PathParser("/1aa").parse());
    }

    @Test
    public void testParserFailedOnInvalidFieldName2() {
        assertThrows(PathParseException.class, () -> new PathParser("/aa/1bb").parse());
    }

    @Test
    public void testParserFailedOnNotEnclosedSelectCriteria1() {
        assertThrows(PathParseException.class, () -> new PathParser("/aa[12").parse());
    }

    @Test
    public void testParserFailedOnNotEnclosedSelectCriteria2() {
        assertThrows(PathParseException.class, () -> new PathParser("/aa[12]/bb['xx'").parse());
    }

    @Test
    public void testParserFailedOnInvalidSelectCriteria1() {
        assertThrows(PathParseException.class, () -> new PathParser("/1aa[ ]").parse());
    }

    @Test
    public void testParserFailedOnInvalidSelectCriteria2() {
        assertThrows(PathParseException.class, () -> new PathParser("/1aa[]").parse());
    }

    @Test
    public void testParserFailedOnInvalidSelectCriteria3() {
        assertThrows(PathParseException.class, () -> new PathParser("/1aa[aa]").parse());
    }

}
