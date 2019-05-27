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
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Field
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 30.05.16
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonFieldMetadata implements Serializable {

    public static final String CALENDAR_DATE_PATTERN = "dd.MM.yyyy";

    private FieldTypeCode type;
    private String code;
    private String title;
    private String format;
    private List<JsonFieldValue> values;

    public JsonFieldMetadata() {
        values = new ArrayList<>();
    }

    public JsonFieldMetadata(FieldTypeCode type, String code, String title) {
        this();
        this.type = type;
        this.code = code;
        this.title = title;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JsonFieldMetadata)) {
            return false;
        }
        JsonFieldMetadata f = (JsonFieldMetadata) obj;
        return Objects.equals(type, f.type) &&
               Objects.equals(code, f.code) &&
               Objects.equals(title, f.title) &&
               Objects.equals(values, f.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, code, title, format, values);
    }

    @Override
    public String toString() {
        return "JsonFieldMetadata{" +
               "type=" + type +
               ", code='" + code + '\'' +
               ", title='" + title + '\'' +
               ", values=" + values +
               '}';
    }
}
