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
package com.epam.eco.commons.avro.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.SchemaValidationException;

import com.epam.eco.commons.avro.io.parsing.GrammarError;

/**
 * @author Andrei_Tytsik
 */
public class DetailedSchemaValidationException extends SchemaValidationException {

    private static final long serialVersionUID = 1L;

    private final Schema reader;
    private final Schema writer;
    private final List<GrammarError> errors;

    public DetailedSchemaValidationException(
            Schema reader,
            Schema writer,
            Throwable cause,
            List<GrammarError> errors) {
        super(reader, writer, cause);

        this.reader = reader;
        this.writer = writer;
        this.errors =
                errors != null ?
                Collections.unmodifiableList(new ArrayList<>(errors)) :
                Collections.emptyList();
    }

    public DetailedSchemaValidationException(
            Schema reader,
            Schema writer,
            List<GrammarError> errors) {
        super(reader, writer);

        this.reader = reader;
        this.writer = writer;
        this.errors =
                errors != null ?
                Collections.unmodifiableList(new ArrayList<>(errors)) :
                Collections.emptyList();
    }

    public Schema getReader() {
        return reader;
    }
    public Schema getWriter() {
        return writer;
    }
    public List<GrammarError> getErrors() {
        return errors;
    }

}
