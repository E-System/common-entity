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

package com.es.lib.entity.util

import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 12.10.15
 */
class LikeUtilSpec extends Specification {

    def "Any"() {
        expect:
        LikeUtil.any(text) == result
        where:
        text                         || result
        null                         || "%%"
        ""                           || "%%"
        " "                          || "%%"
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
        LikeUtil.begin(text) == result
        where:
        text                         || result
        null                         || "%"
        ""                           || "%"
        " "                          || "%"
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
        LikeUtil.like(text, any) == result
        where:
        text   | any   || result
        "find" | true  || "%find%"
        "find" | false || "find%"
    }
}
