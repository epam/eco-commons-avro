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

import org.junit.Assert;
import org.junit.Test;

import com.epam.eco.commons.avro.data.TestPerson;

/**
 * @author Andrei_Tytsik
 */
public class FieldExtractorTest {

    @Test
    public void testFieldInfosExtracted() throws Exception {
        List<FieldInfo> infos = FieldExtractor.fromSchema(TestPerson.SCHEMA$);

        Assert.assertNotNull(infos);
        Assert.assertEquals(13, infos.size());

        Set<String> paths = infos.stream().
                map(FieldInfo::getPath).
                collect(Collectors.toSet());

        Assert.assertEquals(13, paths.size());
        Assert.assertTrue(paths.contains("age"));
        Assert.assertTrue(paths.contains("name"));
        Assert.assertTrue(paths.contains("hobby"));
        Assert.assertTrue(paths.contains("hobby.kind"));
        Assert.assertTrue(paths.contains("hobby.description"));
        Assert.assertTrue(paths.contains("job"));
        Assert.assertTrue(paths.contains("job.company"));
        Assert.assertTrue(paths.contains("job.position"));
        Assert.assertTrue(paths.contains("job.position.title"));
        Assert.assertTrue(paths.contains("job.position.skill"));
        Assert.assertTrue(paths.contains("job.position.skill.level"));
        Assert.assertTrue(paths.contains("job.position.skill.description"));
        Assert.assertTrue(paths.contains("job.previousJob"));
    }

}
