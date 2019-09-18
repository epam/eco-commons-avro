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
package com.epam.eco.commons.avro.modification;

import java.util.HashMap;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Type;
import org.apache.avro.SchemaBuilder;
import org.junit.Assert;
import org.junit.Test;

import com.epam.eco.commons.avro.data.TestPerson;

/**
 * @author Andrei_Tytsik
 */
public class SchemaModificationsTest {

    @Test
    public void testSchemaIsModified() throws Exception {
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
        Assert.assertNotNull(schema);

        Assert.assertEquals("Name_new", schema.getName());
        Assert.assertEquals("Namespace_new", schema.getNamespace());

        Assert.assertNull(schema.getField("name"));
        Assert.assertNull(schema.getField("hobby").schema().getTypes().get(1).getElementType().getField("description"));
        Assert.assertNull(schema.getField("job").schema().getField("company"));
        Assert.assertNull(schema.getField("job").schema().getField("position").schema().getField("title"));
        Assert.assertNull(schema.getField("job").schema().getField("position").schema().getField("skill").schema().getValueType().getField("description"));

        Assert.assertNotNull(schema.getField("birthdate"));
        Assert.assertEquals(schema.getField("birthdate").schema().getType(), Type.LONG);
        Assert.assertNotNull(schema.getField("job").schema().getField("position").schema().getField("responsibilities"));
        Assert.assertEquals(schema.getField("job").schema().getField("position").schema().getField("responsibilities").schema().getType(), Type.ARRAY);
        Assert.assertEquals(schema.getField("job").schema().getField("position").schema().getField("responsibilities").schema().getElementType().getType(), Type.STRING);

        Assert.assertEquals("prop_add1_value", schema.getObjectProps().get("prop_add1"));
        Assert.assertEquals("prop_add2_value", schema.getObjectProps().get("prop_add2"));
        Assert.assertEquals("prop_add3_value", schema.getObjectProps().get("prop_add3"));
        Assert.assertEquals("prop_add4_value", schema.getObjectProps().get("prop_add4"));
        Assert.assertEquals("prop_add5_value", schema.getObjectProps().get("prop_add5"));
        Assert.assertEquals("prop_add6_value", schema.getObjectProps().get("prop_add6"));

        Assert.assertNull(schema.getObjectProps().get("prop1"));
        Assert.assertNull(schema.getObjectProps().get("prop2"));
        Assert.assertNull(schema.getObjectProps().get("prop3"));
    }

}
