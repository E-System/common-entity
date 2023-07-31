package com.es.lib.entity.model.security.code

import com.es.lib.entity.model.security.PermissionItem
import spock.lang.Specification

class ISecurityActionSpec extends Specification {
    static TARGET = "TARGET"
    static ACTION = "ACTION"

    def "Join from row"() {
        expect:
        ISecurityAction.join(new PermissionItem(null, TARGET, ACTION)) == TARGET + ISecurityAction._JOIN_STRING + ACTION
        ISecurityAction.join(null) == null
    }

    def "Join from string"() {
        expect:
        ISecurityAction.join(TARGET, ACTION) == TARGET + ISecurityAction._JOIN_STRING + ACTION
        ISecurityAction.join(null, ACTION) == null
        ISecurityAction.join(TARGET, null) == null
    }

    def "Split without role"() {
        expect:
        ISecurityAction.split(TARGET + ISecurityAction._JOIN_STRING + ACTION) == new PermissionItem(null, TARGET, ACTION)
        ISecurityAction.split(null) == null
    }

    def "Split with role"() {
        expect:
        ISecurityAction.split(1, TARGET + ISecurityAction._JOIN_STRING + ACTION) == new PermissionItem(1, TARGET, ACTION)
        ISecurityAction.split(1, null) == null
    }
}
