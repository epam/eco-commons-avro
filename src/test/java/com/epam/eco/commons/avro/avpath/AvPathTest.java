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

import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Type;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.util.Utf8;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.epam.eco.commons.avro.data.TestHobby;
import com.epam.eco.commons.avro.data.TestPerson;
import com.epam.eco.commons.avro.data.TestPersonDataReader;
import com.epam.eco.commons.avro.data.TestSkillLevel;

/**
 * @author Andrei_Tytsik
 */
public class AvPathTest {

    @Test
    public void testValuesAreSelected() {
        GenericRecord personErichGamma = TestPersonDataReader.readGenericTestPersons().get(0);

        String name = AvPath.INSTANCE.selectOne(personErichGamma, "/name", String.class);
        Assertions.assertNotNull(name);
        Assertions.assertEquals("Erich Gamma", name);

        Integer age = AvPath.INSTANCE.selectOne(personErichGamma, "/age", Integer.class);
        Assertions.assertNotNull(age);
        Assertions.assertEquals(Integer.valueOf(55), age);

        Object wrongPathValue = AvPath.INSTANCE.selectOne(
                personErichGamma,
                "/job/wrongpath", Object.class);
        Assertions.assertNull(wrongPathValue);

        List<String> hobbies = AvPath.INSTANCE.select(personErichGamma, "/hobby[*]/kind", String.class);
        Assertions.assertNotNull(hobbies);
        Assertions.assertEquals(2, hobbies.size());
        Assertions.assertEquals("literature", hobbies.get(0));
        Assertions.assertEquals("science", hobbies.get(1));

        List<Object> wrongPathValues = AvPath.INSTANCE.select(
                personErichGamma,
                "/hobby[*]/wrongpath/wrongpath", Object.class);
        Assertions.assertNotNull(wrongPathValues);
        Assertions.assertTrue(wrongPathValues.isEmpty());

        String cppLevelAtPreviousJob = AvPath.INSTANCE.selectOne(
                personErichGamma,
                "/job/previousJob/position/skill['c++']/level",
                String.class);
        Assertions.assertNotNull(cppLevelAtPreviousJob);
        Assertions.assertEquals("advanced", cppLevelAtPreviousJob);
    }

    @Test
    public void testRecordFieldValuesAreUpdated() {
        TestPerson personErichGamma = TestPersonDataReader.readSpecificTestPersons().get(0);

        boolean updated = AvPath.INSTANCE.updateOne(personErichGamma, "/name", "ERICH GAMMA");
        Assertions.assertTrue(updated);
        Assertions.assertEquals("ERICH GAMMA", personErichGamma.getName());

        updated = AvPath.INSTANCE.updateOne(personErichGamma, "/age", 56);
        Assertions.assertTrue(updated);
        Assertions.assertEquals(Integer.valueOf(56), personErichGamma.getAge());

        updated = AvPath.INSTANCE.updateOne(
                personErichGamma,
                "/job/wrongpath", oldValue -> "nomatter");
        Assertions.assertFalse(updated);

        int updateCount = AvPath.INSTANCE.update(personErichGamma, "/hobby[*]/kind", "bungee jumping");
        Assertions.assertEquals(2, updateCount);
        Assertions.assertEquals("bungee jumping", personErichGamma.getHobby().get(0).getKind());
        Assertions.assertEquals("bungee jumping", personErichGamma.getHobby().get(1).getKind());

        updateCount = AvPath.INSTANCE.update(
                personErichGamma,
                "/hobby[*]/wrongpath", "nomatter");
        Assertions.assertEquals(0, updateCount);

        updated = AvPath.INSTANCE.updateOne(
                personErichGamma,
                "/job/previousJob/position/skill['c++']/level", oldValue -> "advanced++");
        Assertions.assertTrue(updated);
        Assertions.assertEquals(
                "advanced++",
                personErichGamma.getJob().getPreviousJob().getPosition().getSkill().get(new Utf8("c++")).getLevel());
    }

    @Test
    public void testArrayElementsAreUpdated() {
        TestPerson personErichGamma = TestPersonDataReader.readSpecificTestPersons().get(0);

        TestHobby hobby1 = new TestHobby(
                "bungee jumping",
                "is an activity that involves jumping from a tall structure while connected to a large " +
                "elastic cord");
        boolean updated = AvPath.INSTANCE.updateOne(personErichGamma, "/hobby[0]", oldValue -> hobby1);
        Assertions.assertTrue(updated);
        Assertions.assertEquals(hobby1, personErichGamma.getHobby().get(0));

        updated = AvPath.INSTANCE.updateOne(personErichGamma, "/wrongpath[0]", oldValue -> "nomatter");
        Assertions.assertFalse(updated);

        TestHobby hobby2 = new TestHobby(
                "climbing",
                "is the activity of using one's hands, feet, or any other part of the body to ascend a steep " +
                "object. It is done recreationally, competitively, in trades that rely on it, and in emergency " +
                "rescue and military operations. It is done indoors and out, on natural and man-made structures.");
        int updateCount = AvPath.INSTANCE.update(personErichGamma, "/hobby[*]", oldValue -> hobby2);
        Assertions.assertEquals(2, updateCount);
        Assertions.assertEquals(hobby2, personErichGamma.getHobby().get(0));
        Assertions.assertEquals(hobby2, personErichGamma.getHobby().get(1));

        updateCount = AvPath.INSTANCE.update(personErichGamma, "/wrongpath[*]", oldValue -> "nomatter");
        Assertions.assertEquals(0, updateCount);
    }

