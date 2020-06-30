/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.entity.query;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Attributes for select data
 *
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 20.04.15
 */
@Getter
@ToString
@RequiredArgsConstructor
public class SelectAttributes {

    private final QueryContext selectQuery;
    private final QueryContext countQuery;
    private final Joins joins;
    private final Conditions conditions;
    private final Orders orders;
}