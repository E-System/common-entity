/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.entity.fieldset.code;

/**
 * Input element view type
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 26.05.15
 */
public interface FieldViewCode {

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
    }

    /**
     * Address
     */
    interface Address {

        /**
         * TO street
         */
        String Street = "street";
        /**
         * To building number
         */
        String Building = "building";
    }
}
