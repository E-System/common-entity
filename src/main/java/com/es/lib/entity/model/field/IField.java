/*
 * Copyright (c) E-System LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2018
 */

package com.es.lib.entity.model.field;

import com.es.lib.entity.iface.IAttrsOwner;
import com.es.lib.entity.model.field.code.FieldType;
import com.es.lib.entity.model.field.code.IFieldAttrs;
import com.es.lib.entity.model.field.json.JsonFieldValue;
import com.es.lib.entity.model.field.json.JsonSelectorItems;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.es.lib.entity.model.field.json.JsonField.CALENDAR_DATE_PATTERN;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 08.04.19
 */
public interface IField extends IAttrsOwner {

    FieldType getFieldType();

    void setFieldType(FieldType fieldTypeCode);

    default String getSection() { return null; }

    String getCode();

    String getName();

    void setName(String name);

    int getSorting();

    Long getOwnerId();

    JsonSelectorItems getSelectorItems();

    default String getSelectorRequest() {
        return null;
    }

    default SimpleDateFormat getDateFormat() {
        if (!getFieldType().equals(FieldType.DATE)) {
            return null;
        }
        return new SimpleDateFormat(getAttribute(IFieldAttrs.FORMAT, CALENDAR_DATE_PATTERN));
    }

    default Collection<JsonFieldValue> getSelectorValues() {
        if (getSelectorItems() == null) {
            return null;
        }
        return getSelectorItems()
            .stream()
            .map(v -> new JsonFieldValue(v.getValue(), v.getTitle()))
            .collect(Collectors.toList());
    }

    default boolean isVisible() {
        return getBoolAttribute(IFieldAttrs.VISIBLE, true);
    }
}
