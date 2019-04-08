/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.entity.fieldset.model;

import com.es.lib.entity.fieldset.IFieldTemplateEvaluator;
import com.es.lib.entity.fieldset.IFieldSetOwner;
import com.es.lib.entity.fieldset.code.IFieldAttributes;
import com.es.lib.entity.fieldset.json.FieldSetValuesJson;
import com.es.lib.entity.fieldset.json.field.FieldValue;
import com.es.lib.entity.fieldset.json.field.SelectorItem;

import java.io.Serializable;
import java.util.*;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 08.04.19
 */
public class Form implements Serializable {

    private FieldSetValuesJson json;
    private Collection<Field> fields;
    private Collection<Field> linearFields;

    public Form() { }

    public Form load(Collection<? extends IFieldSetOwner> fields, FieldSetValuesJson json, IFieldTemplateEvaluator fieldEvaluator, Map<String, Object> context) {
        this.json = json;
        fields.stream()
            .filter(e -> e.getAttributes().get(IFieldAttributes.PARENT) == null)
            .forEach(
                e -> addField(e, fields, json)
            );

        processDefaultValues(fields, fieldEvaluator, context);
        processVisibility(null, getFields(), fieldEvaluator, context);

        unroll(getFields(), getLinearFields());
        return this;
    }

    public void update(IFieldTemplateEvaluator fieldEvaluator, Map<String, Object> context) {
        processVisibility(null, getFields(), fieldEvaluator, context);
    }

    private void processDefaultValues(Collection<? extends IFieldSetOwner> fields, IFieldTemplateEvaluator fieldEvaluator, Map<String, Object> context) {
        fields.forEach(v -> fillDefaultValue(v, fieldEvaluator, context));
    }

    private void fillDefaultValue(IFieldSetOwner field, IFieldTemplateEvaluator fieldEvaluator, Map<String, Object> context) {
        com.es.lib.entity.fieldset.json.field.Field v = json.computeIfAbsent(field.getCode(), k -> new com.es.lib.entity.fieldset.json.field.Field(field.getFieldType(), field.getCode(), field.getName()));
        if (v.isEmpty()) {
            if (!v.getType().isFile()) {
                if (v.getType().isSelector() && !v.getType().isManyAvailable()) {
                    Iterator<SelectorItem> keyIterator = field.getSelector().iterator();
                    if (keyIterator.hasNext()) {
                        SelectorItem selector = keyIterator.next();
                        v.setSingleValue(new FieldValue(selector.getValue(), selector.getTitle()));
                    }
                } else {
                    String defaultValue = field.getAttributes().get(IFieldAttributes.VALUE);
                    String defaultValueTemplate = field.getAttributes().get(IFieldAttributes.VALUE_TEMPLATE);
                    if (defaultValueTemplate != null) {
                        String value = fieldEvaluator.evaluateVelocity(defaultValueTemplate, context);
                        v.setSingleValue(new FieldValue(value));
                    } else if (defaultValue != null) {
                        v.setSingleValue(new FieldValue(defaultValue));
                    }
                }
            }
        }
    }

    private void processVisibility(Field parent, Collection<Field> child, IFieldTemplateEvaluator fieldEvaluator, Map<String, Object> context) {
        child.forEach(v -> {
            boolean childVisible = parent == null || parent.getAttributes().get(IFieldAttributes.VISIBLE) == null || Boolean.parseBoolean(parent.getAttributes().get(IFieldAttributes.VISIBLE));
            if (parent != null) {
                String regexp = v.getAttributes().get(IFieldAttributes.PARENT_REGEXP);
                if (parent.getSingleValue() == null || parent.getSingleValue().getValue() == null || !parent.getSingleValue().getValue().matches(regexp)) {
                    childVisible = false;
                }
            }
            if (!childVisible) {
                v.getAttributes().put(IFieldAttributes.VISIBLE, String.valueOf(false));
                v.getValues().clear();
            } else {
                v.getAttributes().remove(IFieldAttributes.VISIBLE);
                fillDefaultValue(v.getField(), fieldEvaluator, context);
            }
            processVisibility(v, v.getChildElements(), fieldEvaluator, context);
        });
    }

    private void unroll(Collection<Field> source, Collection<Field> target) {
        source.forEach(v -> {
            target.add(v);
            unroll(v.getChildElements(), target);
        });
    }

    private void addField(IFieldSetOwner field, Collection<? extends IFieldSetOwner> fields, FieldSetValuesJson json) {
        getFields().add(new Field().load(this, field, fields, json, Collections.emptyList()));
    }


    public Collection<Field> getFields() {
        if (fields == null) {
            fields = new LinkedList<>();
        }
        return fields;
    }

    public Collection<Field> getLinearFields() {
        if (linearFields == null) {
            linearFields = new LinkedList<>();
        }
        return linearFields;
    }

    public FieldSetValuesJson getJson() {
        return json;
    }

    @Override
    public String toString() {
        return "DynamicForm{" +
               "fields=" + fields +
               ", linearFields=" + linearFields +
               '}';
    }
}
