package com.es.lib.entity.query

import spock.lang.Specification

import static QueryEsl.*

class OrdersSpec extends Specification {

    def "Orders is empty after construction"() {
        expect:
        new Orders().empty
    }

    def "Asc and desc with nulls first"() {
        when:
        def orders = new Orders(
            asc("a"),
            desc("b", false)
        )
        then:
        orders.format() == "a asc, b desc nulls first"
    }

    def "Asc and desc"() {
        when:
        def orders = new Orders(
            asc("a"),
            desc("b")
        )
        then:
        orders.format() == "a asc, b desc"
    }

    def "Asc and desc with all nulls last"() {
        when:
        def orders = new Orders(
            asc("a", true),
            desc("b", true)
        )
        then:
        orders.format() == "a asc nulls last, b desc nulls last"
    }
}
