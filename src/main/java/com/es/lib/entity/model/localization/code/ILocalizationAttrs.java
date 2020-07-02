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
package com.es.lib.entity.model.localization.code;

import com.es.lib.entity.iface.IAttrsOwner;
import com.es.lib.entity.model.localization.JsonLocalization;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 20.10.2017
 */
public interface ILocalizationAttrs extends IAttrsOwner {

    String ATTRIBUTES_PREFIX = "attributes.";

    JsonLocalization getLocalization();

    default String getAttributesValue(String code, String locale) {
        return getLocalization() == null ? getAttribute(code) : getLocalization().getValue(ATTRIBUTES_PREFIX + code, locale, () -> getAttribute(code));
    }
}
