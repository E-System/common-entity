package com.es.lib.entity.util

import com.es.lib.entity.iface.IPrimaryKey
import spock.lang.Specification

class PKeysSpec extends Specification {

    static class Pk1 implements IPrimaryKey<Long> {
        Long id

        Pk1(Long id) {
            this.id = id
        }
    }

    static class RealEntity extends Pk1 {
        String name

        RealEntity(Long id, String name) {
            super(id)
            this.name = name
        }
    }

    static class RealEntity2 extends Pk1 {
        String name
        boolean bl
        RealEntity realEntity

        RealEntity2(Long id, String name, boolean bl, RealEntity realEntity) {
            super(id)
            this.name = name
            this.bl = bl
            this.realEntity = realEntity
        }
    }

    def "Get id"() {
        expect:
        PKeys.id(new Pk1(1)) == 1
    }


    def "Id exist"() {
        expect:
        PKeys.idExist(new Pk1(1))
        !PKeys.idExist(new Pk1(null))
    }

    def "Get ids"() {
        expect:
        PKeys.id([new Pk1(1), new Pk1(2)]).containsAll([1L, 2L])
    }

    def "toMap"() {
        when:
        def res = PKeys.toMap(new RealEntity2(1, '1', true, new RealEntity(2, '2')))
        then:
        res['id'] == 1
        res['name'] == '1'
        res['bl'] == true
        res['realEntity'] == 2
    }

    def "toMap with exclude"() {
        when:
        def res = PKeys.toMap(new RealEntity2(1, '1', true, new RealEntity(2, '2')), ['name', 'bl'])
        then:
        res['id'] == 1
        res['name'] == null
        res['bl'] == null
        res['realEntity'] == 2
    }
}
