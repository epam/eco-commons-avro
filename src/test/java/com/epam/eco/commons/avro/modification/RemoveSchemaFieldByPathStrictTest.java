/*
 * Copyright 2022 EPAM Systems
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
package com.epam.eco.commons.avro.modification;

import com.epam.eco.commons.avro.utils.TestUtils;
import org.apache.avro.Schema;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Maksim_Gavrilov
 */
public class RemoveSchemaFieldByPathStrictTest {

    private Schema getScheme() throws IOException {
        return TestUtils.getScheme("simple_schema.avsc");
    }

    @Test
    public void strictEnabledFieldExists() throws IOException {

        Schema schemaModified = SchemaModifications.of(
                        new RemoveSchemaFieldByPath("string_field1")
                )
                .applyTo(getScheme());
        assert schemaModified.getFields().size() == 0;
    }

    @Test(expected = RuntimeException.class)
    public void strictEnabledFieldNotExists() throws IOException {

        SchemaModifications.of(
                        new RemoveSchemaFieldByPath("string_field2")
                )
                .applyTo(getScheme());
    }

    @Test
    public void strictDisabledFieldExists() throws IOException {

        Schema schemaModified = SchemaModifications.of(
                        new RemoveSchemaFieldByPath("string_field1", false)
                )
                .applyTo(getScheme());
        assert schemaModified.getFields().size() == 0;
    }

    @Test
    public void strictDisabledFieldNotExists() throws IOException {

        Schema schemaModified = SchemaModifications.of(
                        new RemoveSchemaFieldByPath("string_field2", false)
                )
                .applyTo(getScheme());
        assert schemaModified.getFields().size() == 1;
    }
}
