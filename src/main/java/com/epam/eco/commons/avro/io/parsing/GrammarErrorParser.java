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
package com.epam.eco.commons.avro.io.parsing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.io.parsing.Symbol;
import org.apache.avro.io.parsing.Symbol.Alternative;
import org.apache.avro.io.parsing.Symbol.ErrorAction;
import org.apache.avro.io.parsing.Symbol.FieldOrderAction;
import org.apache.avro.io.parsing.Symbol.Kind;
import org.apache.commons.lang3.Validate;

import com.epam.eco.commons.avro.Path;

/**
 * @author Andrei_Tytsik
 */
public abstract class GrammarErrorParser {

    private static final String ARRAY_START_TERMINAL = "array-start";

    private GrammarErrorParser() {
    }

    public static List<GrammarError> parse(Symbol grammar) {
        Validate.notNull(grammar, "Grammar is null");

        if (grammar.production == null) {
            return Collections.emptyList();
        }

        List<GrammarError> errors = new ArrayList<>();
        parse(new LinkedList<>(Arrays.asList(grammar.production)), new Path(), errors);
        return errors;
    }

    private static void parse(LinkedList<Symbol> productionStack, Path path, List<GrammarError> errors) {
        Symbol symbol = productionStack.pollLast();
        if (symbol == null) {
            return;
        }

        if (symbol.kind == Kind.IMPLICIT_ACTION) {
            if (symbol instanceof FieldOrderAction) {
                for (Schema.Field field : ((FieldOrderAction)symbol).fields) {
                    path.append(field.name());
                    parse(productionStack, path, errors);
                    path.subtract();
                }
            } else if (symbol instanceof ErrorAction) {
                errors.add(new GrammarError(path.getPathString(), ((ErrorAction)symbol).msg));
            } else {
                parse(productionStack, path, errors);
            }
        } else if (symbol.kind == Kind.TERMINAL) {
            if (ARRAY_START_TERMINAL.equals(symbol.toString())) {
                parse(productionStack, path, errors);
            }
        } else if (
                symbol.kind == Kind.REPEATER ||
                symbol.kind == Kind.SEQUENCE) {
            parse(new LinkedList<>(Arrays.asList(symbol.production)), path, errors);
        } else if (symbol.kind == Kind.ALTERNATIVE) {
            parse(new LinkedList<>(Arrays.asList(((Alternative)symbol).symbols)), path, errors);
        } else if (symbol.kind == Kind.EXPLICIT_ACTION) {
            // nothing to do
        } else {
            throw new RuntimeException("Unknown symbol kind: " + symbol.kind);
        }
    }

}
