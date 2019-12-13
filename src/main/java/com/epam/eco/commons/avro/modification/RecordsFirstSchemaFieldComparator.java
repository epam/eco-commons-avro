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

import java.util.Comparator;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Type;

import com.epam.eco.commons.avro.AvroUtils;

/**
 * @author Andrei_Tytsik
 */
public abstract class RecordsFirstSchemaFieldComparator implements Comparator<Map<String, Object>> {

    @Override
    public final int compare(Map<String, Object> field1, Map<String, Object> field2) {
        Schema.Type field1Type = AvroUtils.effectiveTypeOfGenericFieldOrElseNullIfUnknown(field1);
        Schema.Type field2Type = AvroUtils.effectiveTypeOfGenericFieldOrElseNullIfUnknown(field2);
        if (isRecordOrAmbiguous(field1Type) && isRecordOrAmbiguous(field2Type)) {
            return 0;
        } else if (isRecordOrAmbiguous(field1Type) && !isRecordOrAmbiguous(field2Type)) {
            return -1;
        } else if (!isRecordOrAmbiguous(field1Type) && isRecordOrAmbiguous(field2Type)) {
            return 1;
        }
        return doCompare(field1, field2);
    }

    protected abstract int doCompare(Map<String, Object> field1, Map<String, Object> field2);

    private static boolean isRecordOrAmbiguous(Type type) {
        return
                type == Schema.Type.RECORD ||
                type == null || // named type (could refer record type)
                type == Schema.Type.UNION; // non-nullable union (could contain record type)
    }

}
