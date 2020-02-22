/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.entity.model.field.code;

/**
 * Fields attributes
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 30.05.16
 */
public interface IFieldValidators {


    interface Regex {

        /**
         * Validator code
         */
        String G_NAME = "REGEX";
        /**
         * Regexp for validate
         */
        String REGEXP = "REGEXP";
    }

    interface Numeric {

        /**
         * Validator code
         */
        String G_NAME = "NUMERIC";
        /**
         * Number ranges
         */
        String RANGES = "RANGES";
    }

    interface Bik {

        /**
         * Validator code
         */
        String G_NAME = "BIK";
    }

    interface Inn {

        /**
         * Validator code
         */
        String G_NAME = "INN";
    }

    interface Kpp {

        /**
         * Validator code
         */
        String G_NAME = "KPP";
    }

    interface PassportDate {

        /**
         * Validator code
         */
        String G_NAME = "PASSPORT_DATE";
    }
}
