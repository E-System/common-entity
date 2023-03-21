package com.es.lib.entity.model.file

import spock.lang.Specification

class StoreRequestSpec extends Specification {

    def "IsValidForFileStore"() {
        expect:
        StoreRequest.create("1", false, null).validForFileStore
        StoreRequest.create("dGVzdC9maWxlcy50eHQ=", false, null).validForFileStore
        !StoreRequest.create("icon/asd.jpg", false, null).validForFileStore
    }
}
