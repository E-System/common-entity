package com.es.lib.entity.localization;

import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 20.10.2017
 */
public interface ILocalizedAttributes {

    String ATTRIBUTES_PREFIX = "attributes.";

    Map<String, String> getAttributes();

    LocalizedJson getLocalization();

    default String getAttributesValue(String code, String locale) {
        return getLocalization() == null ? getAttributes().get(code) : getLocalization().getValue(ATTRIBUTES_PREFIX + code, locale, () -> getAttributes().get(code));
    }
}
