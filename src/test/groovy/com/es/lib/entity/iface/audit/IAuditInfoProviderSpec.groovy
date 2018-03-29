package com.es.lib.entity.iface.audit

import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.03.2018
 */
class IAuditInfoProviderSpec extends Specification {

    class AuditInfoProviderImpl implements IAuditInfoProvider {

        @Override
        IAuditInfo getAuditInfo() {
            return stringAuditInfo('MESSAGE')
        }
    }

    def "StringAuditInfo"() {
        expect:
        new AuditInfoProviderImpl().auditInfo.toString() == 'MESSAGE'
    }
}
