package com.es.lib.entity.type.entity

import com.es.lib.entity.type.JsonbType
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class TestJson {

    static class UserType extends JsonbType {

        @Override
        Class returnedClass() {
            return TestJson.class
        }
    }

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

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        TestJson testJson = (TestJson) o

        if (attrs != testJson.attrs) return false
        if (code != testJson.code) return false

        return true
    }

    int hashCode() {
        int result
        result = (code != null ? code.hashCode() : 0)
        result = 31 * result + (attrs != null ? attrs.hashCode() : 0)
        return result
    }
}
