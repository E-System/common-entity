/*
 * Copyright (c) E-System LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2020
 */

package com.es.lib.entity.model.field.json.value;


import com.es.lib.entity.model.field.json.JsonFieldValue;

import java.util.Map;

/**
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 04.09.2020
 */
public class AttrsFieldValue extends JsonFieldValue<Map<String, String>> {

    public static final String CODE = "attrs";

    public AttrsFieldValue() { super(CODE); }

    public AttrsFieldValue(Map<String, String> value) {
        super(CODE, value);
    }

    public AttrsFieldValue(Map<String, String> value, String title) {
        super(CODE, value, title);
    }

    public AttrsFieldValue(Map<String, String> value, String title, String format) {
        super(CODE, value, title, format);
    }
}
