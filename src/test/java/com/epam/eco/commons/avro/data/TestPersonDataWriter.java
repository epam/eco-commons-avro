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
package com.epam.eco.commons.avro.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

/**
 * @author tytsik
 */
public class TestPersonDataWriter {

    public static void main(String[] args) throws IOException {
        File file = new File("/tmp/test_persons_data");
        DatumWriter<Object> datumWriter = new SpecificDatumWriter<>(TestPerson.SCHEMA$);
        DataFileWriter<Object> fileWriter = new DataFileWriter<>(datumWriter);
        fileWriter.create(TestPerson.SCHEMA$, file);

        fileWriter.append(person1());
        fileWriter.append(person2());
        fileWriter.append(person3());
        fileWriter.append(person4());

        fileWriter.close();
    }

    private static TestPerson person1() {
        TestPerson person = new TestPerson();

        // Erich Gamma
        // 55
        // 1 Microsoft - Engineer
        // 2 IBM - Engineer
        person.setName("Erich Gamma");
        person.setAge(55);
        person.setHobby(new ArrayList<>());
        person.getHobby().add(new TestHobby("literature", "literature"));
        person.getHobby().add(new TestHobby("science", "science"));

        TestJob jobIbm = new TestJob();
        jobIbm.setCompany("IBM");
        jobIbm.setPosition(new TestPosition("Engineer", new HashMap<>()));

        TestJob jobMs = new TestJob();
        jobMs.setCompany("Microsoft");
        jobMs.setPosition(new TestPosition("Engineer", new HashMap<>()));
        jobMs.getPosition().getSkill().put("c++", new TestSkillLevel("advanced", "advanced"));

        jobIbm.setPreviousJob(jobMs);

        person.setJob(jobIbm);

        return person;
    }

    private static TestPerson person2() {
        TestPerson person = new TestPerson();

        // Richard Helm
        person.setName("Richard Helm");
        person.setHobby(new ArrayList<>());
        person.getHobby().add(new TestHobby("computer organization", "computer organization"));

        TestJob job = new TestJob();
        job.setCompany("Unknown");
        job.setPosition(new TestPosition("Unknown", new HashMap<>()));
        job.getPosition().getSkill().put("c", new TestSkillLevel("advanced", "advanced"));

        person.setJob(job);

        return person;
    }

    private static TestPerson person3() {
        TestPerson person = new TestPerson();

        // Ralph Johnson
        // 61
        // University of Illinois - Research associate professor
        person.setName("Ralph Johnson");
        person.setAge(61);
        person.setHobby(new ArrayList<>());
        person.getHobby().add(new TestHobby("cinema", "cinema"));

        TestJob job = new TestJob();
        job.setCompany("University of Illinois");
        job.setPosition(new TestPosition("Research associate professor", new HashMap<>()));
        job.getPosition().getSkill().put("java", new TestSkillLevel("advanced", "advanced"));
        job.getPosition().getSkill().put("smaltalk", new TestSkillLevel("advanced", "advanced"));

        person.setJob(job);

        return person;
    }

    private static TestPerson person4() {
        TestPerson person = new TestPerson();

        // John Vlissides
        // 44
        // 1 Stanford University - engineer
        // 2 IBM - research staff member
        person.setName("John Vlissides");
        person.setAge(44);

        TestJob jobIbm = new TestJob();
        jobIbm.setCompany("IBM");
        jobIbm.setPosition(new TestPosition("Research staff member", new HashMap<>()));

        TestJob jobSu = new TestJob();
        jobSu.setCompany("Stanford University");
        jobSu.setPosition(new TestPosition("Engineer", new HashMap<>()));
        jobSu.getPosition().getSkill().put("smaltalk", new TestSkillLevel("advanced", "advanced"));

        jobIbm.setPreviousJob(jobSu);

        person.setJob(jobIbm);

        return person;
    }

}
