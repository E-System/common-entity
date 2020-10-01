package com.es.lib.entity.iface.file


import spock.lang.Specification

class IStoreSpec extends Specification {

    def "IsMime"() {
        expect:
        IStore.isMime(mime, part) == result
        where:
        mime                | part    || result
        null                | null     | false
        ""                  | ""       | true
        "application/image" | "image"  | true
        "application/image" | "image2" | false
    }

    def "IsImage"() {
        expect:
        IStore.isImage("image/png")
        !IStore.isImage("octet/stream")
    }
}
