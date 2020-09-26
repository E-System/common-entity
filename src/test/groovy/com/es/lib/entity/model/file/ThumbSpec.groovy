package com.es.lib.entity.model.file

import spock.lang.Specification

class ThumbSpec extends Specification {

    def "Create with disabled"() {
        expect:
        Thumb.create(false, "1", "1", "1") == null
    }

    def "Create with invalid quality"() {
        when:
        def res = Thumb.create(true, "1", "1", "asd")
        then:
        res != null
        res.width == 1
        res.height == 1
        Float.isNaN(res.quality)
    }

    def "Create with valid quality"() {
        when:
        def res = Thumb.create(true, "1", "1", "0.5")
        then:
        res != null
        res.width == 1
        res.height == 1
        res.quality == 0.5f
    }

    def "Create with greater quality"() {
        when:
        def res = Thumb.create(true, "1", "1", "1.5")
        then:
        res != null
        res.width == 1
        res.height == 1
        res.quality == 1.0f
    }

    def "Create with lower quality"() {
        when:
        def res = Thumb.create(true, "asd", "1", "-0.1")
        then:
        res != null
        res.width == Thumb.DEFAULT_WIDTH
        res.height == 1
        res.quality == 0.0f
    }
}
