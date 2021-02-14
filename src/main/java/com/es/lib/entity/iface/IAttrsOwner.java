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
package com.es.lib.entity.iface;

import com.es.lib.common.Constant;
import com.es.lib.common.collection.Items;
import com.es.lib.common.date.Dates;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface IAttrsOwner {

    Map<String, String> getAttributes();

    void setAttributes(Map<String, String> attributes);

    default String getAttribute(String code) {
        return getAttribute(code, (String) null);
    }

    default String getAttribute(String code, String defValue) {
        Map<String, String> attributes = getAttributes();
        if (attributes == null) {
            return defValue;
        }
        return attributes.getOrDefault(code, defValue);
    }

    default boolean getBoolAttribute(String code) {
        return getBoolAttribute(code, false);
    }

    default boolean getBoolAttribute(String code, boolean defValue) {
        String attribute = getAttribute(code);
        if (attribute == null) {
            return defValue;
        }
        return Boolean.parseBoolean(attribute);
    }

    default Short getShortAttribute(String code) {
        return getNumberAttribute(code, Short::parseShort);
    }

    default Integer getIntAttribute(String code) {
        return getNumberAttribute(code, Integer::parseInt);
    }

    default Long getLongAttribute(String code) {
        return getNumberAttribute(code, Long::parseLong);
    }

    default Double getDoubleAttribute(String code) {
        return getNumberAttribute(code, Double::parseDouble);
    }

    default <T> T getNumberAttribute(String code, Function<String, T> converter) {
        try {
            return converter.apply(getAttribute(code));
        } catch (NumberFormatException e) {
            return null;
        }
    }


    default <T extends Enum<T>> T getAttribute(String code, Class<T> enumClass) {
        String value = getAttribute(code);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return T.valueOf(enumClass, value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    default Date getDateAttribute(String code) {
        return getDateAttribute(code, Constant.DEFAULT_DATE_PATTERN);
    }

    default Date getDateAttribute(String code, String format) {
        try {
            return Dates.parser().parse(getAttribute(code), format);
        } catch (ParseException e) {
            return null;
        }
    }

    default void setAttributes(Collection<? extends Map.Entry<String, String>> items) {
        Items.updateValues(this::getAttributes, this::setAttributes, items);
    }

    default void setAttribute(String code, String value) {
        setAttributes(Collections.singletonList(Pair.of(code, value)));
    }

    default boolean isAttributeFilled(String code) {
        return StringUtils.isNotBlank(getAttribute(code));
    }

    default void removeEmptyAttributes() {
        setAttributes(Items.removeEmptyValues(getAttributes()));
    }

    default void removeNullAttributes() {
        setAttributes(Items.removeNullValues(getAttributes()));
    }

    default <T> Collection<T> getCollectionAttribute(String code, String splitter, Function<String, T> mapper) {
        String value = getAttribute(code);
        if (StringUtils.isBlank(value)) {
            return new ArrayList<>();
        }
        return Stream.of(value.split(splitter)).map(mapper).collect(Collectors.toList());
    }

    default <T> Collection<T> getCollectionAttribute(String code, Function<String, T> mapper) {
        return getCollectionAttribute(code, ";", mapper);
    }

    default <T> void setCollectionAttribute(String code, String splitter, Collection<T> items) {
        if (Items.isEmpty(items)) {
            setAttribute(code, null);
            return;
        }
        setAttribute(code, items.stream().map(String::valueOf).collect(Collectors.joining(splitter)));
    }

    default <T> void setCollectionAttribute(String code, Collection<T> items) {
        setCollectionAttribute(code, ";", items);
    }
}
