package com.es.lib.entity.model.security

import spock.lang.Specification

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 23.11.2017
 */
class PermissionRowSpec extends Specification {

    def "Equals with all fields"() {
        expect:
        pr1 != pr2
        where:
        pr1                              | pr2
        new PermissionItem(1, "T1", "A1") | new PermissionItem(1, "T2", "A2")
        new PermissionItem(1, "T1", "A")  | new PermissionItem(1, "T2", "A")
        new PermissionItem(1, "T", "A")   | new PermissionItem(2, "T", "A")
    }

    def "Equals same"() {
        expect:
        pr1 == pr2
        where:
        pr1                            | pr2
        new PermissionItem(1, "T", "A") | new PermissionItem(1, "T", "A")
    }
}
