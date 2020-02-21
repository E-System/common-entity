/*
 * Copyright (c) E-System LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2018
 */

package com.es.lib.entity.fieldset;

import com.es.lib.entity.IAttributeOwner;
import com.es.lib.entity.fieldset.code.FieldTypeCode;
import com.es.lib.entity.fieldset.code.IFieldAttributes;
import com.es.lib.entity.fieldset.json.SelectorItemsJson;
import com.es.lib.entity.fieldset.json.field.JsonFieldValue;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.es.lib.entity.fieldset.json.field.JsonFieldMetadata.CALENDAR_DATE_PATTERN;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 08.04.19
 */
public interface IFieldSetOwner extends IAttributeOwner {

    FieldTypeCode getFieldType();

    void setFieldType(FieldTypeCode fieldTypeCode);

    default String getSectionCode() { return null; }

    String getCode();

    String getName();

    void setName(String name);

    int getSorting();

    Long getOwnerId();

    SelectorItemsJson getSelector();

    default SimpleDateFormat getDateFormat() {
        if (!getFieldType().equals(FieldTypeCode.DATE)) {
            return null;
        }
        return new SimpleDateFormat(getAttribute(IFieldAttributes.FORMAT, CALENDAR_DATE_PATTERN));
    }

    default Collection<JsonFieldValue> getSelectorValues() {
        if (getSelector() == null) {
            return null;
        }
        return getSelector()
            .stream()
            .map(v -> new JsonFieldValue(v.getValue(), v.getTitle()))
            .collect(Collectors.toList());
    }

    default boolean isVisible() {
        return getBoolAttribute(IFieldAttributes.VISIBLE, true);
    }
}
