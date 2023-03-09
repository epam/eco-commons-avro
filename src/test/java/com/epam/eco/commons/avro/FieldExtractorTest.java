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
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.epam.eco.commons.avro.data.TestPerson;

/**
 * @author Andrei_Tytsik
 */
public class FieldExtractorTest {

    @Test
    public void testFieldInfosExtracted() {
        List<FieldInfo> infos = FieldExtractor.fromSchema(TestPerson.SCHEMA$);

        Assertions.assertNotNull(infos);
        Assertions.assertEquals(13, infos.size());

        Set<String> paths = infos.stream().
                map(FieldInfo::getPath).
                collect(Collectors.toSet());

        Assertions.assertEquals(13, paths.size());
        Assertions.assertTrue(paths.contains("age"));
        Assertions.assertTrue(paths.contains("name"));
        Assertions.assertTrue(paths.contains("hobby"));
        Assertions.assertTrue(paths.contains("hobby.kind"));
        Assertions.assertTrue(paths.contains("hobby.description"));
        Assertions.assertTrue(paths.contains("job"));
        Assertions.assertTrue(paths.contains("job.company"));
        Assertions.assertTrue(paths.contains("job.position"));
        Assertions.assertTrue(paths.contains("job.position.title"));
        Assertions.assertTrue(paths.contains("job.position.skill"));
        Assertions.assertTrue(paths.contains("job.position.skill.level"));
        Assertions.assertTrue(paths.contains("job.position.skill.description"));
        Assertions.assertTrue(paths.contains("job.previousJob"));
    }

}
