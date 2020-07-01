package com.es.lib.entity.query

import spock.lang.Specification

import java.util.function.Supplier

import static com.es.lib.entity.query.QueryEsl.*

class ConditionsSpec extends Specification {

    def "Empty conditions"() {
        expect:
        "" == cb
        where:
        cb = conditions().format()
    }

    def "Expect exp1 after construction"() {
        expect:
        "exp1" == cb
        where:
        cb = conditions(
            where("exp1")
        ).format()
    }

    def "Expect (and exp1 and exp2)"() {
        expect:
        "and exp1 and exp2" == cb
        where:
        cb = conditions(
            where("and exp1"),
            where("and exp2")
        ).format()
    }

    def "Expect (and exp1 and exp2 1)"() {
        expect:
        "and exp1 and exp2" == cb
        where:
        cb = conditions(
            where("and exp1"),
            where("and exp2", param("hello", new Supplier<Object>() {
                @Override
                Object get() {
                    return 1
                }
            }))
        ).format()
    }

    def "Empty statement 1"() {
        when:
        def cb = conditions(
            where(
                stmt("and a = :id", param("id", new Supplier<Object>() {
                    @Override
                    Object get() {
                        return 2
                    }
                }))
            ),
            where(
                new Supplier<Boolean>() {
                    @Override
                    Boolean get() {
                        return false
                    }
                },
                stmt("and b = :id", param("id", new Supplier<Object>() {
                    @Override
                    Object get() {
                        return 3
                    }
                })),
                skip()
            )
        )
        then:
        cb.format() == 'and a = :id'
        cb.parameters() == ['id': 2]
        cb.skipSelect
    }

    def "Empty statement 2"() {
        when:
        def cb = conditions(
            where(
                stmt("and a = :id", param("id", new Supplier<Object>() {
                    @Override
                    Object get() {
                        return 2
                    }
                }))
            ),
            where(
                new Supplier<Boolean>() {
                    @Override
                    Boolean get() {
                        return true
                    }
                },
                stmt("and b = :id2", param("id2", new Supplier<Object>() {
                    @Override
                    Object get() {
                        return 3
                    }
                })),
                skip()
            )
        )
        then:
        cb.format() == 'and a = :id and b = :id2'
        cb.parameters() == ['id': 2, 'id2': 3]
        !cb.skipSelect
    }
}
