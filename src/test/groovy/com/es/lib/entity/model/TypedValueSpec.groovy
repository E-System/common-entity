package com.es.lib.entity.model

import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 05.03.2018
 */
class TypedValueSpec extends Specification {

    def "GetType and GetValue"() {
        when:
        def item = new TypedValue(ValueTypeCode.STRING, "Hello")
        then:
        item.type == ValueTypeCode.STRING
        item.value == 'Hello'
    }
}
