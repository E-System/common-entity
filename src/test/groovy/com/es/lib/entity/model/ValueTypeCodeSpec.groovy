package com.es.lib.entity.model

import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 05.03.2018
 */
class ValueTypeCodeSpec extends Specification {

    def "IsStringValue"() {
        expect:
        ValueTypeCode.STRING.stringValue
        !ValueTypeCode.STRING.priceValue
        !ValueTypeCode.STRING.numericValue
        !ValueTypeCode.STRING.booleanValue
        !ValueTypeCode.STRING.jsonValue
        !ValueTypeCode.STRING.propertyValue
    }

    def "IsNumericValue"() {
        expect:
        !ValueTypeCode.NUMERIC.stringValue
        !ValueTypeCode.NUMERIC.priceValue
        ValueTypeCode.NUMERIC.numericValue
        !ValueTypeCode.NUMERIC.booleanValue
        !ValueTypeCode.NUMERIC.jsonValue
        !ValueTypeCode.NUMERIC.propertyValue
    }

    def "IsBooleanValue"() {
        expect:
        !ValueTypeCode.BOOLEAN.stringValue
        !ValueTypeCode.BOOLEAN.priceValue
        !ValueTypeCode.BOOLEAN.numericValue
        ValueTypeCode.BOOLEAN.booleanValue
        !ValueTypeCode.BOOLEAN.jsonValue
        !ValueTypeCode.BOOLEAN.propertyValue
    }

    def "IsJsonValue"() {
        expect:
        !ValueTypeCode.JSON.stringValue
        !ValueTypeCode.JSON.priceValue
        !ValueTypeCode.JSON.numericValue
        !ValueTypeCode.JSON.booleanValue
        ValueTypeCode.JSON.jsonValue
        !ValueTypeCode.JSON.propertyValue
    }

    def "IsPropertyValue"() {
        expect:
        !ValueTypeCode.PROPERTY.stringValue
        !ValueTypeCode.PROPERTY.priceValue
        !ValueTypeCode.PROPERTY.numericValue
        !ValueTypeCode.PROPERTY.booleanValue
        !ValueTypeCode.PROPERTY.jsonValue
        ValueTypeCode.PROPERTY.propertyValue
    }

    def "IsPriceValue"() {
        expect:
        !ValueTypeCode.PRICE.stringValue
        ValueTypeCode.PRICE.priceValue
        !ValueTypeCode.PRICE.numericValue
        !ValueTypeCode.PRICE.booleanValue
        !ValueTypeCode.PRICE.jsonValue
        !ValueTypeCode.PRICE.propertyValue
    }
}
