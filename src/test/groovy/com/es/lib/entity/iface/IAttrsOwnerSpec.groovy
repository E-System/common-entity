package com.es.lib.entity.iface

import com.es.lib.entity.iface.IAttrsOwner
import spock.lang.Shared
import spock.lang.Specification

import java.text.SimpleDateFormat

class IAttrsOwnerSpec extends Specification {

    @Shared
    def entity = new TestAttr(['SHORT': '100', 'INT': '50000', 'LONG': '1231231231231231231', 'DOUBLE': '123.123', 'DATE1': '12.03.2018', 'DATE2': '2018-03-12'])

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

    def "GetShort"() {
        expect:
        entity.getShortAttribute('SHORT') == 100
        entity.getShortAttribute('INT') == null
        entity.getShortAttribute('LONG') == null
        entity.getShortAttribute('DOUBLE') == null
        entity.getShortAttribute('DATE1') == null
        entity.getShortAttribute('DATE2') == null
    }

    def "GetInt"() {
        expect:
        entity.getIntAttribute('SHORT') == 100
        entity.getIntAttribute('INT') == 50000
        entity.getIntAttribute('LONG') == null
        entity.getIntAttribute('DOUBLE') == null
        entity.getIntAttribute('DATE1') == null
        entity.getIntAttribute('DATE2') == null
    }

    def "GetLong"() {
        expect:
        entity.getLongAttribute('SHORT') == 100
        entity.getLongAttribute('INT') == 50000
        entity.getLongAttribute('LONG') == 1231231231231231231
        entity.getLongAttribute('DOUBLE') == null
        entity.getLongAttribute('DATE1') == null
        entity.getLongAttribute('DATE2') == null
    }

    def "GetDouble"() {
        expect:
        entity.getDoubleAttribute('SHORT') == 100
        entity.getDoubleAttribute('INT') == 50000
        entity.getDoubleAttribute('LONG') == 1231231231231231231
        entity.getDoubleAttribute('DOUBLE') == 123.123
        entity.getDoubleAttribute('DATE1') == null
        entity.getDoubleAttribute('DATE2') == null
    }

    def "GetDate"() {
        expect:
        entity.getDateAttribute('SHORT') == null
        entity.getDateAttribute('INT') == null
        entity.getDateAttribute('LONG') == null
        entity.getDateAttribute('DOUBLE') == null
        entity.getDateAttribute('DATE1').getDate() == 12
        entity.getDateAttribute('DATE1').getMonth() == 2
        entity.getDateAttribute('DATE1').getYear() == 118
        entity.getDateAttribute('DATE2') == null
        entity.getDateAttribute('DATE2', 'yyyy-MM-dd').getDate() == 12
        entity.getDateAttribute('DATE2', 'yyyy-MM-dd').getMonth() == 2
        entity.getDateAttribute('DATE2', 'yyyy-MM-dd').getYear() == 118
    }

    static enum PCode {
        VALUE
    }

    static class TestAttr implements IAttrsOwner {

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
