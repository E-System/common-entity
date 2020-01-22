package com.es.lib.entity

import spock.lang.Specification

import java.text.SimpleDateFormat

class IAttributeOwnerSpec extends Specification {

    def "Get date attribute"() {
        when:
        def v = new TestAttr(["CODE1": "22.12.2019", "CODE2": null, "CODE3": "", "CODE4": "awd"])
        def d1 = v.getDateAttribute("CODE1")
        def d2 = v.getDateAttribute("CODE2")
        def d3 = v.getDateAttribute("CODE3")
        def d4 = v.getDateAttribute("CODE4")
        then:
        d1 != null && new SimpleDateFormat("dd.MM.yyyy").format(d1) == "22.12.2019"
        d2 == null
        d3 == null
        d4 == null
    }

    def "Get enum attribute"() {
        when:
        def v = new TestAttr(["CODE": "VALUE", "CODE1": "", "CODE2": "VAL"])
        def exist = v.getAttribute("CODE", PCode)
        def notExist = v.getAttribute("CODE1", PCode)
        def notEnum = v.getAttribute("CODE2", PCode)
        then:
        exist == PCode.VALUE
        notExist == null
        notEnum == null
    }

    def "Remove empty attributes"() {
        when:
        def v = new TestAttr(["CODE": null, "CODE1": "", "CODE2": "VAL"])
        v.removeEmptyAttributes()
        then:
        v.attributes.size() == 1
        !v.isAttributeFilled("CODE")
        !v.isAttributeFilled("CODE1")
        v.isAttributeFilled("CODE2")
    }

    def "Remove null attributes"() {
        when:
        def v = new TestAttr(["CODE": null, "CODE1": "", "CODE2": "VAL"])
        v.removeNullAttributes()
        then:
        v.attributes.size() == 2
        !v.isAttributeFilled("CODE")
        v.getAttribute("CODE1") == ""
        v.isAttributeFilled("CODE2")
    }

    static enum PCode {
        VALUE
    }

    static class TestAttr implements IAttributeOwner {

        Map<String, String> attributes

        TestAttr(Map<String, String> attributes) {
            this.attributes = attributes
        }

        @Override
        Map<String, String> getAttributes() {
            return attributes
        }

        @Override
        void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes
        }
    }
}
