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

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 05.03.18
 */
public enum ValueTypeCode {
    /**
     * String
     */
    STRING,
    /**
     * Number
     */
    NUMERIC,
    /**
     * Logical
     */
    BOOLEAN,
    /**
     * Json object
     */
    JSON,
    /**
     * Key value set
     */
    PROPERTY,
    /**
     * Special type for price input (save in coins)
     */
    PRICE;

    public boolean isStringValue() {
        return STRING.equals(this);
    }

    public boolean isNumericValue() {
        return NUMERIC.equals(this);
    }

    public boolean isBooleanValue() {
        return BOOLEAN.equals(this);
    }

    public boolean isJsonValue() {
        return JSON.equals(this);
    }

    public boolean isPropertyValue() {
        return PROPERTY.equals(this);
    }

    public boolean isPriceValue() {
        return PRICE.equals(this);
    }
}