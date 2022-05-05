package com.es.lib.entity.dialect;

public class ArrayOperatorContained extends ArrayOperator {

    public static final String NAME = "es_array_contained";

    public ArrayOperatorContained() {
        super("<@");
    }
}
