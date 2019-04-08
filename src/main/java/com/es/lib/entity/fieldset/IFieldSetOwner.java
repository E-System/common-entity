/*
 * Copyright (c) E-System LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2018
 */

package com.es.lib.entity.fieldset;

import com.es.lib.entity.fieldset.code.FieldTypeCode;
import com.es.lib.entity.fieldset.json.SelectorItemsJson;

import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 08.04.19
 */
public interface IFieldSetOwner {

    String getCode();

    Long getOwnerId();

    int getSorting();

    String getName();

    void setName(String name);

    FieldTypeCode getFieldType();

    void setFieldType(FieldTypeCode fieldTypeCode);

    Map<String, String> getAttributes();

    void setAttributes(Map<String, String> attributes);

    SelectorItemsJson getSelector();
}
