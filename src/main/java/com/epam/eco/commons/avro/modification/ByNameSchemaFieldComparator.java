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
package com.epam.eco.commons.avro.modification;

import java.util.Map;

import com.epam.eco.commons.avro.AvroUtils;

/**
 * @author Andrei_Tytsik
 */
public class ByNameSchemaFieldComparator extends RecordsFirstSchemaFieldComparator {

    public static final ByNameSchemaFieldComparator ASC = new ByNameSchemaFieldComparator(true);
    public static final ByNameSchemaFieldComparator DESC = new ByNameSchemaFieldComparator(false);

    private final boolean asc;

    private ByNameSchemaFieldComparator(boolean asc) {
        this.asc = asc;
    }

    @Override
    public int doCompare(Map<String, Object> field1, Map<String, Object> field2) {
        String fieldName1 = AvroUtils.nameOfGenericField(field1);
        String fieldName2 = AvroUtils.nameOfGenericField(field2);
        return (!asc ? -1 : 1) * fieldName1.compareTo(fieldName2);
    }

}
