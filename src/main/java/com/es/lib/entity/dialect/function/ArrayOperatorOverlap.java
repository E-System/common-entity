package com.es.lib.entity.dialect.function;

public class ArrayOperatorOverlap extends ArrayOperator {

    public static final String NAME = "es_array_overlap";

    public ArrayOperatorOverlap() {
        super("&&");
    }
}