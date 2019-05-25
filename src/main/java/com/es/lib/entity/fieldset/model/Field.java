/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.entity.fieldset.model;

import com.es.lib.entity.fieldset.IFieldSetOwner;
import com.es.lib.entity.fieldset.code.FieldTypeCode;
import com.es.lib.entity.fieldset.code.IFieldAttributes;
import com.es.lib.entity.fieldset.json.field.JsonFieldValue;
import com.es.lib.entity.fieldset.json.field.JsonFieldMetadata;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 08.04.19
 */
public class Field implements Serializable {

    private static final String CALENDAR_DATE_PATTERN = "dd.MM.yyyy";

    private Form form;
    private String name;
    private IFieldSetOwner field;
    private JsonFieldMetadata value;
    private Collection<String> parents;
    private Collection<Field> childElements;
    private Collection<JsonFieldValue> selectorValues;

    private SimpleDateFormat sdf;

    public Field() { }

    public Field load(Form form, IFieldSetOwner field, Collection<? extends IFieldSetOwner> fields, Collection<String> parents) {
        this.form = form;
        this.name = field.getCode();
        this.field = field;
        String format = field.getAttributes().get(IFieldAttributes.FORMAT);
        if (field.getFieldType() == FieldTypeCode.DATE) {
            if (StringUtils.isBlank(format)) {
                format = CALENDAR_DATE_PATTERN;
            }
            sdf = new SimpleDateFormat(format);
        }
        if (field.getSelector() != null) {
            this.selectorValues =
                field.getSelector()
                     .stream()
                     .map(v -> new JsonFieldValue(v.getValue(), v.getTitle()))
                     .collect(Collectors.toList());
        }
        this.value = form.getJson().computeIfAbsent(name, k -> new JsonFieldMetadata(field.getFieldType(), field.getCode(), field.getName()));
        this.getParents().addAll(parents);
        Collection<String> newParents = new ArrayList<>(parents);
        newParents.add(name);
        fields.stream()
              .filter(e -> Objects.equals(field.getOwnerId(), e.getOwnerId()) && name.equals(e.getAttributes().get(IFieldAttributes.PARENT)))
              .forEach(
                  e -> getChildElements().add(new Field().load(form, e, fields, newParents))
              );
        return this;
    }

    public Form getForm() { return form; }

    public String getName() {
        return name;
    }

    public Collection<String> getParents() {
        if (parents == null) {
            parents = new LinkedList<>();
        }
        return parents;
    }

    public IFieldSetOwner getField() { return field; }

    public void setField(IFieldSetOwner field) { this.field = field;}

    public FieldTypeCode getType() { return field.getFieldType();}

    public void setType(FieldTypeCode type) { field.setFieldType(type); }

    public String getTitle() { return field.getName(); }

    public void setTitle(String title) { field.setName(title); }

    public Map<String, String> getAttributes() {
        return field.getAttributes();
    }

    public void setAttributes(Map<String, String> attributes) {
        field.setAttributes(attributes);
    }

    //Все значения
    public List<JsonFieldValue> getValues() { return this.value.getValues(); }

    public void setValues(List<JsonFieldValue> values) { this.value.setValues(values); }

    //Единичное значение
    public JsonFieldValue getSingleValue() { return this.value.getSingleValue(); }

    public void setSingleValue(JsonFieldValue value) { this.value.setSingleValue(value); }

    //Единичное значение в строковом виде
    public String getSingleStringValue() { return this.value.getSingleValue().getValue(); }

    public void setSingleStringValue(String value) { this.value.setSingleValue(new JsonFieldValue(value)); }

    //Единичное значение в виде даты
    public Date getSingleDateValue() {
        try {
            String format = this.value.getSingleValue().getFormat();
            if (StringUtils.isNotBlank(format)) {
                new SimpleDateFormat(format).parse(this.value.getSingleValue().getValue());
            }
            return sdf.parse(this.value.getSingleValue().getValue());
        } catch (Exception e) {
            return null;
        }
    }

    public void setSingleDateValue(Date value) {
        String val = value == null ? null : sdf.format(value);
        String format = field.getAttributes().get(IFieldAttributes.FORMAT);
        if (StringUtils.isBlank(format) || CALENDAR_DATE_PATTERN.equals(format)) {
            format = null;
        }
        this.value.setSingleValue(new JsonFieldValue(val, val, format));
    }

    public Collection<JsonFieldValue> getSelectorValues() { return selectorValues; }

    public boolean isVisible() {
        String visible = getAttributes().get(IFieldAttributes.VISIBLE);
        return visible == null || Boolean.parseBoolean(visible);
    }

    public String getParentClassifier() {
        return StringUtils.join(getParents(), " ");
    }

    public boolean isChildAvailable() {
        return getChildElements().size() > 0;
    }

    public Collection<Field> getChildElements() {
        if (childElements == null) {
            childElements = new LinkedList<>();
        }
        return childElements;
    }

    @Override
    public String toString() {
        return "Field{" +
               "name='" + name + '\'' +
               ", parents=" + parents +
               ", field=" + field +
               ", childElements=" + childElements +
               '}';
    }
}
