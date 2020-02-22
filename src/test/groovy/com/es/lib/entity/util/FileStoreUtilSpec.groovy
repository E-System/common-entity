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

import com.es.lib.entity.model.file.StoreMode
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths
import java.util.function.Supplier

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

    def "Create file in file store"() {
        when:
        def basePath = Paths.get('/tmp')
        def crc32 = 1231231
        def data = '1231231'
        def fileName = 'fileName'
        def fileExt = 'txt'
        def mime = 'text/plain'
        def result = FileStoreUtil.toStore(basePath, crc32, data.length(), fileName, fileExt, mime, data.bytes, new Supplier<FileStore>() {
            @Override
            FileStore get() {
                return new FileStore()
            }
        }, null)
        def path = Paths.get(basePath.toString(), result.filePath)
        then:
        result != null
        result.fileName == fileName
        result.fileExt == fileExt
        result.mime == mime
        result.size == data.length()
        result.crc32 == crc32
        result.filePath.endsWith(fileExt)
        Files.exists(path)
        Files.isReadable(path)
        new String(Files.readAllBytes(path)) == data
    }

    /* def "Create temporary file with name"() {
         when:
         def basePath = '/tmp'
         def crc32 = 1231231
         def data = '1231231'
         def fileExt = 'txt'
         def mime = 'raw/text'
         def result = FileStoreUtil.createTemporary(basePath, data.bytes, fileExt, FileStoreMode.TEMPORARY, null)
         def path = Paths.get(basePath, result.path)
         then:
         result != null
         result.ext == fileExt
         result.mime == mime
         result.size == data.length()
         result.crc32 == crc32
         Files.exists(path)
         Files.isReadable(path)
         new String(Files.readAllBytes(path)) == data
     }*/

    def "Create temporary file"() {
        when:
        def basePath = Paths.get('/tmp')
        def crc32 = 4136033880
        def data = '1231231'
        def fileExt = 'txt'
        def result = FileStoreUtil.createTemporary(basePath, data.bytes, fileExt, StoreMode.TEMPORARY, null)
        def path = Paths.get(basePath.toString(), result.filePath)
        then:
        result != null
        result.fileExt == fileExt
        result.mime == 'text/plain'
        result.size == data.length()
        result.crc32 == crc32
        Files.exists(path)
        Files.isReadable(path)
        new String(Files.readAllBytes(path)) == data
    }

    def "Create file in file store from temporary"() {
        when:
        def basePath = Paths.get('/tmp')
        def crc32 = 4136033880
        def data = '1231231'
        def fileExt = 'txt'
        def temporary = FileStoreUtil.createTemporary(basePath, data.bytes, fileExt, StoreMode.TEMPORARY, null)
        def result = FileStoreUtil.toStore(basePath, temporary, new Supplier<FileStore>() {
            @Override
            FileStore get() {
                return new FileStore()
            }
        }, null)
        def temporaryPath = Paths.get(basePath.toString(), temporary.filePath)
        def path = Paths.get(basePath.toString(), result.filePath)
        then:
        result != null
        result.fileExt == fileExt
        result.mime == 'text/plain'
        result.size == data.length()
        result.crc32 == crc32
        result.filePath.endsWith(fileExt)
        Files.exists(path)
        Files.isReadable(path)
        new String(Files.readAllBytes(path)) == data
        !Files.exists(temporaryPath)
    }
}
