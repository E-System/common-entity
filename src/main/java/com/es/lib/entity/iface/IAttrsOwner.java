package com.es.lib.entity.iface;

import com.es.lib.common.collection.CollectionUtil;
import com.es.lib.common.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

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
        return getDateAttribute(code, DateUtil.CALENDAR_DATE_PATTERN);
    }

    default Date getDateAttribute(String code, String format) {
        try {
            return DateUtil.parse(getAttribute(code), format);
        } catch (ParseException e) {
            return null;
        }
    }

    default void setAttributes(Collection<? extends Map.Entry<String, String>> items) {
        CollectionUtil.updateValues(this::getAttributes, this::setAttributes, items);
    }

    default void setAttribute(String code, String value) {
        setAttributes(Collections.singletonList(Pair.of(code, value)));
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
}
