package com.es.lib.entity;

import com.es.lib.common.collection.CollectionUtil;
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

    default void setAttributes(Collection<? extends Map.Entry<String, String>> items) {
        CollectionUtil.updateValues(this::getAttributes, this::setAttributes, items);
    }

    default void setAttribute(String code, String value) {
        setAttributes(Collections.singletonList(Pair.of(code, value)));
    }
}
