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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author Andrei_Tytsik
 */
abstract class PathUtils {

    public static final int GET_RECORD_FIELD_BEGIN_CH = '/';
    public static final int SELECT_ELEMENTS_BEGIN_CH = '[';
    public static final int SELECT_ELEMENTS_END_CH = ']';

    public static final String SELECT_CRITERIA_MATCH_ALL = "*";
    public static final String SELECT_CRITERIA_BY_NULL_KEY = "null";
    public static final Pattern SELECT_CRITERIA_BY_LITERAL_KEY_PATTERN = Pattern.compile("^'(.*)'$");

    public static final String ELEMENT_SELECTOR_MATCH_ALL = "[*]";

    private PathUtils() {
    }

    public static String templatePathToPlain(String templatePath, String separator) {
        if (templatePath == null) {
            return null;
        }

        String[] parts = StringUtils.split(templatePath, (char)GET_RECORD_FIELD_BEGIN_CH);
        StringBuilder plainPath = new StringBuilder();
        for (String part : parts) {
            if (StringUtils.isEmpty(part)) {
                continue;
            }
            part = StringUtils.substringBefore(part, ELEMENT_SELECTOR_MATCH_ALL);

            if (plainPath.length() > 0) {
                plainPath.append(separator);
            }
            plainPath.append(part);
        }
        return plainPath.toString();
    }

    public static boolean isMatchAllPath(String path) {
        return path != null && path.endsWith(ELEMENT_SELECTOR_MATCH_ALL);
    }

    public static boolean isGetRecordFieldBeginCh(int ch) {
        return GET_RECORD_FIELD_BEGIN_CH == ch;
    }

    public static boolean isSelectCriteriaMatchAll(String selectCriteria) {
        return SELECT_CRITERIA_MATCH_ALL.equals(selectCriteria);
    }

    public static boolean isSelectCriteriaByNullKey(String selectCriteria) {
        return SELECT_CRITERIA_BY_NULL_KEY.equals(selectCriteria);
    }

    public static boolean isSelectCriteriaByNumericKey(String selectCriteria) {
        return NumberUtils.isNumber(selectCriteria);
    }

    public static Number parseSelectCriteriaAsNumericKey(String selectCriteria) {
        return NumberUtils.createNumber(selectCriteria);
    }

    public static boolean isSelectCriteriaByLiteralKey(String selectCriteria) {
        return SELECT_CRITERIA_BY_LITERAL_KEY_PATTERN.matcher(selectCriteria).matches();
    }

    public static String parseSelectCriteriaAsLiteralKey(String selectCriteria) {
        Matcher matcher = SELECT_CRITERIA_BY_LITERAL_KEY_PATTERN.matcher(selectCriteria);
        matcher.find();
        return matcher.group(1);
    }

    public static boolean isSelectElementsBeginCh(int ch) {
        return SELECT_ELEMENTS_BEGIN_CH == ch;
    }

    public static boolean isSelectElementsEndCh(int ch) {
        return SELECT_ELEMENTS_END_CH == ch;
    }

    public static boolean isMarkupCh(int ch) {
        return
                GET_RECORD_FIELD_BEGIN_CH == ch ||
                SELECT_ELEMENTS_BEGIN_CH == ch ||
                SELECT_ELEMENTS_END_CH == ch;
    }

}
