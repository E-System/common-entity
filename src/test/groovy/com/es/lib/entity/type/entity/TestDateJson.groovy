package com.es.lib.entity.type.entity

import com.es.lib.entity.type.JsonbType
import com.fasterxml.jackson.annotation.JsonInclude

import java.time.OffsetDateTime
import java.time.ZonedDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
class TestDateJson {

    static class UserType extends JsonbType {

        @Override
        Class returnedClass() {
            return TestDateJson.class
        }
    }

    String code
    OffsetDateTime offsetDateTime
    ZonedDateTime zonedDateTime
    int val

    TestDateJson() {
    }

    TestDateJson(String code, OffsetDateTime offsetDateTime, ZonedDateTime zonedDateTime, int val) {
        this.code = code
        this.offsetDateTime = offsetDateTime
        this.zonedDateTime = zonedDateTime
        this.val = val
    }
}
