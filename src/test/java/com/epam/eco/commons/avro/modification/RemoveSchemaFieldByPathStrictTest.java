package com.epam.eco.commons.avro.modification;

import org.apache.avro.Schema;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class RemoveSchemaFieldByPathStrictTest {

    private Schema getScheme() throws IOException {
        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("simple_schema.avsc");
        return new Schema.Parser().parse(inputStream);
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
