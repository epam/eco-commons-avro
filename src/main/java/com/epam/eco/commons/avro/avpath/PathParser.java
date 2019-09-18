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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;

import com.epam.eco.commons.avro.AvroUtils;

/**
 * @author Andrei_Tytsik
 */
public class PathParser {

    private static final int GET_RECORD_FIELD_BEGIN = '/';
    private static final int SELECT_ELEMENTS_BEGIN = '[';
    private static final int SELECT_ELEMENTS_END = ']';

    private static final String SELECT_CRITERIA_MATCH_ALL = "*";
    private static final String SELECT_CRITERIA_BY_NULL_KEY = "null";
    private static final Pattern SELECT_CRITERIA_BY_LITERAL_KEY_PATTERN = Pattern.compile("^'(.*)'$");

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
            if (isGetRecordFieldBegin(ch)) {
                exps.add(parseGetRecordFieldExp());
            } else if (isSelectElementsBegin(ch)) {
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
        while (!isGetRecordFieldEnd(ch = read())) {
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

    private boolean isGetRecordFieldBegin(int ch) {
        return GET_RECORD_FIELD_BEGIN == ch;
    }

    private boolean isGetRecordFieldEnd(int ch) {
        return isMarkupSymbol(ch);
    }

    private boolean isMarkupSymbol(int ch) {
        return
                GET_RECORD_FIELD_BEGIN == ch ||
                SELECT_ELEMENTS_BEGIN == ch ||
                SELECT_ELEMENTS_END == ch ||
                -1 == ch;
    }

    private Expression<?> parseSelectElementsExp() {
        StringBuilder selectCriteriaBuilder = new StringBuilder();
        int ch;
        while (!isSelectElementsEnd(ch = read())) {
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
        if (isSelectCriteriaMatchAll(selectCriteria)) {
            return new SelectAllElements();
        } else if (isSelectCriteriaByNullKey(selectCriteria)) {
            return new SelectElementByKey(null);
        } else if (isSelectCriteriaByNumericKey(selectCriteria)) {
            Number key = parseSelectCriteriaAsNumericKey(selectCriteria);
            return new SelectElementByKey(key);
        } else if (isSelectCriteriaByLiteralKey(selectCriteria)) {
            String key = parseSelectCriteriaAsLiteralKey(selectCriteria);
            return new SelectElementByKey(key);
        } else {
            throw new PathParseException(
                    path,
                    String.format("select criteria '%s' is invalid", selectCriteria));
        }
    }

    private boolean isSelectCriteriaMatchAll(String selectCriteria) {
        return SELECT_CRITERIA_MATCH_ALL.equals(selectCriteria);
    }

    private boolean isSelectCriteriaByNullKey(String selectCriteria) {
        return SELECT_CRITERIA_BY_NULL_KEY.equals(selectCriteria);
    }

    private boolean isSelectCriteriaByNumericKey(String selectCriteria) {
        return NumberUtils.isNumber(selectCriteria);
    }

    private Number parseSelectCriteriaAsNumericKey(String selectCriteria) {
        return NumberUtils.createNumber(selectCriteria);
    }

    private boolean isSelectCriteriaByLiteralKey(String selectCriteria) {
        return SELECT_CRITERIA_BY_LITERAL_KEY_PATTERN.matcher(selectCriteria).matches();
    }

    private String parseSelectCriteriaAsLiteralKey(String selectCriteria) {
        Matcher matcher = SELECT_CRITERIA_BY_LITERAL_KEY_PATTERN.matcher(selectCriteria);
        matcher.find();
        return matcher.group(1);
    }

    private boolean isSelectElementsBegin(int ch) {
        return SELECT_ELEMENTS_BEGIN == ch;
    }

    private boolean isSelectElementsEnd(int ch) {
        return SELECT_ELEMENTS_END == ch;
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
