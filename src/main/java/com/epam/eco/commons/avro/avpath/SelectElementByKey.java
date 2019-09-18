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

/**
 * @author Andrei_Tytsik
 */
public class SelectElementByKey extends DelegatingEvaluationExpression {

    private final Object key;

    public SelectElementByKey(Object key) {
        this.key = key;
        initDelegates();
    }

    public Object getKey() {
        return key;
    }

    private void initDelegates() {
        addDelegate(new SelectMapValueByKey(key));
        if (key instanceof Integer && (Integer)key >= 0) {
            addDelegate(new SelectArrayElementByIndex((Integer)key));
        }
    }

}
