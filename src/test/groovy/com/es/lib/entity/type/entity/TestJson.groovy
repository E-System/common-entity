package com.es.lib.entity.type.entity

class TestJson {

    private String code
    private Map<String, Integer> attrs

    TestJson() {
    }

    TestJson(String code, Map<String, Integer> attrs) {
        this.code = code
        this.attrs = attrs
    }

    String getCode() {
        return code
    }

    void setCode(String code) {
        this.code = code
    }

    Map<String, Integer> getAttrs() {
        return attrs
    }

    void setAttrs(Map<String, Integer> attrs) {
        this.attrs = attrs
    }

}
