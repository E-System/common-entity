package com.es.lib.entity;

import com.es.lib.common.DateUtil;
import com.es.lib.common.collection.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface IAttributeOwner {

    String COLLECTION_SPLITTER = ";";


    Map<String, String> getAttributes();

    void setAttributes(Map<String, String> attributes);

    default String getAttribute(String code) {
        Map<String, String> attributes = getAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.get(code);
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
        return getDateAttribute(code, DateUtil.CALENDAR_DATE_PATTERN);
    }

    default Date getDateAttribute(String code, String format) {
        try {
            return DateUtil.parse(getAttribute(code), format);
        } catch (ParseException e) {
            return null;
        }
    }

    default boolean getBoolAttr(String code) {
        return getBoolAttr(code, false);
    }

    default boolean getBoolAttr(String code, boolean defValue) {
        String attribute = getAttribute(code);
        if (attribute == null) {
            return defValue;
        }
        return Boolean.parseBoolean(attribute);
    }

    default void setAttributes(Collection<? extends Map.Entry<String, String>> items) {
        CollectionUtil.updateValues(this::getAttributes, this::setAttributes, items);
    }

    default void setAttribute(String code, String value) {
        setAttributes(Collections.singletonList(Pair.of(code, value)));
    }

    default void setAttribute(String code, boolean value) {
        setAttributes(Collections.singletonList(Pair.of(code, value ? String.valueOf(true) : null)));
    }

    default boolean isAttributeFilled(String code) {
        return StringUtils.isNotBlank(getAttribute(code));
    }

    default void removeEmptyAttributes() {
        setAttributes(CollectionUtil.removeEmptyValues(getAttributes()));
    }

    default void removeNullAttributes() {
        setAttributes(CollectionUtil.removeNullValues(getAttributes()));
    }

    default <T> Collection<T> getCollectionAttr(String code, String splitter, Function<String, T> mapper) {
        String value = getAttribute(code);
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
        if (CollectionUtil.isEmpty(items)) {
            setAttribute(code, null);
            return;
        }
        setAttribute(code, items.stream().map(String::valueOf).collect(Collectors.joining(splitter)));
    }

    default <T> void setCollectionAttr(String code, Collection<T> items) {
        setCollectionAttr(code, COLLECTION_SPLITTER, items);
    }
}
