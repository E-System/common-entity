/*
 * Copyright 2018 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.es.lib.entity.util

import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 17.03.2018
 */
class FileStoreUtilSpec extends Specification {

    def "IsMime"() {
        expect:
        FileStoreUtil.isMime(mime, part) == result
        where:
        mime                | part    || result
        null                | null     | false
        ""                  | ""       | true
        "application/image" | "image"  | true
        "application/image" | "image2" | false
    }

    def "IsImage"() {
        expect:
        FileStoreUtil.isImage("image/png")
        !FileStoreUtil.isImage("octet/stream")
    }

    def "GetPathPart"() {
        expect:
        FileStoreUtil.getPathPart("text") == File.separator + "text"
    }

    def "GetLocalPath"() {
        when:
        def path = FileStoreUtil.getLocalPath("prefix", null, "name", "ext")
        then:
        path.startsWith(FileStoreUtil.getPathPart("prefix"))
        !path.contains("/null/")
        path.endsWith("name.ext")
    }

    def "GetLocalPath with scope"() {
        when:
        def path = FileStoreUtil.getLocalPath("prefix", "null", "name", "ext")
        then:
        path.startsWith(FileStoreUtil.getPathPart("prefix"))
        path.contains("/null/")
        path.endsWith("name.ext")
    }

    def "ExtractFileParts"() {
        when:
        def result = FileStoreUtil.extractFileParts("asd.ext")
        then:
        result != null
        result.fileName == 'asd'
        result.ext == 'ext'
    }

    def "ExtractFileParts without extension"() {
        when:
        def result = FileStoreUtil.extractFileParts("asd")
        then:
        result != null
        result.fileName == 'asd'
        result.ext == ''
    }

}
