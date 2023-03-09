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

import org.junit.jupiter.api.Test;

/**
 * @author Andrei_Tytsik
 */
public class SelectElementByKeyTest {

    private final SelectArrayElementByIndexTest arrayTest = new SelectArrayElementByIndexTest() {
        @Override
        protected Expression<?> instance(int index) {
            return new SelectElementByKey(index);
        }
    };

    private final SelectMapValueByKeyTest mapTest = new SelectMapValueByKeyTest() {
        @Override
        protected Expression<?> instance(Object key) {
            return new SelectElementByKey(key);
        }
    };

    @Test
    public void testArrayElementIsSelectedByIndex() {
        arrayTest.testArrayElementIsSelectedByIndex();
    }

    @Test
    public void testEmptyResultIsSelectedForEmptyOrNullArray() {
        arrayTest.testEmptyResultIsSelectedForNullArray();
    }

    @Test
    public void testListValueIsAccepted() {
        arrayTest.testListValueIsAccepted();
    }

    @Test
    public void testMapValueIsSelectedByKey() {
        mapTest.testMapValueIsSelectedByKey();
    }

    @Test
    public void testEmptyResultIsSelectedForEmptyOrNullMap() {
        mapTest.testEmptyResultIsSelectedForEmptyOrNullMap();
    }

    @Test
    public void testMapValueIsAccepted() {
        mapTest.testMapValueIsAccepted();
    }

}
