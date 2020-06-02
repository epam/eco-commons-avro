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
package com.epam.eco.commons.avro.traversal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.avro.Schema;

import com.epam.eco.commons.avro.AvroUtils;

/**
 * @author Andrei_Tytsik
 */
public class SchemaTraverseTestData {

    public final static String SCHEMA_JSON =
            "{\"type\": \"record\"," +
            "\"name\": \"TestPerson\"," +
            "\"namespace\" : \"com.epam.eco.schemaregistry.client.avro.data\"," +
            "\"fields\": [" +
                "{\"name\": \"age\", \"type\": [\"null\", \"int\"]}," +
                "{\"name\": \"hobby\", \"type\": [\"null\", " +
                    "{\"type\": \"array\", \"items\": " +
                        "{\"name\": \"TestHobby\", \"type\": \"record\", \"fields\":[" +
                            "{\"name\": \"kind\", \"type\": \"string\"}," +
                            "{\"name\": \"description\", \"type\": \"string\"}" +
                        "]}" +
                    "}" +
                "]}," +
                "{\"name\": \"job\", \"type\":" +
                    "{\"type\": \"record\", \"name\": \"TestJob\", \"fields\":[" +
                        "{\"name\": \"position\", \"type\":" +
                            "{\"type\": \"record\", \"name\": \"TestPosition\", \"fields\":[" +
                                "{\"name\": \"skill\", \"type\":" +
                                    "{\"type\": \"map\", \"values\":" +
                                        "{\"type\": \"record\", \"name\": \"TestSkillLevel\", \"fields\":[" +
                                            "{\"name\": \"level\", \"type\": \"string\"}" +
                                        "]}" +
                                    "}" +
                                "}" +
                            "]}" +
                        "}," +
                        "{\"name\": \"previousJob\", \"type\":  [\"null\", \"TestJob\"]}" +
                    "]}" +
                "}]" +
            "}";

    public final static Schema SCHEMA = new Schema.Parser().parse(SCHEMA_JSON);

    public final static Object GENERIC_SCHEMA = AvroUtils.schemaToGeneric(SCHEMA);

    public static final int RECORD_COUNT = 6;
    public static final int UNION_COUNT = 3;
    public static final int NULL_COUNT = 3;
    public static final int ARRAY_COUNT = 1;
    public static final int MAP_COUNT = 1;
    public static final int INT_COUNT = 1;
    public static final int STRING_COUNT = 3;
    public static final Set<String> FIELD_PATHS = new HashSet<>(Arrays.asList(
            "age", "hobby", "hobby.kind", "hobby.description", "job", "job.position", "job.position.skill", "job.position.skill.level", "job.previousJob"
            ));

    public static final String DESIRED_PATH = "job.position.skill.level";
    public static final int DESIRED_PATH_RECORD_COUNT = 4;
    public static final int DESIRED_PATH_UNION_COUNT = 0;
    public static final int DESIRED_PATH_NULL_COUNT = 0;
    public static final int DESIRED_PATH_ARRAY_COUNT = 0;
    public static final int DESIRED_PATH_MAP_COUNT = 1;
    public static final int DESIRED_PATH_INT_COUNT = 0;
    public static final int DESIRED_PATH_STRING_COUNT = 1;
    public static final Set<String> DESIRED_PATH_FIELD_PATHS = new HashSet<>(Arrays.asList(
            "job", "job.position", "job.position.skill", "job.position.skill.level"
            ));

}
