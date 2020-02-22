package com.es.lib.entity.model.file


import spock.lang.Specification

class StorePathSpec extends Specification {

    def "Build relative path"() {
        when:
        def path = StorePath.relative("prefix", "name", "ext")
        then:
        path.startsWith("/prefix")
        path.endsWith("name.ext")
        path.absolute
    }

    def "Build relative path with null prefix"() {
        when:
        def path = StorePath.relative(null, "name", "ext")
        then:
        !path.startsWith("/prefix")
        path.endsWith("name.ext")
        path.absolute
    }
}
