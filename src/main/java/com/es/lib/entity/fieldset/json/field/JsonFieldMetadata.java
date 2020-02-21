/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.entity.fieldset.json.field;


import com.es.lib.entity.fieldset.code.FieldTypeCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Field
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 30.05.16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonFieldMetadata implements Serializable {

    public static final String CALENDAR_DATE_PATTERN = "dd.MM.yyyy";

    private FieldTypeCode type;
    private String code;
    private String title;
    private String format;
    private List<JsonFieldValue> values = new ArrayList<>();

    public JsonFieldMetadata(FieldTypeCode type, String code, String title, List<JsonFieldValue> values) {
        this.type = type;
        this.code = code;
        this.title = title;
        this.values = values;
    }

    public boolean isEmpty() { return values == null || values.isEmpty(); }

    @JsonIgnore
    public JsonFieldValue getSingleValue() {
        if (getValues().isEmpty()) {
            return new JsonFieldValue();
        }
        return getValues().get(0);
    }

    @JsonIgnore
    public void setSingleValue(JsonFieldValue value) {
        if (getValues().isEmpty()) {
            getValues().add(value);
            return;
        }
        this.getValues().set(0, value);
    }

    @JsonIgnore
    public String getSingleValueString() {
        return getSingleValue().getValue();
    }
}