    @Test
    public void testMapValuesAreUpdated() {
        TestPerson personErichGamma = TestPersonDataReader.readSpecificTestPersons().get(0);

        TestSkillLevel skillLevel1 = new TestSkillLevel("advanced++", "advanced++");
        boolean updated = AvPath.INSTANCE.updateOne(
                personErichGamma,
                "/job/previousJob/position/skill['c++']",
                oldValue -> skillLevel1);
        Assertions.assertTrue(updated);
        Assertions.assertEquals(
                skillLevel1,
                personErichGamma.getJob().getPreviousJob().getPosition().getSkill().get(new Utf8("c++")));

        updated = AvPath.INSTANCE.updateOne(
                personErichGamma,
                "/job/previousJob/position/wrongpath['c++']",
                oldValue -> "nomatter");
        Assertions.assertFalse(updated);

        TestSkillLevel skillLevel2 = new TestSkillLevel("advanced++++", "advanced++++");
        int updateCount = AvPath.INSTANCE.update(
                personErichGamma,
                "/job/previousJob/position/skill[*]",
                oldValue -> skillLevel2);
        Assertions.assertEquals(1, updateCount);
        Assertions.assertEquals(
                skillLevel2,
                personErichGamma.getJob().getPreviousJob().getPosition().getSkill().get(new Utf8("c++")));

        updateCount = AvPath.INSTANCE.update(
                personErichGamma,
                "/job/previousJob/position/wrongpathl[*]",
                oldValue -> "nomatter");
        Assertions.assertEquals(0, updateCount);
    }

    @Test
    public void testPathsTemplatesAreResolved() {
        TestPerson personErichGamma = TestPersonDataReader.readSpecificTestPersons().get(0);

        List<PathTemplate> pathTemplates = AvPath.INSTANCE.getPathTemplates(personErichGamma.getSchema());

        Assertions.assertNotNull(pathTemplates);
        Assertions.assertEquals(15, pathTemplates.size());

        Assertions.assertEquals("/age", pathTemplates.get(0).getPath());
        Assertions.assertEquals(Type.INT, pathTemplates.get(0).getSchema().getType());

        Assertions.assertEquals("/hobby", pathTemplates.get(1).getPath());
        Assertions.assertEquals(Type.ARRAY, pathTemplates.get(1).getSchema().getType());

        Assertions.assertEquals("/hobby[*]", pathTemplates.get(2).getPath());
        Assertions.assertEquals(Type.RECORD, pathTemplates.get(2).getSchema().getType());

        Assertions.assertEquals("/hobby[*]/description", pathTemplates.get(3).getPath());
        Assertions.assertEquals(Type.STRING, pathTemplates.get(3).getSchema().getType());

        Assertions.assertEquals("/hobby[*]/kind", pathTemplates.get(4).getPath());
        Assertions.assertEquals(Type.STRING, pathTemplates.get(4).getSchema().getType());

        Assertions.assertEquals("/job", pathTemplates.get(5).getPath());
        Assertions.assertEquals(Type.RECORD, pathTemplates.get(5).getSchema().getType());

        Assertions.assertEquals("/job/company", pathTemplates.get(6).getPath());
        Assertions.assertEquals(Type.STRING, pathTemplates.get(6).getSchema().getType());

        Assertions.assertEquals("/job/position", pathTemplates.get(7).getPath());
        Assertions.assertEquals(Type.RECORD, pathTemplates.get(7).getSchema().getType());

        Assertions.assertEquals("/job/position/skill", pathTemplates.get(8).getPath());
        Assertions.assertEquals(Type.MAP, pathTemplates.get(8).getSchema().getType());

        Assertions.assertEquals("/job/position/skill[*]", pathTemplates.get(9).getPath());
        Assertions.assertEquals(Type.RECORD, pathTemplates.get(9).getSchema().getType());

        Assertions.assertEquals("/job/position/skill[*]/description", pathTemplates.get(10).getPath());
        Assertions.assertEquals(Type.STRING, pathTemplates.get(10).getSchema().getType());

        Assertions.assertEquals("/job/position/skill[*]/level", pathTemplates.get(11).getPath());
        Assertions.assertEquals(Type.STRING, pathTemplates.get(11).getSchema().getType());

        Assertions.assertEquals("/job/position/title", pathTemplates.get(12).getPath());
        Assertions.assertEquals(Type.STRING, pathTemplates.get(12).getSchema().getType());

        Assertions.assertEquals("/job/previousJob", pathTemplates.get(13).getPath());
        Assertions.assertEquals(Type.RECORD, pathTemplates.get(13).getSchema().getType());

        Assertions.assertEquals("/name", pathTemplates.get(14).getPath());
        Assertions.assertEquals(Type.STRING, pathTemplates.get(14).getSchema().getType());
    }

    @Test
    public void testPathsTemplatesAreResolvedForWeirdUnion() {
        Schema schema = new Schema.Parser().parse(
                "{\"type\":\"record\",\"name\":\"TestRecord\",\"fields\":[" +
                "    {\"name\":\"weirdUnion\",\"type\":[\"null\",\"int\",{\"type\": \"array\", \"items\": \"string\"}]}" +
                "]}");

        List<PathTemplate> pathTemplates = AvPath.INSTANCE.getPathTemplates(schema);
        Assertions.assertNotNull(pathTemplates);
        Assertions.assertEquals(3, pathTemplates.size());

        Assertions.assertEquals("/weirdUnion", pathTemplates.get(0).getPath());
        Assertions.assertEquals(Type.ARRAY, pathTemplates.get(0).getSchema().getType());

        Assertions.assertEquals("/weirdUnion", pathTemplates.get(1).getPath());
        Assertions.assertEquals(Type.INT, pathTemplates.get(1).getSchema().getType());

        Assertions.assertEquals("/weirdUnion[*]", pathTemplates.get(2).getPath());
        Assertions.assertEquals(Type.STRING, pathTemplates.get(2).getSchema().getType());
    }

}
