/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.entity.fieldset.json;


import com.es.lib.entity.fieldset.json.field.JsonField;
import com.es.lib.entity.type.JsonbType;

import java.util.LinkedHashMap;

/**
 * Fields values
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 30.05.16
 */
public class JsonFields extends LinkedHashMap<String, JsonField> {

    public static class UserType extends JsonbType {

        @Override
        public Class returnedClass() {
            return JsonFields.class;
        }
    }
}
