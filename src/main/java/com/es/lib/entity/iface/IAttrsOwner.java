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

    String COLLECTION_SPLITTER = ";";

    Map<String, String> getAttrs();

    void setAttrs(Map<String, String> attrs);

    default String getAttr(String code) {
        return getAttr(code, (String) null);
    }

    default String getAttr(String code, String defValue) {
        Map<String, String> attributes = getAttrs();
        if (attributes == null) {
            return defValue;
        }
        return attributes.getOrDefault(code, defValue);
    }

    default boolean getBoolAttr(String code) {
        return getBoolAttr(code, false);
    }

    default boolean getBoolAttr(String code, boolean defValue) {
        String attribute = getAttr(code);
        if (attribute == null) {
            return defValue;
        }
        return Boolean.parseBoolean(attribute);
    }

    default Short getShortAttr(String code) {
        return getNumberAttr(code, Short::parseShort);
    }

    default Integer getIntAttr(String code) {
        return getNumberAttr(code, Integer::parseInt);
    }

    default Long getLongAttr(String code) {
        return getNumberAttr(code, Long::parseLong);
    }

    default Double getDoubleAttr(String code) {
        return getNumberAttr(code, Double::parseDouble);
    }

    default <T> T getNumberAttr(String code, Function<String, T> converter) {
        try {
            return converter.apply(getAttr(code));
        } catch (NumberFormatException e) {
            return null;
        }
    }


    default <T extends Enum<T>> T getAttr(String code, Class<T> enumClass) {
        String value = getAttr(code);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return T.valueOf(enumClass, value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    default Date getDateAttr(String code) {
        return getDateAttr(code, Constant.DEFAULT_DATE_PATTERN);
    }

    default Date getDateAttr(String code, String format) {
        try {
            return Dates.parser().parse(getAttr(code), format);
        } catch (ParseException e) {
            return null;
        }
    }

    default void setAttrs(Collection<? extends Map.Entry<String, String>> items) {
        Items.updateValues(this::getAttrs, this::setAttrs, items);
    }

    default void setAttr(String code, String value) {
        setAttrs(Collections.singletonList(Pair.of(code, value)));
    }

    default void setAttr(Map.Entry<String, String> entry) {
        setAttrs(Collections.singletonList(entry));
    }

    default boolean isAttrFilled(String code) {
        return StringUtils.isNotBlank(getAttr(code));
    }

    default void removeEmptyAttrs() {
        setAttrs(Items.removeEmptyValues(getAttrs()));
    }

    default void removeNullAttrs() {
        setAttrs(Items.removeNullValues(getAttrs()));
    }

    default <T> Collection<T> getCollectionAttr(String code, String splitter, Function<String, T> mapper) {
        String value = getAttr(code);
        if (StringUtils.isBlank(value)) {
            return new ArrayList<>();
        }
        return Stream.of(value.split(splitter)).map(mapper).collect(Collectors.toList());
    }

    default <T> Set<T> getSetAttr(String code, String splitter, Function<String, T> mapper) {
        return new HashSet<>(getCollectionAttr(code, splitter, mapper));
    }

    default <T> Collection<T> getCollectionAttr(String code, Function<String, T> mapper) {
        return getCollectionAttr(code, COLLECTION_SPLITTER, mapper);
    }

    default <T> Set<T> getSetAttr(String code, Function<String, T> mapper) {
        return new HashSet<>(getCollectionAttr(code, mapper));
    }

    default <T> void setCollectionAttr(String code, String splitter, Collection<T> items) {
        if (Items.isEmpty(items)) {
            setAttr(code, null);
            return;
        }
        setAttr(code, items.stream().map(String::valueOf).collect(Collectors.joining(splitter)));
    }

    default <T> void setCollectionAttr(String code, Collection<T> items) {
        setCollectionAttr(code, COLLECTION_SPLITTER, items);
    }
}
