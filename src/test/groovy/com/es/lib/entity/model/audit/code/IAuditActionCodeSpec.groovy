package com.es.lib.entity.model.audit.code

import spock.lang.Specification

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
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
                  IAuditActionCode.LOGOUT,
                  IAuditActionCode.CHANGE_PASSWORD,
                  IAuditActionCode.CHANGE_PERMISSION
        ]
    }
}
