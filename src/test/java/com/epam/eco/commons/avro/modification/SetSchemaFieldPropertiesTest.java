package com.epam.eco.commons.avro.modification;

import java.util.HashMap;
import java.util.Map;

import org.apache.avro.Schema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.epam.eco.commons.avro.data.TestPerson;

public class SetSchemaFieldPropertiesTest {

    @Test
    public void testFieldPropertiesAreSetOnFieldOnly() {
        Map<String, Object> props = new HashMap<>();
        props.put("compareAsSet", true);
        props.put("_datahub.field_meta.comparison_type", "set");

        Schema schemaModified = SchemaModifications.of(
                new SetSchemaFieldProperties("hobby", props, true)
        ).applyTo(TestPerson.SCHEMA$);

        Schema.Field hobbyField = schemaModified.getField("hobby");
        Assertions.assertEquals(Boolean.TRUE, hobbyField.getObjectProp("compareAsSet"));
        Assertions.assertEquals("set", hobbyField.getObjectProp("_datahub.field_meta.comparison_type"));

        // must not leak onto other fields or onto the field's type schema
        Assertions.assertNull(schemaModified.getField("name").getObjectProp("compareAsSet"));
    }

    @Test
    public void testNestedFieldPropertiesAreSetByPath() {
        Map<String, Object> props = new HashMap<>();
        props.put("compareAsSet", true);

        Schema schemaModified = SchemaModifications.of(
                new SetSchemaFieldProperties("job.position.title", props, true)
        ).applyTo(TestPerson.SCHEMA$);

        Schema.Field titleField = schemaModified.getField("job").schema()
                .getField("position").schema()
                .getField("title");
        Assertions.assertEquals(Boolean.TRUE, titleField.getObjectProp("compareAsSet"));
    }

    @Test
    public void testThrowsWhenFieldNotFoundAndStrict() {
        Map<String, Object> props = new HashMap<>();
        props.put("compareAsSet", true);

        Assertions.assertThrows(
                RuntimeException.class,
                () -> SchemaModifications.of(
                        new SetSchemaFieldProperties("nonExistentField", props, true)
                ).applyTo(TestPerson.SCHEMA$));
    }

    @Test
    public void testDoesNotThrowWhenFieldNotFoundAndNotStrict() {
        Map<String, Object> props = new HashMap<>();
        props.put("compareAsSet", true);

        Schema schemaModified = SchemaModifications.of(
                new SetSchemaFieldProperties("nonExistentField", props, false)
        ).applyTo(TestPerson.SCHEMA$);

        Assertions.assertNotNull(schemaModified);
    }

}
