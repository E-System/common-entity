package com.es.lib.entity

import spock.lang.Specification

class IPrimaryKeySpec extends Specification {

    class Pk1 implements IPrimaryKey<Long> {
        Long id

        Pk1(Long id) {
            this.id = id
        }
    }

    def "Get null or id"() {
        expect:
        IPrimaryKey.getNullOrId(new Pk1(1)) == 1
    }


    def "Is managed"() {
        expect:
        IPrimaryKey.isManaged(new Pk1(1))
        !IPrimaryKey.isManaged(new Pk1(null))
    }
}
