package com.es.lib.entity.iface

import spock.lang.Shared
import spock.lang.Specification

import java.text.SimpleDateFormat

class IAttrsOwnerSpec extends Specification {

    @Shared
    def entity = new TestAttr(['SHORT': '100', 'INT': '50000', 'LONG': '1231231231231231231', 'DOUBLE': '123.123', 'DATE1': '12.03.2018', 'DATE2': '2018-03-12', 'COLL': '1;2;3;4;5'])

    def "Get date attribute"() {
        when:
        def v = new TestAttr(["CODE1": "22.12.2019", "CODE2": null, "CODE3": "", "CODE4": "awd"])
        def d1 = v.getDateAttr("CODE1")
        def d2 = v.getDateAttr("CODE2")
        def d3 = v.getDateAttr("CODE3")
        def d4 = v.getDateAttr("CODE4")
        then:
        d1 != null && new SimpleDateFormat("dd.MM.yyyy").format(d1) == "22.12.2019"
        d2 == null
        d3 == null
        d4 == null
    }

    def "Get enum attribute"() {
        when:
        def v = new TestAttr(["CODE": "VALUE", "CODE1": "", "CODE2": "VAL"])
        def exist = v.getAttr("CODE", PCode)
        def notExist = v.getAttr("CODE1", PCode)
        def notEnum = v.getAttr("CODE2", PCode)
        then:
        exist == PCode.VALUE
        notExist == null
        notEnum == null
    }

    def "Remove empty attributes"() {
        when:
        def v = new TestAttr(["CODE": null, "CODE1": "", "CODE2": "VAL"])
        v.removeEmptyAttrs()
        then:
        v.attrs.size() == 1
        !v.isAttrFilled("CODE")
        !v.isAttrFilled("CODE1")
        v.isAttrFilled("CODE2")
    }

    def "Remove null attributes"() {
        when:
        def v = new TestAttr(["CODE": null, "CODE1": "", "CODE2": "VAL"])
        v.removeNullAttrs()
        then:
        v.attrs.size() == 2
        !v.isAttrFilled("CODE")
        v.getAttr("CODE1") == ""
        v.isAttrFilled("CODE2")
    }

    def "GetShort"() {
        expect:
        entity.getShortAttr('SHORT') == 100
        entity.getShortAttr('INT') == null
        entity.getShortAttr('LONG') == null
        entity.getShortAttr('DOUBLE') == null
        entity.getShortAttr('DATE1') == null
        entity.getShortAttr('DATE2') == null
    }

    def "GetInt"() {
        expect:
        entity.getIntAttr('SHORT') == 100
        entity.getIntAttr('INT') == 50000
        entity.getIntAttr('LONG') == null
        entity.getIntAttr('DOUBLE') == null
        entity.getIntAttr('DATE1') == null
        entity.getIntAttr('DATE2') == null
    }

    def "GetLong"() {
        expect:
        entity.getLongAttr('SHORT') == 100
        entity.getLongAttr('INT') == 50000
        entity.getLongAttr('LONG') == 1231231231231231231
        entity.getLongAttr('DOUBLE') == null
        entity.getLongAttr('DATE1') == null
        entity.getLongAttr('DATE2') == null
    }

    def "GetDouble"() {
        expect:
        entity.getDoubleAttr('SHORT') == 100
        entity.getDoubleAttr('INT') == 50000
        entity.getDoubleAttr('LONG') == 1231231231231231231
        entity.getDoubleAttr('DOUBLE') == 123.123
        entity.getDoubleAttr('DATE1') == null
        entity.getDoubleAttr('DATE2') == null
    }

    def "GetDate"() {
        expect:
        entity.getDateAttr('SHORT') == null
        entity.getDateAttr('INT') == null
        entity.getDateAttr('LONG') == null
        entity.getDateAttr('DOUBLE') == null
        entity.getDateAttr('DATE1').getDate() == 12
        entity.getDateAttr('DATE1').getMonth() == 2
        entity.getDateAttr('DATE1').getYear() == 118
        entity.getDateAttr('DATE2') == null
        entity.getDateAttr('DATE2', 'yyyy-MM-dd').getDate() == 12
        entity.getDateAttr('DATE2', 'yyyy-MM-dd').getMonth() == 2
        entity.getDateAttr('DATE2', 'yyyy-MM-dd').getYear() == 118
    }

    def "GetCollection"() {
        expect:
        entity.getCollectionAttr('COLL1', { Long.parseLong(it) }) == []
        entity.getCollectionAttr('COLL', { Long.parseLong(it) }) == [1L, 2L, 3L, 4L, 5L]
        entity.getCollectionAttr('COLL', { Integer.parseInt(it) }) == [1, 2, 3, 4, 5]
    }

    def "SetCollection"() {
        when:
        def entity = new TestAttr([:])
        entity.setCollectionAttr("COLL", [1L, 2L])
        then:
        entity.getCollectionAttr('COLL1', { Long.parseLong(it) }) == []
        entity.getCollectionAttr('COLL', { Long.parseLong(it) }) == [1L, 2L]
    }

    static enum PCode {
        VALUE
    }

    static class TestAttr implements IAttrsOwner {

        Map<String, String> attrs

        TestAttr(Map<String, String> attrs) {
            this.attrs = attrs
        }
    }
}
