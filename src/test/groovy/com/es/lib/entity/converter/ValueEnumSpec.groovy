package com.es.lib.entity.converter

import spock.lang.Specification

class ValueEnumSpec extends Specification {

    def "From value"() {
        expect:
        ValueEnum.fromValue("value1", Test1.class) == Test1.VALUE1
        ValueEnum.fromValue("value2", Test1.class) == Test1.VALUE2
    }

    def "To label"() {
        expect:
        ValueEnum.toLabel(Test1.VALUE1) == 'Value1'
    }

    static enum Test1 implements ValueEnum<String> {
        VALUE1("value1"),
        VALUE2("value2")

        private String value

        Test1(String value) {
            this.value = value
        }

        @Override
        String getValue() {
            return value
        }
    }
}
