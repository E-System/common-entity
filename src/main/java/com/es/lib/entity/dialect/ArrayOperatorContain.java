package com.es.lib.entity.dialect;

public class ArrayOperatorContain extends ArrayOperator {

    public static final String NAME = "es_array_contain";

    public ArrayOperatorContain() {
        super("@>");
    }
}
