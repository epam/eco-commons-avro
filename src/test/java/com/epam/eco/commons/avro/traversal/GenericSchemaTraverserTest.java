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
package com.epam.eco.commons.avro.traversal;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.avro.Schema.Type;
import org.junit.Assert;
import org.junit.Test;

import com.epam.eco.commons.avro.AvroUtils;

/**
 * @author Andrei_Tytsik
 */
public class GenericSchemaTraverserTest {

    @Test
    public void testSchemaIsTraversed() throws Exception {
        AtomicInteger recordCount = new AtomicInteger(0);
        AtomicInteger unionCount = new AtomicInteger(0);
        AtomicInteger nullCount = new AtomicInteger(0);
        AtomicInteger arrayCount = new AtomicInteger(0);
        AtomicInteger mapCount = new AtomicInteger(0);
        AtomicInteger intCount = new AtomicInteger(0);
        AtomicInteger stringCount = new AtomicInteger(0);
        Set<String> fieldPaths = new HashSet<>();

        new GenericSchemaTraverser(new GenericSchemaTraverseListener() {
            @Override
            public void onSchema(String path, Object parentSchema, Object schema) {
                Type type = AvroUtils.typeOfGenericSchema(schema);
                if (Type.RECORD == type) {
                    recordCount.incrementAndGet();
                } else if (Type.UNION == type) {
                    unionCount.incrementAndGet();
                } else if (Type.NULL == type) {
                    nullCount.incrementAndGet();
                } else if (Type.ARRAY == type) {
                    arrayCount.incrementAndGet();
                } else if (Type.MAP == type) {
                    mapCount.incrementAndGet();
                } else if (Type.INT == type) {
                    intCount.incrementAndGet();
                } else if (Type.STRING == type) {
                    stringCount.incrementAndGet();
                } else {
                    throw new RuntimeException(String.format("Unexpected schema type %s", type));
                }
            }
            @Override
            public void onSchemaField(String path, Map<String, Object> parentSchema, Map<String, Object> field) {
                fieldPaths.add(path);
            }
        }).walk(SchemaTraverseTestData.GENERIC_SCHEMA);

        Assert.assertEquals(SchemaTraverseTestData.RECORD_COUNT, recordCount.get());
        Assert.assertEquals(SchemaTraverseTestData.UNION_COUNT, unionCount.get());
        Assert.assertEquals(SchemaTraverseTestData.NULL_COUNT, nullCount.get());
        Assert.assertEquals(SchemaTraverseTestData.ARRAY_COUNT, arrayCount.get());
        Assert.assertEquals(SchemaTraverseTestData.MAP_COUNT, mapCount.get());
        Assert.assertEquals(SchemaTraverseTestData.INT_COUNT, intCount.get());
        Assert.assertEquals(SchemaTraverseTestData.STRING_COUNT, stringCount.get());
        Assert.assertEquals(SchemaTraverseTestData.FIELD_PATHS, fieldPaths);
    }

    @Test
    public void testSchemaIsTraversedByDesiredPath() throws Exception {
        AtomicInteger recordCount = new AtomicInteger(0);
        AtomicInteger unionCount = new AtomicInteger(0);
        AtomicInteger nullCount = new AtomicInteger(0);
        AtomicInteger arrayCount = new AtomicInteger(0);
        AtomicInteger mapCount = new AtomicInteger(0);
        AtomicInteger intCount = new AtomicInteger(0);
        AtomicInteger stringCount = new AtomicInteger(0);
        Set<String> fieldPaths = new HashSet<>();

        new GenericSchemaTraverser(new GenericSchemaTraverseListener() {
            @Override
            public void onSchema(String path, Object parentSchema, Object schema) {
                Type type = AvroUtils.typeOfGenericSchema(schema);
                if (Type.RECORD == type) {
                    recordCount.incrementAndGet();
                } else if (Type.UNION == type) {
                    unionCount.incrementAndGet();
                } else if (Type.NULL == type) {
                    nullCount.incrementAndGet();
                } else if (Type.ARRAY == type) {
                    arrayCount.incrementAndGet();
                } else if (Type.MAP == type) {
                    mapCount.incrementAndGet();
                } else if (Type.INT == type) {
                    intCount.incrementAndGet();
                } else if (Type.STRING == type) {
                    stringCount.incrementAndGet();
                } else {
                    throw new RuntimeException(String.format("Unexpected schema type %s", type));
                }
            }
            @Override
            public void onSchemaField(String path, Map<String, Object> parentSchema, Map<String, Object> field) {
                fieldPaths.add(path);
            }
        }).walk(SchemaTraverseTestData.GENERIC_SCHEMA, SchemaTraverseTestData.DESIRED_PATH);

        Assert.assertEquals(SchemaTraverseTestData.DESIRED_PATH_RECORD_COUNT, recordCount.get());
        Assert.assertEquals(SchemaTraverseTestData.DESIRED_PATH_UNION_COUNT, unionCount.get());
        Assert.assertEquals(SchemaTraverseTestData.DESIRED_PATH_NULL_COUNT, nullCount.get());
        Assert.assertEquals(SchemaTraverseTestData.DESIRED_PATH_ARRAY_COUNT, arrayCount.get());
        Assert.assertEquals(SchemaTraverseTestData.DESIRED_PATH_MAP_COUNT, mapCount.get());
        Assert.assertEquals(SchemaTraverseTestData.DESIRED_PATH_INT_COUNT, intCount.get());
        Assert.assertEquals(SchemaTraverseTestData.DESIRED_PATH_STRING_COUNT, stringCount.get());
        Assert.assertEquals(SchemaTraverseTestData.DESIRED_PATH_FIELD_PATHS, fieldPaths);
    }

}
