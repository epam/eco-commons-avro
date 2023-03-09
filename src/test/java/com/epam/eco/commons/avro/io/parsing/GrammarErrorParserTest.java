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
package com.epam.eco.commons.avro.io.parsing;

import java.io.IOException;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.io.parsing.ResolvingGrammarGenerator;
import org.apache.avro.io.parsing.Symbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.epam.eco.commons.avro.AvroUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Andrei_Tytsik
 */
public class GrammarErrorParserTest {

    private static final Schema WRITER_SCHEMA = AvroUtils.schemaFromJson(
            "{\"type\":\"record\",\"name\":\"R1\",\"fields\":["
                    + "{\"name\":\"f1\",\"type\":{\"type\":\"record\",\"name\":\"R1_1\",\"fields\":["
                            + "{\"name\":\"f1_1\",\"type\":\"boolean\"},"
                            + "{\"name\":\"f1_2\",\"type\":{\"type\":\"record\",\"name\":\"R1_1_1\",\"fields\":["
                                + "{\"name\":\"f1_2_1\",\"type\":\"int\"}"
                            + "]}}"
                        + "]}},"
                    + "{\"name\":\"f2\",\"type\":\"int\"},"
                    + "{\"name\":\"f3\",\"type\":\"int\"},"
                    + "{\"name\":\"f4\",\"type\":\"long\"},"
                    + "{\"name\":\"f5\",\"type\":\"string\"},"
                    + "{\"name\":\"f6\",\"type\":\"int\"},  "
                    + "{\"name\":\"f7\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"R_7\",\"fields\":["
                        + "{\"name\":\"f7_1\",\"type\":\"string\"}"
                    + "]}}},"
                    + "{\"name\":\"f8\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":\"boolean\"}}},"
                    + "{\"name\":\"f9\",\"type\":[\"null\", \"string\"]},"
                    + "{\"name\":\"f10\",\"type\":{\"type\": \"enum\",\"name\": \"WEEKENDS\", \"symbols\": [\"SATURDAY\", \"SUNDAY\"]}},"
                    + "{\"name\":\"f11\",\"type\":\"string\"},"
                    + "{\"name\":\"f12\",\"type\":{\"type\": \"map\", \"values\": \"long\"}},"
                    + "{\"name\":\"f13\",\"type\":\"string\"},"
                    + "{\"name\":\"f14\",\"type\":{\"type\": \"fixed\", \"size\": 16, \"name\": \"md5\"}},"
                    + "{\"name\":\"f15\",\"type\":\"string\"}"
                + "]}");

    private static final Schema READER_SCHEMA = AvroUtils.schemaFromJson(
            "{\"type\":\"record\",\"name\":\"R1\",\"fields\":["
                    + "{\"name\":\"f1\",\"type\":{\"type\":\"record\",\"name\":\"R1_1\",\"fields\":["
                            + "{\"name\":\"f1_1\",\"type\":\"int\"},"
                            + "{\"name\":\"f1_2\",\"type\":{\"type\":\"record\",\"name\":\"R1_1_1\",\"fields\":["
                                + "{\"name\":\"f1_2_1\",\"type\":\"int\"}"
                            + "]}}"
                        + "]}},"
                    + "{\"name\":\"f2\",\"type\":\"int\"},"
                    + "{\"name\":\"f3\",\"type\":\"int\"},"
                    + "{\"name\":\"f4\",\"type\":\"int\"},"
                    + "{\"name\":\"f5\",\"type\":\"string\"},"
                    + "{\"name\":\"f6\",\"type\":\"int\"},  "
                    + "{\"name\":\"f7\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"R_7\",\"fields\":["
                        + "{\"name\":\"f7_1\",\"type\":\"int\"}"
                    + "]}}},"
                    + "{\"name\":\"f8\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":\"string\"}}},"
                    + "{\"name\":\"f9\",\"type\":[\"null\", {\"type\":\"record\",\"name\":\"R_9\",\"fields\":[{\"name\":\"f9_1\",\"type\":\"int\"}]}]},"
                    + "{\"name\":\"f10\",\"type\":{\"type\": \"enum\",\"name\": \"WEEKENDS\", \"symbols\": [\"SATURDAY\", \"SUNDAY\"]}},"
                    + "{\"name\":\"f11\",\"type\":\"string\"},"
                    + "{\"name\":\"f12\",\"type\":{\"type\": \"map\", \"values\": \"long\"}},"
                    + "{\"name\":\"f13\",\"type\":\"string\"},"
                    + "{\"name\":\"f14\",\"type\":{\"type\": \"fixed\", \"size\": 16, \"name\": \"md5\"}},"
                    + "{\"name\":\"f15\",\"type\":\"string\"}"
                + "]}");

    @Test
    public void testErrorsParsed() throws IOException {
        Symbol grammar = new ResolvingGrammarGenerator().generate(WRITER_SCHEMA, READER_SCHEMA);

        List<GrammarError> errors = GrammarErrorParser.parse(grammar);
        Assertions.assertNotNull(errors);
        Assertions.assertEquals(5, errors.size());

        Assertions.assertEquals("f1.f1_1", errors.get(0).getPath());
        Assertions.assertEquals("Found boolean, expecting int", errors.get(0).getMessage());

        Assertions.assertEquals("f4", errors.get(1).getPath());
        Assertions.assertEquals("Found long, expecting int", errors.get(1).getMessage());

        Assertions.assertEquals("f7.f7_1", errors.get(2).getPath());
        Assertions.assertEquals("Found string, expecting int", errors.get(2).getMessage());

        Assertions.assertEquals("f8", errors.get(3).getPath());
        Assertions.assertEquals("Found boolean, expecting string", errors.get(3).getMessage());

        Assertions.assertEquals("f9", errors.get(4).getPath());
        Assertions.assertEquals("Found string, expecting union", errors.get(4).getMessage());
    }

    @Test
    public void testNoErrorsParsed() throws IOException {
        Symbol grammar = new ResolvingGrammarGenerator().generate(WRITER_SCHEMA, WRITER_SCHEMA);

        List<GrammarError> errors = GrammarErrorParser.parse(grammar);
        Assertions.assertNotNull(errors);
        Assertions.assertEquals(0, errors.size());
    }

    @Test
    public void testFailsOnIllegalArguments() {
        assertThrows(Exception.class, () -> GrammarErrorParser.parse(null));
    }

}
