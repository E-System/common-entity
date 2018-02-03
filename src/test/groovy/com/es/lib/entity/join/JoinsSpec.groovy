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

package com.es.lib.entity.join

import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 01.06.15
 */
class JoinsSpec extends Specification {

    def "Joins is empty after construction"() {
        expect:
        new Joins().empty
    }

    def "One full join condition without fetch"() {
        when:
        def joins = new Joins().inner("a.a", "a")
        then:
        joins.format(true) == " join a.a a"
    }

    def "One full join condition with fetch"() {
        when:
        def joins = new Joins().innerFetch("a.a", "a")
        then:
        joins.format(true) == " join fetch a.a a"
    }

    def "One full join condition without fetch by flag"() {
        when:
        def joins = new Joins().innerFetch("a.a", "a")
        then:
        joins.format(false) == " join a.a a"
    }

    def "One right join condition without fetch"() {
        when:
        def joins = new Joins().right("a.a", "a")
        then:
        joins.format(true) == " right join a.a a"
    }

    def "One right join condition with fetch"() {
        when:
        def joins = new Joins().rightFetch("a.a", "a")
        then:
        joins.format(true) == " right join fetch a.a a"
    }

    def "One right join condition without fetch by flag"() {
        when:
        def joins = new Joins().right("a.a", "a")
        then:
        joins.format(false) == " right join a.a a"
    }

    def "One left join condition without fetch"() {
        when:
        def joins = new Joins().left("a.a", "a")
        then:
        joins.format(true) == " left join a.a a"
    }

    def "One left join condition with fetch"() {
        when:
        def joins = new Joins().leftFetch("a.a", "a")
        then:
        joins.format(true) == " left join fetch a.a a"
    }

    def "One left join condition without fetch by flag"() {
        when:
        def joins = new Joins().left("a.a", "a")
        then:
        joins.format(false) == " left join a.a a"
    }

    def "Alias not exist"() {
        when:
        def joins = new Joins().leftFetch("a.a", "a")
        then:
        joins.findAlias("a.b") == "a.b"
    }

    def "Alias exist"() {
        when:
        def joins = new Joins().leftFetch("a.a", "a")
        then:
        joins.findAlias("a.a") == "a"
    }

    def "Recursive alias"() {
        when:
        def joins = new Joins().inner("a.b", "c").inner("c.a", "d")
        then:
        joins.findAlias("a.b.a") == "d"
    }

    def "All conditions in add order with declared fetches"() {
        when:
        def joins = new Joins()
                .inner("a1.a1", "b1")
                .innerFetch("a2.a2", "b2")
                .right("a3.a3", "b3")
                .rightFetch("a4.a4", "b4")
                .left("a5.a5", "b5")
                .leftFetch("a6.a6", "b6")
        then:
        joins.format(true) == " join a1.a1 b1 join fetch a2.a2 b2 right join a3.a3 b3 right join fetch a4.a4 b4 left join a5.a5 b5 left join fetch a6.a6 b6"
    }

    def "All conditions in add order without fetches"() {
        when:
        def joins = new Joins()
                .inner("a1.a1", "b1")
                .innerFetch("a2.a2", "b2")
                .right("a3.a3", "b3")
                .rightFetch("a4.a4", "b4")
                .left("a5.a5", "b5")
                .leftFetch("a6.a6", "b6")
        then:
        joins.format(false) == " join a1.a1 b1 join a2.a2 b2 right join a3.a3 b3 right join a4.a4 b4 left join a5.a5 b5 left join a6.a6 b6"
    }
}
