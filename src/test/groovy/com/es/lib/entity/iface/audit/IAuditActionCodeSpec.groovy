package com.es.lib.entity.iface.audit

import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.03.2018
 */
class IAuditActionCodeSpec extends Specification {

    def "Filed name equals values"() {
        expect:
        IAuditActionCode.class.getField(value).get(null) == value
        where:
        value << [IAuditActionCode.INSERT,
                  IAuditActionCode.UPDATE,
                  IAuditActionCode.DELETE,
                  IAuditActionCode.LOGIN_SUCCESS,
                  IAuditActionCode.LOGIN_ERROR,
                  IAuditActionCode.LOGOUT
        ]
    }
}
