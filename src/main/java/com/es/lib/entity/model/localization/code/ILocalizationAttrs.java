package com.es.lib.entity.model.localization.code;

import com.es.lib.entity.iface.IAttrsOwner;
import com.es.lib.entity.model.localization.JsonLocalization;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 20.10.2017
 */
public interface ILocalizationAttrs extends IAttrsOwner {

    String ATTRIBUTES_PREFIX = "attributes.";

    JsonLocalization getLocalization();

    default String getAttributesValue(String code, String locale) {
        return getLocalization() == null ? getAttribute(code) : getLocalization().getValue(ATTRIBUTES_PREFIX + code, locale, () -> getAttribute(code));
    }
}
