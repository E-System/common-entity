package com.es.lib.entity.iface.audit

import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.03.2018
 */
class StringAuditInfoSpec extends Specification {

    def "ToString"() {
        expect:
        new StringAuditInfo('MESSAGE').toString() == 'MESSAGE'
    }
}
