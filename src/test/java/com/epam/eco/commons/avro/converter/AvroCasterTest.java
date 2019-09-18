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
package com.epam.eco.commons.avro.converter;

import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.junit.Assert;
import org.junit.Test;

import com.epam.eco.commons.avro.AvroUtils;
import com.epam.eco.commons.avro.converter.AvroCaster.Feature;
import com.epam.eco.commons.avro.data.TestPersonDataReader;
import com.epam.eco.commons.avro.data.derived.TestPersonDerived;
import com.epam.eco.commons.avro.modification.ChangeSchemaFieldNamesCase;
import com.epam.eco.commons.avro.modification.ChangeSchemaFieldNamesCase.Case;
import com.epam.eco.commons.avro.modification.SchemaModifications;

/**
 * @author Andrei_Tytsik
 */
public class AvroCasterTest {

    @Test
    public void testGenericRecordIsCasted() throws Exception {
        for (GenericRecord origin : TestPersonDataReader.readGenericTestPersons()) {
            GenericRecord casted = AvroCaster.cast(
                    origin,
                    TestPersonDerived.SCHEMA$);
            assertRecordCasted(origin, casted, null);
        }
    }

    @Test
    public void testSpecificRecordIsCasted() throws Exception {
        for (GenericRecord origin : TestPersonDataReader.readSpecificTestPersons()) {
            GenericRecord casted = AvroCaster.cast(
                    origin,
                    TestPersonDerived.SCHEMA$);
            assertRecordCasted(origin, casted, null);
        }
    }

    @Test
    public void testGenericRecordIsCastedIgnoringFieldCases() throws Exception {
        Schema schemaWithUpperCasedFields = SchemaModifications.
                of(ChangeSchemaFieldNamesCase.with(Case.UPPER)).
                applyTo(TestPersonDerived.SCHEMA$);
        for (GenericRecord origin : TestPersonDataReader.readGenericTestPersons()) {
            GenericRecord casted = AvroCaster.cast(
                    origin,
                    schemaWithUpperCasedFields,
                    Feature.IGNORE_FIELD_NAMES_CASE);
            assertRecordCasted(origin, casted, true);
        }
    }

    @Test
    public void testSpecificRecordIsCastedIgnoringFieldCases() throws Exception {
        Schema schemaWithUpperCasedFields = SchemaModifications.
                of(ChangeSchemaFieldNamesCase.with(Case.UPPER)).
                applyTo(TestPersonDerived.SCHEMA$);
        for (GenericRecord origin : TestPersonDataReader.readSpecificTestPersons()) {
            GenericRecord casted = AvroCaster.cast(
                    origin,
                    schemaWithUpperCasedFields,
                    Feature.IGNORE_FIELD_NAMES_CASE);
            assertRecordCasted(origin, casted, true);
        }
    }

    private static String FIELD_AGE = "age";
    private static String FIELD_NAME = "name";
    private static String FIELD_HOBBY = "hobby";
    private static String FIELD_HOBBY_KIND = "kind";
    private static String FIELD_HOBBY_DESCRIPTION = "description";
    private static String FIELD_JOB = "job";
    private static String FIELD_JOB_COMPANY = "company";
    private static String FIELD_JOB_POSITION = "position";
    private static String FIELD_JOB_POSITION_TITLE = "title";
    private static String FIELD_JOB_POSITION_SKILL = "skill";
    private static String FIELD_JOB_POSITION_SKILL_LEVEL = "level";
    private static String FIELD_JOB_POSITION_DESCRIPTION = "description";

