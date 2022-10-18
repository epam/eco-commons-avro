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
package com.epam.eco.commons.avro.utils;

import org.apache.avro.Schema;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Maksim_Gavrilov
 */
public class TestUtils {

    public static Schema getScheme(String filePath) throws IOException {
        InputStream inputStream = TestUtils.class
                .getClassLoader()
                .getResourceAsStream(filePath);
        return new Schema.Parser().parse(inputStream);
    }
}
