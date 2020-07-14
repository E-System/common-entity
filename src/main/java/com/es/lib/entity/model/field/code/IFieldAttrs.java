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
 * Fields attributes
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 30.05.16
 */
public interface IFieldAttrs {

    /**
     * View type
     */
    String VIEW_TYPE = "VIEW_TYPE";
    /**
     * Default value
     */
    String VALUE = "VALUE";
    /**
     * Velocity template for value (for calculated values)
     */
    String VALUE_TEMPLATE = "VALUE_TEMPLATE";
    /**
     * Parent element code
     */
    String PARENT = "PARENT";
    /**
     * Regexp for check parent value for current field availability
     */
    String PARENT_REGEXP = "PARENT_REGEXP";
    /**
     * Position
     */
    String POSITION = "POSITION";
    /**
     * Value format (for example date pattern: dd-MM-yyyy)
     */
    String FORMAT = "FORMAT";
    /**
     * Visible flag
     */
    String VISIBLE = "VISIBLE";
    /**
     * Required flag (need to fill)
     */
    String REQUIRED = "REQUIRED";


    interface Position {

        /**
         * Fill
         */
        String FILL = "FILL";
        /**
         * Left
         */
        String LEFT = "LEFT";
        /**
         * Right
         */
        String RIGHT = "RIGHT";
    }
}
