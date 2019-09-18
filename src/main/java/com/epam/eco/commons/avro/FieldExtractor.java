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
package com.epam.eco.commons.avro;

import java.util.ArrayList;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.commons.lang3.Validate;

import com.epam.eco.commons.avro.traversal.SchemaTraverseListener;
import com.epam.eco.commons.avro.traversal.SchemaTraverser;

/**
 * @author Andrei_Tytsik
 */
public abstract class FieldExtractor {

    private FieldExtractor() {
    }

    public static List<FieldInfo> fromSchema(Schema schema) {
        Validate.notNull(schema, "Schema is null");

        List<FieldInfo> infos = new ArrayList<>();
        new SchemaTraverser(
                new SchemaTraverseListener() {
                    @Override
                    public void onSchemaField(String path, Schema parentSchema, Field field) {
                        infos.add(FieldInfo.with(parentSchema, field, path));
                    }
                    @Override
                    public void onSchema(String path, Schema parentSchema, Schema schema) {
                    }
                }).walk(schema);
        return infos;
    }

}
