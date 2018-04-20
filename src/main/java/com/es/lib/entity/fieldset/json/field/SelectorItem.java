/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.entity.fieldset.json.field;

import java.io.Serializable;

/**
 * Selector element
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 30.05.16
 */
public class SelectorItem implements Serializable, Comparable<SelectorItem> {

    private int sorting;
    private String value;
    private String title;

    public SelectorItem() { }

    public SelectorItem(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public SelectorItem(int sorting, String value, String title) {
        this.sorting = sorting;
        this.value = value;
        this.title = title;
    }

    public int getSorting() {
        return sorting;
    }

    public void setSorting(int sorting) {
        this.sorting = sorting;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SelectorItem{" +
               "sorting=" + sorting +
               ", value='" + value + '\'' +
               ", title='" + title + '\'' +
               '}';
    }

    @Override
    public int compareTo(SelectorItem o) {
        int v = Integer.compare(sorting, o.sorting);
        if (v != 0) return v;
        return String.CASE_INSENSITIVE_ORDER.compare(title, o.title);
    }
}
