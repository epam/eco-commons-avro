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
package com.epam.eco.commons.avro.converter;

import org.apache.avro.Schema;

/**
 * @author Ihar_Karoza
 */
public class AvroConversionException extends RuntimeException {

    private static final long serialVersionUID = 8715128591741702115L;

    public AvroConversionException(String message) {
        super(message);
    }

    public AvroConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public AvroConversionException(Object value, Schema schema) {
        super(formatMessage(value, schema, null));
    }

    public AvroConversionException(Object value, Schema schema, String reason) {
        super(formatMessage(value, schema, reason));
    }

    private static String formatMessage(Object value, Schema schema, String reason) {
        return String.format("Can not convert value %s to schema %s: %s",
                             value,
                             schema,
                             reason != null ? reason : "");
    }

}
