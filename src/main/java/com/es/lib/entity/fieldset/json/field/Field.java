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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Field implements Serializable {

    private FieldTypeCode type;
    private String code;
    private String title;
    private String format;
    private List<FieldValue> values;

    public Field() {
        values = new ArrayList<>();
    }

    public Field(FieldTypeCode type, String code, String title) {
        this.type = type;
        this.code = code;
        this.title = title;
        values = new ArrayList<>();
    }

    public Field(FieldTypeCode type, String code, String title, List<FieldValue> values) {
        this.type = type;
        this.code = code;
        this.title = title;
        this.values = values;
    }

    public FieldTypeCode getType() {
        return type;
    }

    public void setType(FieldTypeCode type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormat() { return format; }

    public void setFormat(String format) { this.format = format; }

    public List<FieldValue> getValues() {
        return values;
    }

    public void setValues(List<FieldValue> values) {
        this.values = values;
    }

    public boolean isEmpty() { return values == null || values.isEmpty(); }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Field)) {
            return false;
        }
        Field f = (Field) obj;
        return Objects.equals(type, f.type) &&
            Objects.equals(code, f.code) &&
            Objects.equals(title, f.title) &&
            Objects.equals(values, f.values);
    }

    @JsonIgnore
    public FieldValue getSingleValue() {
        if (getValues().isEmpty()) {
            return new FieldValue();
        }
        return getValues().get(0);
    }

    @JsonIgnore
    public void setSingleValue(FieldValue value) {
        if (getValues().isEmpty()) {
            getValues().add(value);
            return;
        }
        this.getValues().set(0, value);
    }

    @Override
    public String toString() {
        return "Field{" +
               "type=" + type +
               ", code='" + code + '\'' +
               ", title='" + title + '\'' +
               ", values=" + values +
               '}';
    }
}
