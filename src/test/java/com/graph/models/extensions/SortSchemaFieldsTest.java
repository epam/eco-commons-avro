package com.graph.models.extensions;

import com.epam.eco.commons.avro.modification.CachedSchemaModifications;
import com.epam.eco.commons.avro.modification.SortSchemaFields;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;
import org.junit.Test;

public class SortSchemaFieldsTest {

    @Test
    public void test() {
        SortSchemaFields sortSchemaFields = new SortSchemaFields();
        Schema schema = ReflectData.AllowNull.get().getSchema(Event.class);
        CachedSchemaModifications.of(sortSchemaFields).applyTo(schema);
    }
}
