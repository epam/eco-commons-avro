package com.epam.eco.commons.avro.modification;

import com.epam.eco.commons.avro.utils.TestUtils;
import org.apache.avro.Schema;
import org.junit.Test;

import java.io.IOException;

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
