package com.es.lib.entity.localization;

import com.es.lib.entity.IAttributeOwner;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 20.10.2017
 */
public interface ILocalizedAttributes extends IAttributeOwner {

    String ATTRIBUTES_PREFIX = "attributes.";

    LocalizedJson getLocalization();

    default String getAttributesValue(String code, String locale) {
        return getLocalization() == null ? getAttribute(code) : getLocalization().getValue(ATTRIBUTES_PREFIX + code, locale, () -> getAttribute(code));
    }
}
