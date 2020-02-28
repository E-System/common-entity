/*
 * Copyright 2020 E-System LLC
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
package com.es.lib.entity.model.field.code;

/**
 * Input element view type
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 26.05.15
 */
public interface IFieldView {

    /**
     * Text input
     */
    interface Text {

        /**
         * Single line
         */
        String Single = "single";
        String Many = "many";
    }

    /**
     * Selector
     */
    interface Selector {

        /**
         * Combobox, list
         */
        String Holistic = "holistic";
        /**
         * Checkbox, Radio button
         */
        String Separate = "separate";
        /**
         * Menu
         */
        String Menu = "menu";
    }

    /**
     * Address
     */
    interface Address {

        /**
         * To street
         */
        String Street = "street";
        /**
         * To building number
         */
        String Building = "building";
    }
}
