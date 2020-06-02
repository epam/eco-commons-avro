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

import org.apache.avro.generic.GenericRecord;
import org.apache.commons.lang3.Validate;

/**
 * @author Andrei_Tytsik
 */
public class GetRecordFieldResult extends AbstractEvaluationResult {

    private final String fieldName;
    private final boolean validSchemaField;

    public GetRecordFieldResult(
            GenericRecord containerRecord,
            String fieldName,
            boolean validSchemaField,
            Object value) {
        super(containerRecord, value);

        Validate.notBlank(fieldName, "Field name is blank");

        this.fieldName = fieldName;
        this.validSchemaField = validSchemaField;
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isValidSchemaField() {
        return validSchemaField;
    }

    public GenericRecord getContainerRecord() {
        return (GenericRecord)getContainer();
    }

    @Override
    public String toString() {
        return String.format(
                "{containerRecord: %s, fieldName: %s, validSchemaField: %s, value: %s}",
                getContainerRecord(), getFieldName(), isValidSchemaField(), getValue());
    }

    public static GetRecordFieldResult with(
            GenericRecord containerRecord,
            String fieldName,
            boolean validSchemaField,
            Object value) {
        return new GetRecordFieldResult(
                containerRecord,
                fieldName,
                validSchemaField,
                value);
    }

}
