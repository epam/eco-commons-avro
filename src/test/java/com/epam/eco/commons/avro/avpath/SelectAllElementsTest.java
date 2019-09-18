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

import org.junit.Test;

/**
 * @author Andrei_Tytsik
 */
public class SelectAllElementsTest {

    private SelectAllArrayElementsTest arrayTest = new SelectAllArrayElementsTest() {
        @Override
        protected Expression<?> instance() {
            return new SelectAllElements();
        }
    };

    private SelectAllMapValuesTest mapTest = new SelectAllMapValuesTest() {
        @Override
        protected Expression<?> instance() {
            return new SelectAllElements();
        }
    };

    @Test
    public void testAllArrayElementsAreSelected() throws Exception {
        arrayTest.testAllArrayElementsAreSelected();
    }

    @Test
    public void testEmptyResultIsSelectedForEmptyOrNullArray() throws Exception {
        arrayTest.testEmptyResultIsSelectedForEmptyOrNullArray();
    }

    @Test
    public void testListValueIsAccepted() throws Exception {
        arrayTest.testListValueIsAccepted();
    }

    @Test
    public void testAllMapValuesAreSelected() throws Exception {
        mapTest.testAllMapValuesAreSelected();
    }

    @Test
    public void testEmptyResultIsSelectedForEmptyOrNullMap() throws Exception {
        mapTest.testEmptyResultIsSelectedForEmptyOrNullMap();
    }

    @Test
    public void testMapValueIsAccepted() throws Exception {
        mapTest.testMapValueIsAccepted();
    }

}
