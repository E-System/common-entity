package com.es.lib.entity.model.file.output

import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 12.04.2018
 */
class OutputDataSpec extends Specification {

    @Shared
    def fileName = 'fileName'
    @Shared
    def contentType = 'contentType'

    def "Create with file"() {
        when:
        def data = OutputData.create(fileName, new File(fileName))
        then:
        data instanceof OutputFileData
        !data.stream
        !data.bytes
        data.fileName == fileName
        ((OutputFileData) data).file.name == fileName
    }

    def "Create with stream"() {
        when:
        def data = OutputData.create(fileName, contentType, new ByteArrayInputStream())
        then:
        data instanceof OutputStreamData
        data.isStream()
        !data.bytes
        data.fileName == fileName
        ((OutputStreamData) data).contentType == contentType
        ((OutputStreamData) data).getStream() != null
    }

    def "Create with bytes"() {
        when:
        def data = OutputData.create(fileName, contentType, 'Hello'.bytes)
        then:
        data instanceof OutputByteData
        !data.stream
        data.bytes
        data.fileName == fileName
        ((OutputByteData) data).contentType == contentType
        ((OutputByteData) data).bytes != null
    }
}
