/*
 * Copyright (c) E-System LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2020
 */

package com.es.lib.entity.model.field.json.value;

import com.es.lib.entity.model.field.json.JsonFieldValue;

/**
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 04.09.2020
 */
public class NumberFieldValue extends JsonFieldValue<Number> {

    public static final String CODE = "num";

    public NumberFieldValue() { super(CODE); }

    public NumberFieldValue(Number value) {
        super(CODE, value);
    }

    public NumberFieldValue(Number value, String title) {
        super(CODE, value, title);
    }

    public NumberFieldValue(Number value, String title, String format) {
        super(CODE, value, title, format);
    }
}
