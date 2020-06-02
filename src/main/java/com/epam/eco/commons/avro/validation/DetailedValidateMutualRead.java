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

import java.io.IOException;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.SchemaValidationException;
import org.apache.avro.SchemaValidationStrategy;
import org.apache.avro.io.parsing.ResolvingGrammarGenerator;
import org.apache.commons.collections4.CollectionUtils;

import com.epam.eco.commons.avro.io.parsing.GrammarError;
import com.epam.eco.commons.avro.io.parsing.GrammarErrorParser;

/**
 * @author Andrei_Tytsik
 */
public class DetailedValidateMutualRead implements SchemaValidationStrategy {

    @Override
    public void validate(Schema toValidate, Schema existing) throws SchemaValidationException {
        canRead(toValidate, existing);
        canRead(existing, toValidate);
    }

    static void canRead(Schema writtenWith, Schema readUsing) throws SchemaValidationException {
        List<GrammarError> errors = null;
        try {
            errors = GrammarErrorParser.parse(
                    new ResolvingGrammarGenerator().generate(writtenWith, readUsing));
        } catch (IOException ioe) {
            throw new DetailedSchemaValidationException(readUsing, writtenWith, ioe, errors);
        }
        if (!CollectionUtils.isEmpty(errors)) {
            throw new DetailedSchemaValidationException(readUsing, writtenWith, errors);
        }
    }

}
