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
package com.es.lib.entity

import com.es.lib.entity.model.file.StoreMode
import com.es.lib.entity.model.file.code.IFileStoreAttrs
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths
import java.util.function.Supplier

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 17.03.2018
 */
class FileStoresSpec extends Specification {

    def "Create file in file store"() {
        when:
        def basePath = Paths.get('/tmp')
        def crc32 = 1231231
        def data = '1231231'
        def fileName = 'fileName'
        def fileExt = 'txt'
        def mime = 'text/plain'
        def result = FileStores.toStore(basePath, null, crc32, data.length(), fileName, fileExt, mime, data.bytes, null, null, new Supplier<FileStore>() {
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
        result.checkers.isEmpty()
        Files.exists(path)
        Files.isReadable(path)
        new String(Files.readAllBytes(path)) == data
    }

    def "Download file in file store"() {
        when:
        def basePath = Paths.get('/tmp')
        def fileName = 'Right_Stopper_WheelAble_SolutionBased'
        def fileExt = 'jpg'
        def url = 'https://cdn.shopify.com/s/files/1/0277/7631/9588/products/Right_Stopper_WheelAble_SolutionBased.jpg?v=1594327034'
        def result = FileStores.toStore(basePath, null, url, null, null, new Supplier<FileStore>() {
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
        result.mime == 'image/jpeg'
        result.size == 72994
        result.crc32 == 2287021278
        result.filePath.endsWith(fileExt)
        result.checkers.isEmpty()
        Files.exists(path)
        Files.isReadable(path)
        !new String(Files.readAllBytes(path)).isEmpty()
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
        def result = FileStores.createTemporary(basePath, data.bytes, fileExt, StoreMode.TEMPORARY, null, null)
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

    def "Create temporary file from url"() {
        when:
        def basePath = Paths.get('/tmp')
        def fileName = 'Right_Stopper_WheelAble_SolutionBased'
        def fileExt = 'jpg'
        def url = 'https://cdn.shopify.com/s/files/1/0277/7631/9588/products/Right_Stopper_WheelAble_SolutionBased.jpg?v=1594327034'
        def result = FileStores.createTemporary(basePath, url, StoreMode.TEMPORARY, null,null)
        def path = Paths.get(basePath.toString(), result.filePath)
        then:
        result != null
        result.fileName == fileName
        result.fileExt == fileExt
        result.mime == 'image/jpeg'
        result.size == 72994
        result.crc32 == 2287021278
        result.filePath.endsWith(fileExt)
        Files.exists(path)
        Files.isReadable(path)
        !new String(Files.readAllBytes(path)).isEmpty()
    }

    def "Create file in file store from temporary"() {
        when:
        def basePath = Paths.get('/tmp')
        def crc32 = 4136033880
        def data = '1231231'
        def fileExt = 'txt'
        def temporary = FileStores.createTemporary(basePath, data.bytes, fileExt, StoreMode.TEMPORARY, null, null)
        def result = FileStores.toStore(basePath, null, temporary, [IFileStoreAttrs.Security.CHECKER_LOGGED_CODE] as HashSet, ["TAG1", "TAG2"] as HashSet, new Supplier<FileStore>() {
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
        !result.checkers.isEmpty()
        result.checkers == [IFileStoreAttrs.Security.CHECKER_LOGGED_CODE] as HashSet
        !result.tags.isEmpty()
        result.tags == ["TAG1", "TAG2"] as HashSet
        Files.exists(path)
        Files.isReadable(path)
        new String(Files.readAllBytes(path)) == data
        Files.exists(temporaryPath)
    }

    def "Create file in file store from temporary twice"() {
        when:
        def basePath = Paths.get('/tmp')
        def crc32 = 4136033880
        def data = '1231231'
        def fileExt = 'txt'
        def temporary = FileStores.createTemporary(basePath, data.bytes, fileExt, StoreMode.TEMPORARY, null, null)
        def result = FileStores.toStore(basePath, null, temporary, [IFileStoreAttrs.Security.CHECKER_LOGGED_CODE] as HashSet, null, new Supplier<FileStore>() {
            @Override
            FileStore get() {
                return new FileStore()
            }
        }, null)
        def result2 = FileStores.toStore(basePath, null, temporary, [IFileStoreAttrs.Security.CHECKER_LOGGED_CODE] as HashSet, null, new Supplier<FileStore>() {
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
        !result.checkers.isEmpty()
        result.checkers == [IFileStoreAttrs.Security.CHECKER_LOGGED_CODE] as HashSet
        result2 != null
        result2.fileExt == fileExt
        result2.mime == 'text/plain'
        result2.size == data.length()
        result2.crc32 == crc32
        result2.filePath.endsWith(fileExt)
        !result2.checkers.isEmpty()
        result2.checkers == [IFileStoreAttrs.Security.CHECKER_LOGGED_CODE] as HashSet
        Files.exists(path)
        Files.isReadable(path)
        new String(Files.readAllBytes(path)) == data
        Files.exists(temporaryPath)
    }
}
