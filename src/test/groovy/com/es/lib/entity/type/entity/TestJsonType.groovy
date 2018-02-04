package com.es.lib.entity.type.entity

import com.es.lib.entity.type.JsonbType

class TestJsonType extends JsonbType {

    @Override
    Class returnedClass() {
        return TestJson.class
    }
}
