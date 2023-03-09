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
package com.epam.eco.commons.avro.avpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * @author Andrei_Tytsik
 */
public class ExpressionChain implements Expression<Object> {

    private final List<Expression<?>> expressions;

    public ExpressionChain(List<Expression<?>> expressions) {
        Validate.notEmpty(expressions, "List of expressions is null or empty");
        Validate.noNullElements(expressions, "List of expressions contains null elements");

        this.expressions = Collections.unmodifiableList(expressions);
    }

    @Override
    public boolean accepts(Object object) {
        return expressions.get(0).accepts(object);
    }

    @Override
    public List<EvaluationResult> eval(Object object) {
        if (object == null) {
            return Collections.emptyList();
        }

        List<EvaluationResult> output = null;
        for (Expression<?> exp : expressions) {
            List<Object> input = output != null ? toInput(output) : Collections.singletonList(object);
            output = evalAll(exp, input);
        }
        return output;
    }

    public List<Expression<?>> getExpressions() {
        return Collections.unmodifiableList(expressions);
    }

    @SuppressWarnings("unchecked")
    public List<EvaluationResult> evalAll(Expression<?> exp, List<Object> input) {
        List<EvaluationResult> output = new ArrayList<>();
        for (Object object : input) {
            output.addAll(((Expression<Object>)exp).eval(object));
        }
        return output;
    }

    @Override
    public String toString() {
        return StringUtils.join(expressions, "->");
    }

    private List<Object> toInput(List<EvaluationResult> output) {
        return output.stream().
                map(EvaluationResult::getValue).
                filter(Objects::nonNull).
                collect(Collectors.toList());
    }

}
