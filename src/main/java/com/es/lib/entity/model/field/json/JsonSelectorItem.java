/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.entity.model.field.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Selector element metadata
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 30.05.16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonSelectorItem implements Serializable, Comparable<JsonSelectorItem> {

    private String value;
    private String title;
    private int sorting;

    public JsonSelectorItem(String value, String title) {
        this(value, title, 0);
    }

    @Override
    public int compareTo(JsonSelectorItem o) {
        int v = Integer.compare(sorting, o.sorting);
        if (v != 0) return v;
        return String.CASE_INSENSITIVE_ORDER.compare(title, o.title);
    }
}
