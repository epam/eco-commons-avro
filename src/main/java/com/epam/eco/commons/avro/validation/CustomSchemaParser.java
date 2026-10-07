package com.epam.eco.commons.avro.validation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.avro.NameValidator;
import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.apache.avro.SchemaParseException;

import com.epam.eco.commons.avro.traversal.SchemaTraverseListener;
import com.epam.eco.commons.avro.traversal.SchemaTraverser;

/**
 * Restores Avro 1.11.x name checking: type names, field names and enum symbols
 * are validated, namespaces are not.
 * <p>
 * Avro 1.12 {@link NameValidator} cannot do this by itself — {@code Schema.Parser}
 * runs the same validator on every full-name segment, including namespace parts.
 * <p>
 * todo remove this class once all schemas have been updated to use the correct namespaces and use default parser instead.
 */
public final class CustomSchemaParser {

    private CustomSchemaParser() {
    }

    public static Schema parse(String schemaJson, boolean validateDefaults) {
        return finish(newParser(validateDefaults).parse(schemaJson));
    }

    public static Schema parse(File file, boolean validateDefaults) throws IOException {
        return finish(newParser(validateDefaults).parse(file));
    }

    public static Schema parse(InputStream inputStream, boolean validateDefaults) throws IOException {
        return finish(newParser(validateDefaults).parse(inputStream));
    }

    private static Schema finish(Schema schema) {
        validateNames(schema);
        return schema;
    }

    private static Schema.Parser newParser(boolean validateDefaults) {
        return new Schema.Parser(NameValidator.NO_VALIDATION)
                .setValidateDefaults(validateDefaults);
    }

    /**
     * Validates record/enum/fixed simple names, field names and enum symbols
     * with {@link NameValidator#UTF_VALIDATOR}. Does not inspect namespaces.
     */
    private static void validateNames(Schema schema) {
        new SchemaTraverser(new SchemaTraverseListener() {
            @Override
            public void onSchema(String path, Schema parentSchema, Schema current) {
                Type type = current.getType();
                if (type == Type.RECORD || type == Type.ENUM || type == Type.FIXED) {
                    requireValidName(current.getName());
                }
                if (type == Type.ENUM) {
                    current.getEnumSymbols().forEach(CustomSchemaParser::requireValidName);
                }
            }

            @Override
            public void onSchemaField(String path, Schema parentSchema, Field field) {
                requireValidName(field.name());
            }
        }).walk(schema);
    }

    private static void requireValidName(String name) {
        if (name == null) {
            return;
        }
        NameValidator.Result result = NameValidator.UTF_VALIDATOR.validate(name);
        if (!result.isOK()) {
            throw new SchemaParseException(result.getErrors());
        }
    }

}
