/*
 * Copyright 2020 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.es.lib.entity.iface.field;

import com.es.lib.entity.iface.IAttrsOwner;
import com.es.lib.entity.model.field.code.FieldType;
import com.es.lib.entity.model.field.code.IFieldAttrs;
import com.es.lib.entity.model.field.json.JsonFieldValue;
import com.es.lib.entity.model.field.json.JsonSelectorItems;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.es.lib.entity.model.field.json.JsonField.CALENDAR_DATE_PATTERN;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 08.04.19
 */
public interface IField extends IAttrsOwner {

    FieldType getType();

    void setType(FieldType fieldTypeCode);

    default String getSection() { return null; }

    String getCode();

    String getName();

    void setName(String name);

    default int getSorting() {
        return 0;
    }

    default Long getOwnerId() {
        return null;
    }

    default void setSelectorItems(JsonSelectorItems selectorItems) { }

    default JsonSelectorItems getSelectorItems() {
        return new JsonSelectorItems();
    }

    default String getSelectorRequest() {
        return null;
    }

    default SimpleDateFormat getDateFormat() {
        if (!getType().equals(FieldType.DATE)) {
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

    default boolean isParentOf(IField v) {
        return Objects.equals(getOwnerId(), v.getOwnerId()) && getCode().equals(v.getAttribute(IFieldAttrs.PARENT));
    }

    default boolean isRoot() {
        return getAttribute(IFieldAttrs.PARENT) == null;
    }
}
