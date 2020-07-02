package com.es.lib.entity.model

import spock.lang.Specification

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 05.03.2018
 */
class SPValueSpec extends Specification {

    def "String"() {
        when:
        def item = new SPValue(ValueTypeCode.STRING, 'Hello')
        then:
        item.type == ValueTypeCode.STRING
        item.string == 'Hello'
        item.value == 'Hello'
        item.price == null
        item.numeric == null
        item.bool == null
        item.json == null
        item.property == null
    }

    def "Price"() {
        when:
        def item = new SPValue(ValueTypeCode.PRICE, '100')
        then:
        item.type == ValueTypeCode.PRICE
        item.string == null
        item.value == '100'
        item.price == '100'
        item.numeric == null
        item.bool == null
        item.json == null
        item.property == null
    }

    def "Price as Long"() {
        when:
        def item = new SPValue(ValueTypeCode.PRICE, '100')
        then:
        item.type == ValueTypeCode.PRICE
        item.string == null
        item.value == '100'
        item.price == '100'
        item.priceAsLong == 100L
        item.numeric == null
        item.bool == null
        item.json == null
        item.property == null
    }

    def "Numeric"() {
        when:
        def item = new SPValue(ValueTypeCode.NUMERIC, '100')
        then:
        item.type == ValueTypeCode.NUMERIC
        item.string == null
        item.value == '100'
        item.price == null
        item.numeric == 100
        item.bool == null
        item.json == null
        item.property == null
    }

    def "Boolean"() {
        when:
        def item = new SPValue(ValueTypeCode.BOOLEAN, 'true')
        then:
        item.type == ValueTypeCode.BOOLEAN
        item.string == null
        item.value == 'true'
        item.price == null
        item.numeric == null
        item.bool
        item.json == null
        item.property == null
    }

    def "Json"() {
        when:
        def item = new SPValue(ValueTypeCode.JSON, '{"asd":"asd"}')
        then:
        item.type == ValueTypeCode.JSON
        item.string == null
        item.value == '{\n  "asd" : "asd"\n}'
        item.price == null
        item.numeric == null
        item.bool == null
        item.json == '{"asd":"asd"}'
        item.property == null
    }

    def "Property with one row"() {
        when:
        def item = new SPValue(ValueTypeCode.PROPERTY, 'key=value')
        then:
        item.type == ValueTypeCode.PROPERTY
        item.string == null
        item.value == 'key=value'
        item.price == null
        item.numeric == null
        item.bool == null
        item.json == null
        item.property.size() == 1
        item.property[0].key == 'key'
        item.property[0].value == 'value'
    }

    def "Property with two row"() {
        when:
        def item = new SPValue(ValueTypeCode.PROPERTY, 'key1=value1\nkey2=value2')
        then:
        item.type == ValueTypeCode.PROPERTY
        item.string == null
        item.value == 'key1=value1\nkey2=value2'
        item.price == null
        item.numeric == null
        item.bool == null
        item.json == null
        item.property.size() == 2
        item.property[0].key == 'key1'
        item.property[0].value == 'value1'
        item.property[1].key == 'key2'
        item.property[1].value == 'value2'
    }
}
