/*
 * Copyright 2019 EPAM Systems
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
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.epam.eco.commons.avro.AvroUtils;

/**
 * @author Andrei_Tytsik
 */
public class PathParser {

    private final String path;
    private final char[] pathChars;

    private int pos;

    public PathParser(String path) {
        Validate.notBlank(path, "Path is blank");

        this.path = path;
        this.pathChars = path.toCharArray();
    }

    public Expression<?> parse() {
        List<Expression<?>> exps = new ArrayList<>();

        reset();

        int ch;
        while ((ch = read()) != -1) {
            if (PathUtils.isGetRecordFieldBeginCh(ch)) {
                exps.add(parseGetRecordFieldExp());
            } else if (PathUtils.isSelectElementsBeginCh(ch)) {
                exps.add(parseSelectElementsExp());
            } else {
                throw new PathParseException(
                        path,
                        String.format(
                                "unexpected character '%s' at position %d", (char)ch, pos));
            }
        }

        return new ExpressionChain(exps);
    }

    private void reset() {
        pos = -1;
    }

    private Expression<?> parseGetRecordFieldExp() {
        StringBuilder fieldNameBuilder = new StringBuilder();
        int ch;
        while (!isGetRecordFieldEndCh(ch = read())) {
            fieldNameBuilder.append((char)ch);
        }
        if (ch != -1) {
            unread();
        }

        String fieldName = fieldNameBuilder.toString();
        if (!AvroUtils.isFieldNameValid(fieldName)) {
            throw new PathParseException(
                    path,
                    String.format("field name '%s' is invalid", fieldName));
        }

        return new GetRecordField(fieldName);
    }

    private boolean isGetRecordFieldEndCh(int ch) {
        return PathUtils.isMarkupCh(ch) || ch == -1;
    }

    private Expression<?> parseSelectElementsExp() {
        StringBuilder selectCriteriaBuilder = new StringBuilder();
        int ch;
        while (!PathUtils.isSelectElementsEndCh(ch = read())) {
            if (ch == -1) {
                throw new PathParseException(
                        path,
                        String.format(
                                "no closing ']' symbol for select criteria '%s'",
                                selectCriteriaBuilder.toString()));
            }

            selectCriteriaBuilder.append((char)ch);
        }

        String selectCriteria = selectCriteriaBuilder.toString();
        if (PathUtils.isSelectCriteriaMatchAll(selectCriteria)) {
            return new SelectAllElements();
        } else if (PathUtils.isSelectCriteriaByNullKey(selectCriteria)) {
            return new SelectElementByKey(null);
        } else if (PathUtils.isSelectCriteriaByNumericKey(selectCriteria)) {
            Number key = PathUtils.parseSelectCriteriaAsNumericKey(selectCriteria);
            return new SelectElementByKey(key);
        } else if (PathUtils.isSelectCriteriaByLiteralKey(selectCriteria)) {
            String key = PathUtils.parseSelectCriteriaAsLiteralKey(selectCriteria);
            return new SelectElementByKey(key);
        } else {
            throw new PathParseException(
                    path,
                    String.format("select criteria '%s' is invalid", selectCriteria));
        }
    }

    private int read() {
        if (pos >= pathChars.length - 1) {
            return -1;
        }
        return pathChars[++pos];
    }

    private void unread() {
        if (pos > -1 && pos < pathChars.length) {
            pos--;
        }
    }

}
