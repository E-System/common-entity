/*
 * Copyright (c) E-System LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2022
 */

package com.es.lib.entity.converter;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 11.03.2022
 */
public interface ValueEnum<R> {

    R getValue();

    static <R, T extends Enum<T> & ValueEnum<R>> T fromValue(R value, Class<T> enumType) {
        return Arrays.stream(enumType.getEnumConstants())
                     .filter(v -> value.equals(v.getValue())).findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Value [" + value + "] not supported."));
    }

    static <T extends Enum<T>> String toLabel(T item) {
        return item == null ? null : StringUtils.capitalize(item.name().toLowerCase());
    }
}
