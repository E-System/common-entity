/*
 * Copyright (c) E-System LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2022
 */

package com.es.lib.entity.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.persistence.AttributeConverter;
import java.util.function.Function;

/**
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 11.03.2022
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ValueEnumConverter<E extends Enum<E> & ValueEnum<T>, T> implements AttributeConverter<E, T> {

    private final Function<T, E> converter;

    @Override
    public T convertToDatabaseColumn(E value) {return value == null ? null : value.getValue();}

    @Override
    public E convertToEntityAttribute(T code) {return code == null ? null : converter.apply(code);}

}