    @SuppressWarnings("unchecked")
    private void assertRecordCasted(GenericRecord origin, GenericRecord casted, Boolean upperLowerCase) {
        Assert.assertNotNull(casted);

        Map<String, Object> originMap = AvroUtils.encodeRecordToMap(origin);
        Map<String, Object> castedMap = AvroUtils.encodeRecordToMap(casted);

        String ageField = changeFieldCase(FIELD_AGE, upperLowerCase);
        String nameField = changeFieldCase(FIELD_NAME, upperLowerCase);
        Assert.assertFalse(castedMap.containsKey(nameField));
        Assert.assertTrue(castedMap.containsKey(ageField));
        Assert.assertEquals(originMap.get(FIELD_AGE), castedMap.get(ageField));

        String hobbyField = changeFieldCase(FIELD_HOBBY, upperLowerCase);
        String hobbyKindField = changeFieldCase(FIELD_HOBBY_KIND, upperLowerCase);
        String hobbyDescriptionField = changeFieldCase(FIELD_HOBBY_DESCRIPTION, upperLowerCase);
        Assert.assertTrue(castedMap.containsKey(hobbyField));

        Map<String, Object> originHobbyMap = (Map<String, Object>)originMap.get(FIELD_HOBBY);
        Map<String, Object> castedHobbyMap = (Map<String, Object>)castedMap.get(hobbyField);
        if (castedHobbyMap != null) {
            List<Map<String, Object>> originHobbyList = (List<Map<String, Object>>)originHobbyMap.get("array");
            List<Map<String, Object>> castedHobbyList = (List<Map<String, Object>>)castedHobbyMap.get("array");
            if (castedHobbyList != null) {
                for (int i = 0; i < castedHobbyList.size(); i++) {
                    Map<String, Object> originHobbyValueMap = originHobbyList.get(i);
                    Map<String, Object> castedHobbyValueMap = castedHobbyList.get(i);

                    Assert.assertFalse(castedHobbyValueMap.containsKey(hobbyDescriptionField));
                    Assert.assertTrue(castedHobbyValueMap.containsKey(hobbyKindField));
                    Assert.assertEquals(
                            originHobbyValueMap.get(FIELD_HOBBY_KIND),
                            castedHobbyValueMap.get(hobbyKindField));
                }
            }
        }

        String jobField = changeFieldCase(FIELD_JOB, upperLowerCase);
        String jobCompanyField = changeFieldCase(FIELD_JOB_COMPANY, upperLowerCase);
        Assert.assertTrue(castedMap.containsKey(jobField));

        Map<String, Object> originJobMap = (Map<String, Object>)originMap.get(FIELD_JOB);
        Map<String, Object> castedJobMap = (Map<String, Object>)castedMap.get(jobField);
        if (castedJobMap != null) {
            Assert.assertFalse(castedJobMap.containsKey(jobCompanyField));

            String jobPositionField = changeFieldCase(FIELD_JOB_POSITION, upperLowerCase);
            String jobPositionTitleField = changeFieldCase(FIELD_JOB_POSITION_TITLE, upperLowerCase);
            Assert.assertTrue(castedJobMap.containsKey(jobPositionField));

            Map<String, Object> originJobPositionMap = (Map<String, Object>)originJobMap.get(FIELD_JOB_POSITION);
            Map<String, Object> castedJobPositionMap = (Map<String, Object>)castedJobMap.get(jobPositionField);
            if (castedJobPositionMap != null) {
                Assert.assertFalse(castedJobPositionMap.containsKey(jobPositionTitleField));

                String jobPositionSkillField = changeFieldCase(FIELD_JOB_POSITION_SKILL, upperLowerCase);
                Assert.assertTrue(castedJobPositionMap.containsKey(jobPositionSkillField));
                Map<String, Map<String, Object>> originJobPositionSkillMap =
                        (Map<String, Map<String, Object>>)originJobPositionMap.get(FIELD_JOB_POSITION_SKILL);
                Map<String, Map<String, Object>> castedJobPositionSkillMap =
                        (Map<String, Map<String, Object>>)castedJobPositionMap.get(jobPositionSkillField);
                if (castedJobPositionSkillMap != null) {
                    String jobPositionSkillLevelField = changeFieldCase(FIELD_JOB_POSITION_SKILL_LEVEL, upperLowerCase);
                    String jobPositionSkillDescriptionField = changeFieldCase(FIELD_JOB_POSITION_DESCRIPTION, upperLowerCase);
                    for (String jobPositionSkillKey : castedJobPositionSkillMap.keySet()) {
                        Map<String, Object> originJobPositionSkillValueMap =
                                originJobPositionSkillMap.get(jobPositionSkillKey);
                        Map<String, Object> castedJobPositionSkillValueMap =
                                castedJobPositionSkillMap.get(jobPositionSkillKey);

                        Assert.assertFalse(
                                castedJobPositionSkillValueMap.containsKey(jobPositionSkillDescriptionField));
                        Assert.assertTrue(
                                castedJobPositionSkillValueMap.containsKey(jobPositionSkillLevelField));
                        Assert.assertEquals(
                                originJobPositionSkillValueMap.get(FIELD_JOB_POSITION_SKILL_LEVEL),
                                castedJobPositionSkillValueMap.get(jobPositionSkillLevelField));
                    }
                }
            }
        }
    }

    private static String changeFieldCase(String field, Boolean upperLowerCase) {
        if (upperLowerCase == null) {
            return field;
        } else if (upperLowerCase) {
            return field.toUpperCase();
        } else {
            return field.toLowerCase();
        }
    }

}
