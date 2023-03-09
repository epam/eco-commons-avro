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
package com.epam.eco.commons.avro.converter;

import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    public void testGenericRecordIsCasted() {
        for (GenericRecord origin : TestPersonDataReader.readGenericTestPersons()) {
            GenericRecord casted = AvroCaster.cast(
                    origin,
                    TestPersonDerived.SCHEMA$);
            assertRecordCasted(origin, casted, null);
        }
    }

    @Test
    public void testSpecificRecordIsCasted() {
        for (GenericRecord origin : TestPersonDataReader.readSpecificTestPersons()) {
            GenericRecord casted = AvroCaster.cast(
                    origin,
                    TestPersonDerived.SCHEMA$);
            assertRecordCasted(origin, casted, null);
        }
    }

    @Test
    public void testGenericRecordIsCastedIgnoringFieldCases() {
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
    public void testSpecificRecordIsCastedIgnoringFieldCases() {
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

    private static final String FIELD_AGE = "age";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_HOBBY = "hobby";
    private static final String FIELD_HOBBY_KIND = "kind";
    private static final String FIELD_HOBBY_DESCRIPTION = "description";
    private static final String FIELD_JOB = "job";
    private static final String FIELD_JOB_COMPANY = "company";
    private static final String FIELD_JOB_POSITION = "position";
    private static final String FIELD_JOB_POSITION_TITLE = "title";
    private static final String FIELD_JOB_POSITION_SKILL = "skill";
    private static final String FIELD_JOB_POSITION_SKILL_LEVEL = "level";
    private static final String FIELD_JOB_POSITION_DESCRIPTION = "description";

    @SuppressWarnings("unchecked")
    private void assertRecordCasted(GenericRecord origin, GenericRecord casted, Boolean upperLowerCase) {
        Assertions.assertNotNull(casted);

        Map<String, Object> originMap = AvroUtils.encodeRecordToMap(origin);
        Map<String, Object> castedMap = AvroUtils.encodeRecordToMap(casted);

        String ageField = changeFieldCase(FIELD_AGE, upperLowerCase);
        String nameField = changeFieldCase(FIELD_NAME, upperLowerCase);
        Assertions.assertFalse(castedMap.containsKey(nameField));
        Assertions.assertTrue(castedMap.containsKey(ageField));
        Assertions.assertEquals(originMap.get(FIELD_AGE), castedMap.get(ageField));

        String hobbyField = changeFieldCase(FIELD_HOBBY, upperLowerCase);
        String hobbyKindField = changeFieldCase(FIELD_HOBBY_KIND, upperLowerCase);
        String hobbyDescriptionField = changeFieldCase(FIELD_HOBBY_DESCRIPTION, upperLowerCase);
        Assertions.assertTrue(castedMap.containsKey(hobbyField));

        Map<String, Object> originHobbyMap = (Map<String, Object>)originMap.get(FIELD_HOBBY);
        Map<String, Object> castedHobbyMap = (Map<String, Object>)castedMap.get(hobbyField);
        if (castedHobbyMap != null) {
            List<Map<String, Object>> originHobbyList = (List<Map<String, Object>>)originHobbyMap.get("array");
            List<Map<String, Object>> castedHobbyList = (List<Map<String, Object>>)castedHobbyMap.get("array");
            if (castedHobbyList != null) {
                for (int i = 0; i < castedHobbyList.size(); i++) {
                    Map<String, Object> originHobbyValueMap = originHobbyList.get(i);
                    Map<String, Object> castedHobbyValueMap = castedHobbyList.get(i);

                    Assertions.assertFalse(castedHobbyValueMap.containsKey(hobbyDescriptionField));
                    Assertions.assertTrue(castedHobbyValueMap.containsKey(hobbyKindField));
                    Assertions.assertEquals(
                            originHobbyValueMap.get(FIELD_HOBBY_KIND),
                            castedHobbyValueMap.get(hobbyKindField));
                }
            }
        }

        String jobField = changeFieldCase(FIELD_JOB, upperLowerCase);
        String jobCompanyField = changeFieldCase(FIELD_JOB_COMPANY, upperLowerCase);
        Assertions.assertTrue(castedMap.containsKey(jobField));

        Map<String, Object> originJobMap = (Map<String, Object>)originMap.get(FIELD_JOB);
        Map<String, Object> castedJobMap = (Map<String, Object>)castedMap.get(jobField);
        if (castedJobMap != null) {
            Assertions.assertFalse(castedJobMap.containsKey(jobCompanyField));

            String jobPositionField = changeFieldCase(FIELD_JOB_POSITION, upperLowerCase);
            String jobPositionTitleField = changeFieldCase(FIELD_JOB_POSITION_TITLE, upperLowerCase);
            Assertions.assertTrue(castedJobMap.containsKey(jobPositionField));

            Map<String, Object> originJobPositionMap = (Map<String, Object>)originJobMap.get(FIELD_JOB_POSITION);
            Map<String, Object> castedJobPositionMap = (Map<String, Object>)castedJobMap.get(jobPositionField);
            if (castedJobPositionMap != null) {
                Assertions.assertFalse(castedJobPositionMap.containsKey(jobPositionTitleField));

                String jobPositionSkillField = changeFieldCase(FIELD_JOB_POSITION_SKILL, upperLowerCase);
                Assertions.assertTrue(castedJobPositionMap.containsKey(jobPositionSkillField));
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

                        Assertions.assertFalse(
                                castedJobPositionSkillValueMap.containsKey(jobPositionSkillDescriptionField));
                        Assertions.assertTrue(
                                castedJobPositionSkillValueMap.containsKey(jobPositionSkillLevelField));
                        Assertions.assertEquals(
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
