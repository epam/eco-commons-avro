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

import java.util.Collections;
import java.util.List;

import org.apache.avro.generic.GenericRecord;
import org.apache.commons.lang3.Validate;

/**
 * @author Andrei_Tytsik
 */
public class GetRecordField implements Expression<GenericRecord> {

    private final String fieldName;

    public GetRecordField(String fieldName) {
        Validate.notBlank(fieldName, "Field name is blank");

        this.fieldName = fieldName;
    }

    @Override
    public boolean accepts(Object object) {
        return object instanceof GenericRecord;
    }

    @Override
    public List<EvaluationResult> eval(GenericRecord record) {
        if (record == null) {
            return Collections.emptyList();
        }

        boolean validSchemaField = record.getSchema().getField(fieldName) != null;
        Object value = validSchemaField ? record.get(fieldName) : null;

        return Collections.singletonList(
                GetRecordFieldResult.with(
                        record,
                        fieldName,
                        validSchemaField,
                        value));
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        return String.format("Record.%s", fieldName);
    }

}
