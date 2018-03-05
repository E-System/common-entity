/*
 * Copyright 2018 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.es.lib.entity.model;

import java.io.Serializable;

/**
 * Typed value
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 05.03.18
 */
public class TypedValue implements Serializable {

    private final ValueTypeCode type;
    private final String value;

    public TypedValue(ValueTypeCode type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Value type
     *
     * @return value type
     */
    public ValueTypeCode getType() {
        return type;
    }

    /**
     * Value
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TypedValue[" +
               "type='" + type + "'" +
               ", value='" + value + "'" +
               ']';
    }
}
