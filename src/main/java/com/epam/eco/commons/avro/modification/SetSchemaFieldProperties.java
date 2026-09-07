package com.epam.eco.commons.avro.modification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.epam.eco.commons.avro.traversal.GenericSchemaTraverser;

import static java.util.Map.copyOf;

public record SetSchemaFieldProperties(
        String path,
        Map<String, Object> properties,
        boolean strict
) implements SchemaModification {

    public SetSchemaFieldProperties {
        Validate.notNull(path, "path cannot be null");
        Validate.notNull(properties, "properties cannot be null");

        properties = copyOf(properties);
    }

    @Override
    public void applyToGeneric(Map<String, Object> schemaMap) {
        List<Map<String, Object>> fieldsToUpdate = new ArrayList<>();

        new GenericSchemaTraverser((path, parentSchema, field) -> {
            if (SetSchemaFieldProperties.this.path().equals(path)) {
                fieldsToUpdate.add(field);
            }
        }).walk(schemaMap, null);

        if (strict && fieldsToUpdate.isEmpty()) {
            throw new RuntimeException(String.format("Field not found at '%s'", path));
        }

        fieldsToUpdate.forEach(field -> field.putAll(properties));
    }
}
