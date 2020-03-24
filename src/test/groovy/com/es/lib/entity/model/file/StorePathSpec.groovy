package com.es.lib.entity.model.file


import spock.lang.Specification

class StorePathSpec extends Specification {

    def "Build relative path"() {
        when:
        def path = StorePath.relative("prefix", null, "name", "ext")
        println path
        then:
        path.startsWith("/prefix")
        !path.toString().contains("/null/")
        path.endsWith("name.ext")
        path.absolute
    }

    def "Build relative path with scope"() {
        when:
        def path = StorePath.relative("prefix", "null", "name", "ext")
        println path
        then:
        path.startsWith("/prefix")
        path.toString().contains("/null/")
        path.endsWith("name.ext")
        path.absolute
    }

    def "Build relative path with null prefix"() {
        when:
        def path = StorePath.relative(null, null, "name", "ext")
        println path
        then:
        !path.startsWith("/prefix")
        !path.toString().contains("/null/")
        path.endsWith("name.ext")
        path.absolute
    }

    def "Build relative path with null prefix with scope"() {
        when:
        def path = StorePath.relative(null, "null", "name", "ext")
        println path
        then:
        !path.startsWith("/prefix")
        path.toString().contains("/null/")
        path.endsWith("name.ext")
        path.absolute
    }
}
