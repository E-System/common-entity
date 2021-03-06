package com.es.lib.entity.result

import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 05.03.2018
 */
class KeyValueSpec extends Specification {

    def "GetKey and GetValue"() {
        when:
        def item = new KeyValue("key", "value")
        then:
        item.key == 'key'
        item.value == 'value'
    }

    def "GetPropertyRow"() {
        given:
        def item = new KeyValue("key", "value")
        when:
        def res = item.propertyRow
        then:
        res == 'key=value'
    }
}
