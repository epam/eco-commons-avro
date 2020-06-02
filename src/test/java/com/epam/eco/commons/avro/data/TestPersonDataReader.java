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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.commons.io.IOUtils;

import com.epam.eco.commons.avro.AvroUtilsTest;

/**
 * @author Andrei_Tytsik
 */
public class TestPersonDataReader {

    private static final String DATA_FILE_PATH = "/test_persons_data";
    private static final File DATA_FILE;
    static {
        try {
            DATA_FILE = new File(AvroUtilsTest.class.getResource(DATA_FILE_PATH).toURI());
        } catch (URISyntaxException use) {
            throw new RuntimeException(use);
        }
    }

    public static List<byte[]> readBinaryTestPersons() {
        DataFileReader<Object> fileReader = null;
        try {
            List<byte[]> persons = new ArrayList<>();
            DatumReader<Object> datumReader = new GenericDatumReader<>();
            fileReader = new DataFileReader<>(DATA_FILE, datumReader);
            Object person = null;
            while (fileReader.hasNext()) {
                person = fileReader.next(person);
                ByteArrayOutputStream out = null;
                try {
                    out = new ByteArrayOutputStream();
                    GenericDatumWriter<Object> writer = new GenericDatumWriter<>(TestPerson.SCHEMA$);
                    Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
                    writer.write(person, encoder);
                    encoder.flush();
                    persons.add(out.toByteArray());
                } finally {
                    IOUtils.closeQuietly(out);
                }
            }
            return persons;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } finally {
            IOUtils.closeQuietly(fileReader);
        }
    }

    public static List<String> readJsonTestPersons() {
        DataFileReader<Object> fileReader = null;
        try {
            List<String> persons = new ArrayList<>();
            DatumReader<Object> datumReader = new GenericDatumReader<>();
            fileReader = new DataFileReader<>(DATA_FILE, datumReader);
            Object person = null;
            while (fileReader.hasNext()) {
                person = fileReader.next(person);
                ByteArrayOutputStream out = null;
                try {
                    out = new ByteArrayOutputStream();
                    GenericDatumWriter<Object> writer = new GenericDatumWriter<>(TestPerson.SCHEMA$);
                    Encoder encoder = EncoderFactory.get().jsonEncoder(TestPerson.SCHEMA$, out);
                    writer.write(person, encoder);
                    encoder.flush();
                    persons.add(new String(out.toByteArray(), StandardCharsets.UTF_8));
                } finally {
                    IOUtils.closeQuietly(out);
                }
            }
            return persons;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } finally {
            IOUtils.closeQuietly(fileReader);
        }
    }

    public static List<GenericRecord> readGenericTestPersons() {
        DataFileReader<GenericRecord> fileReader = null;
        try {
            List<GenericRecord> persons = new ArrayList<>();
            DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(TestPerson.SCHEMA$);
            fileReader = new DataFileReader<>(DATA_FILE, datumReader);
            GenericRecord person = null;
            while (fileReader.hasNext()) {
                persons.add(fileReader.next(person));
            }
            return persons;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } finally {
            IOUtils.closeQuietly(fileReader);
        }
    }

    public static List<TestPerson> readSpecificTestPersons() {
        DataFileReader<TestPerson> fileReader = null;
        try {
            List<TestPerson> persons = new ArrayList<>();
            DatumReader<TestPerson> datumReader = new SpecificDatumReader<>(TestPerson.SCHEMA$);
            fileReader = new DataFileReader<>(DATA_FILE, datumReader);
            TestPerson person = null;
            while (fileReader.hasNext()) {
                persons.add(fileReader.next(person));
            }
            return persons;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } finally {
            IOUtils.closeQuietly(fileReader);
        }
    }

}
