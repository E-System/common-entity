package com.es.lib.entity.model.audit

import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.03.2018
 */
class AuditInfoSpec extends Specification {

    def "Construct with title and value"() {
        when:
        def res = new AuditInfo('TITLE', 'VALUE')
        then:
        res.title == 'TITLE'
        res.value == 'VALUE'
    }

    def "Json audit"() {
        when:
        def res = new TestJsonAuditInfoProvider(1, 'NAME').getAuditInfo()
        then:
        res.title == 'TITLE'
        res.value == '{"id":1,"name":"NAME"}'
    }

    def "String audit"() {
        when:
        def res = new TestStringAuditInfoProvider(1, 'NAME').getAuditInfo()
        then:
        res.title == '{id=1, name=NAME}'
        res.value == null
    }

    class TestJsonAuditInfoProvider implements IAuditInfoProvider {
        Integer id
        String name

        TestJsonAuditInfoProvider(Integer id, String name) {
            this.id = id
            this.name = name
        }

        @Override
        IAuditInfo getAuditInfo() {
            return jsonAuditInfo('TITLE', this)
        }
    }

    class TestStringAuditInfoProvider implements IAuditInfoProvider {
        Integer id
        String name

        TestStringAuditInfoProvider(Integer id, String name) {
            this.id = id
            this.name = name
        }

        @Override
        IAuditInfo getAuditInfo() {
            return auditInfo('{id=' + id + ', name=' + name + '}')
        }
    }
}
