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

import java.util.HashMap;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Type;
import org.apache.avro.SchemaBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.epam.eco.commons.avro.data.TestPerson;

/**
 * @author Andrei_Tytsik
 */
public class SchemaModificationsTest {

    @Test
    public void testSchemaIsModified() {
        Schema schemaModified = SchemaModifications.of(initAvroModifications()).applyTo(TestPerson.SCHEMA$);
        assertSchemaModifiedAsExpected(schemaModified);
    }

    private SchemaModification[] initAvroModifications() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("prop_add1", "prop_add1_value");
        properties.put("prop_add2", "prop_add2_value");
        properties.put("prop_add3", "prop_add3_value");
        return new SchemaModification[]{
                new RenameSchema("Name_new", "Namespace_new"),

                new RemoveSchemaFieldByPath("name"),
                new RemoveSchemaFieldByPath("hobby.description"),
                new RemoveSchemaFieldByPath("job.company"),
                new RemoveSchemaFieldByPath("job.position.title"),
                new RemoveSchemaFieldByPath("job.position.skill.description"),

                new AddSchemaFieldToPath(new Schema.Field("birthdate", Schema.create(Type.LONG), null, (Object)null), null),
                new AddSchemaFieldToPath(new Schema.Field("responsibilities", SchemaBuilder.array().items(Schema.create(Type.STRING)), null, (Object)null), "job.position"),

                new SetSchemaProperties(properties),
                new SetSchemaProperties("prop_add4", "prop_add4_value"),
                SetSchemaProperties.builder().
                    property("prop_add5", "prop_add5_value").
                    property("prop_add6", "prop_add6_value").
                    build(),

                    RemoveSchemaProperties.with("prop1", "prop2", "prop3")};
    }

    private void assertSchemaModifiedAsExpected(Schema schema) {
        Assertions.assertNotNull(schema);

        Assertions.assertEquals("Name_new", schema.getName());
        Assertions.assertEquals("Namespace_new", schema.getNamespace());

        Assertions.assertNull(schema.getField("name"));
        Assertions.assertNull(schema.getField("hobby").schema().getTypes().get(1).getElementType().getField("description"));
        Assertions.assertNull(schema.getField("job").schema().getField("company"));
        Assertions.assertNull(schema.getField("job").schema().getField("position").schema().getField("title"));
        Assertions.assertNull(schema.getField("job").schema().getField("position").schema().getField("skill").schema().getValueType().getField("description"));

        Assertions.assertNotNull(schema.getField("birthdate"));
        Assertions.assertEquals(schema.getField("birthdate").schema().getType(), Type.LONG);
        Assertions.assertNotNull(schema.getField("job").schema().getField("position").schema().getField("responsibilities"));
        Assertions.assertEquals(schema.getField("job").schema().getField("position").schema().getField("responsibilities").schema().getType(), Type.ARRAY);
        Assertions.assertEquals(schema.getField("job").schema().getField("position").schema().getField("responsibilities").schema().getElementType().getType(), Type.STRING);

        Assertions.assertEquals("prop_add1_value", schema.getObjectProps().get("prop_add1"));
        Assertions.assertEquals("prop_add2_value", schema.getObjectProps().get("prop_add2"));
        Assertions.assertEquals("prop_add3_value", schema.getObjectProps().get("prop_add3"));
        Assertions.assertEquals("prop_add4_value", schema.getObjectProps().get("prop_add4"));
        Assertions.assertEquals("prop_add5_value", schema.getObjectProps().get("prop_add5"));
        Assertions.assertEquals("prop_add6_value", schema.getObjectProps().get("prop_add6"));

        Assertions.assertNull(schema.getObjectProps().get("prop1"));
        Assertions.assertNull(schema.getObjectProps().get("prop2"));
        Assertions.assertNull(schema.getObjectProps().get("prop3"));
    }

}
