package com.es.lib.entity


import spock.lang.Specification

class IAttributeOwnerSpec extends Specification {

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
