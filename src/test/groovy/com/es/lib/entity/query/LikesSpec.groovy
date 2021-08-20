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

package com.es.lib.entity.query

import spock.lang.Specification

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 12.10.15
 */
class LikesSpec extends Specification {

    def "Any"() {
        expect:
        Likes.any(text) == result
        where:
        text                         || result
        null                         || null
        ""                           || null
        " "                          || null
        "find"                       || "%find%"
        "FIND"                       || "%find%"
        "FinD"                       || "%find%"
        " find"                      || "%find%"
        " find "                     || "%find%"
        "  find "                    || "%find%"
        "  find  "                   || "%find%"
        "  find  \n"                 || "%find%"
        "\t\n  find  \n"             || "%find%"
        "\t\n  find in text  \n"     || "%find in text%"
        "\t\n  текст для поиска  \n" || "%текст для поиска%"
        "\t\n  Текст Для Поиска  \n" || "%текст для поиска%"
    }

    def "Begin"() {
        expect:
        Likes.begin(text) == result
        where:
        text                         || result
        null                         || null
        ""                           || null
        " "                          || null
        "find"                       || "find%"
        "FIND"                       || "find%"
        "FinD"                       || "find%"
        " find"                      || "find%"
        " find "                     || "find%"
        "  find "                    || "find%"
        "  find  "                   || "find%"
        "  find  \n"                 || "find%"
        "\t\n  find  \n"             || "find%"
        "\t\n  find in text  \n"     || "find in text%"
        "\t\n  текст для поиска  \n" || "текст для поиска%"
        "\t\n  Текст Для Поиска  \n" || "текст для поиска%"
    }

    def "Like"() {
        expect:
        Likes.like(text, any) == result
        where:
        text   | any   || result
        "find" | true  || "%find%"
        "find" | false || "find%"
    }
}
