package com.es.lib.entity;

import com.es.lib.common.collection.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public interface IAttributeOwner {

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
