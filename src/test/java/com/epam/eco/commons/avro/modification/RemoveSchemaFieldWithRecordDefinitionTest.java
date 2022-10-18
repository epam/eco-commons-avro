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
package com.epam.eco.commons.avro.modification;

import com.epam.eco.commons.avro.utils.TestUtils;
import org.apache.avro.Schema;
import org.apache.avro.SchemaParseException;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Maksim_Gavrilov
 */
public class RemoveSchemaFieldWithRecordDefinitionTest {

    private Schema getScheme() throws IOException {
        return TestUtils.getScheme("nested_record_with_empty_namespace.avsc");
    }


    @Test
    public void successRemove() throws Exception {
        Schema schemaModified = SchemaModifications.of(
                        new RemoveSchemaFieldByPath("another_two_string"),
                        new RemoveSchemaFieldByPath("two_string")
                )
                .applyTo(getScheme());

        assert schemaModified.getFields().size() == 1;
        assert schemaModified.getFields().get(0).name().equals("int_field");

        assert schemaModified.getFields().get(0).schema().getTypes().size() == 2;
        assert schemaModified.getFields().get(0).schema().getTypes().get(0).getType() == Schema.Type.INT;
        assert schemaModified.getFields().get(0).schema().getTypes().get(1).getType() == Schema.Type.NULL;
        System.out.println(schemaModified);
    }

    @Test(expected = NullPointerException.class)
    public void wrongOrderInRemove() throws Exception {
        SchemaModifications.of(
                        new RemoveSchemaFieldByPath("two_string"),
                        new RemoveSchemaFieldByPath("another_two_string")
                )
                .applyTo(getScheme());
    }

    @Test(expected = SchemaParseException.class)
    public void removeOnlyFieldWithDefinition() throws Exception {
        SchemaModifications.of(
                        new RemoveSchemaFieldByPath("two_string")
                )
                .applyTo(getScheme());
    }


}
