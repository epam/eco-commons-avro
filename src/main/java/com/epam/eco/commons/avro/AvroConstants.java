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
package com.epam.eco.commons.avro;

/**
 * @author Andrei_Tytsik
 */
public abstract class  AvroConstants {

    public static final String SCHEMA_KEY_NAME = "name";
    public static final String SCHEMA_KEY_NAMESPACE = "namespace";
    public static final String SCHEMA_KEY_TYPE = "type";
    public static final String SCHEMA_KEY_FIELDS = "fields";
    public static final String SCHEMA_KEY_FIELD_NAME = "name";
    public static final String SCHEMA_KEY_FIELD_TYPE = "type";
    public static final String SCHEMA_KEY_FIELD_DOC = "doc";
    public static final String SCHEMA_KEY_FIELD_ORDER = "order";
    public static final String SCHEMA_KEY_ARRAY_ITEMS = "items";
    public static final String SCHEMA_KEY_MAP_VALUES = "values";
    public static final String SCHEMA_KEY_ENUM_SYMBOLS = "symbols";
    public static final String SCHEMA_KEY_FIXED_SIZE = "size";

    private AvroConstants() {
    }

}
