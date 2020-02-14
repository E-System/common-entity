package com.es.lib.entity.util

import com.es.lib.entity.IPrimaryKey
import spock.lang.Specification

class EntityUtilSpec extends Specification {

    class Pk1 implements IPrimaryKey<Long> {
        Long id

        Pk1(Long id) {
            this.id = id
        }
    }

    def "Get id"() {
        expect:
        EntityUtil.id(new Pk1(1)) == 1
    }


    def "Id exist"() {
        expect:
        EntityUtil.idExist(new Pk1(1))
        !EntityUtil.idExist(new Pk1(null))
    }

    def "Get ids"() {
        expect:
        EntityUtil.id([new Pk1(1), new Pk1(2)]).containsAll([1L, 2L])
    }
}
