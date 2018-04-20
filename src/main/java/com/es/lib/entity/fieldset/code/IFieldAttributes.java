/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.entity.fieldset.code;

/**
 * Fields attributes
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 30.05.16
 */
public interface IFieldAttributes {

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
     * Validator
     */
    String VALIDATOR = "VALIDATOR";
    /**
     * Validator parameter
     */
    String VALIDATOR_PARAM = "VALIDATOR_PARAM_";
    /**
     * Position
     */
    String POSITION = "POSITION";
    /**
     * Value format (for example date pattern: dd-MM-yyyy)
     */
    String FORMAT = "FORMAT";


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

    interface Validator {

        interface Regex extends Validator {

            /**
             * Name (code)
             */
            String G_NAME = "REGEX";
            /**
             * Regexp for validate
             */
            String REGEXP = "REGEXP";
        }

        interface Numeric extends Validator {

            /**
             * Name (code)
             */
            String G_NAME = "NUMERIC";
            /**
             * Number ranges
             */
            String RANGES = "RANGES";
        }

        interface Bik extends Validator {

            /**
             * Name (code)
             */
            String G_NAME = "BIK";
        }
    }

}
