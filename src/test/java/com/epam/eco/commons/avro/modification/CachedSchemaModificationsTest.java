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
public class CachedSchemaModificationsTest {

    @Test
    public void testModifiedSchemaIsCached() throws Exception {
        SchemaModification[] modifications = initAvroModifications();

        Schema schema1 = CachedSchemaModifications.of(modifications).applyTo(TestPerson.SCHEMA$);

        Assert.assertNotNull(schema1);
        Assert.assertNotEquals(TestPerson.SCHEMA$, schema1);

        Schema schema2 = CachedSchemaModifications.of(modifications).applyTo(TestPerson.SCHEMA$);

        Assert.assertNotNull(schema2);
        Assert.assertNotEquals(TestPerson.SCHEMA$, schema2);

        Assert.assertTrue(schema1 == schema2);
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

}
