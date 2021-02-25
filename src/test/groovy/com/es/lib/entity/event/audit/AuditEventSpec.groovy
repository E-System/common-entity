package com.es.lib.entity.event.audit

import com.es.lib.entity.model.audit.code.IAuditActionCode
import spock.lang.Specification

class AuditEventSpec extends Specification {

    def "Builder with initiator"(){
        when:
        def initiator = "INITIATOR_ID"
        def event = AuditEvent.create(IAuditActionCode.CHANGE_PERMISSION, initiator).build()
        then:
        event.action == IAuditActionCode.CHANGE_PERMISSION
        event.initiator == initiator
        event.title == null
        event.value == null
        event.valueType == null
    }

    def "Builder without initiator"(){
        when:
        def event = AuditEvent.create(IAuditActionCode.CHANGE_PERMISSION).build()
        then:
        event.action == IAuditActionCode.CHANGE_PERMISSION
        event.initiator == null
        event.title == null
        event.value == null
        event.valueType == null
    }

    def "Builder with title"(){
        when:
        def event = AuditEvent.create(IAuditActionCode.CHANGE_PERMISSION).title("TITLE").build()
        then:
        event.action == IAuditActionCode.CHANGE_PERMISSION
        event.initiator == null
        event.title == "TITLE"
        event.value == null
        event.valueType == null
    }

    def "Builder with title and value"(){
        when:
        def event = AuditEvent.create(IAuditActionCode.CHANGE_PERMISSION).title("TITLE").value("VALUE_TYPE", "VALUE").build()
        then:
        event.action == IAuditActionCode.CHANGE_PERMISSION
        event.initiator == null
        event.title == "TITLE"
        event.value == "VALUE"
        event.valueType == "VALUE_TYPE"
    }
}
