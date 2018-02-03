/*
 * Copyright 2016 E-System LLC
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

import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 20.07.15
 */
class HStoreUtilSpec extends Specification {

    def "Convert from Map<?,?> in hstore string"() {
        expect:
        HStoreUtil.toString(map as Map<?, ?>) == result
        where:
        map                            || result
        null                           || ""
        []                             || ""
        ["a": "a"]                     || '"a"=>"a"'
        ["a": null]                    || '"a"=>NULL'
        ["a": "a", "b": "b"]           || '"a"=>"a", "b"=>"b"'
        ["a": "a\"b", "b": "b\"a"]     || '"a"=>"a\\\\\"b", "b"=>"b\\\\\"a"'
        ["a": "a\"\\b", "b": "b\\\"a"] || '"a"=>"a\\\\\"\\\\b", "b"=>"b\\\\\\\\\"a"'
        ["a": "a\\b", "b": "b\\a"]     || '"a"=>"a\\\\b", "b"=>"b\\\\a"'
    }

    def "false with null and empty map"() {
        expect:
        HStoreUtil.isFlagEnabled(attributes, "code") == result
        where:
        attributes || result
        null       || false
        [:]        || false
    }

    def "false with not available key"() {
        expect:
        HStoreUtil.isFlagEnabled(attributes, "code") == result
        where:
        attributes        || result
        ['hello': 'true'] || false
    }

    def "Success isFlagEnabled with String and Boolean"() {
        expect:
        HStoreUtil.isFlagEnabled(attributes, "code") == result
        where:
        attributes        || result
        ['code': 'true']  || true
        ['code': 'false'] || false
        ['code': true]    || true
        ['code': false]   || false
    }
}
