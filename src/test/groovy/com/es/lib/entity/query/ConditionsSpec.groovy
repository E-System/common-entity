package com.es.lib.entity.query

import com.es.lib.entity.iface.IPrimaryKey
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

    static class WhereEntity implements IPrimaryKey<Integer> {
        Integer id

        WhereEntity(Integer id) {
            this.id = id
        }
    }

    def "where with entity"() {
        when:
        def cb = conditions(
            where("e.id", "idValue", { return new WhereEntity(12) })
        )
        then:
        cb.format() == 'and e.id = :idValue'
        cb.parameters() == ['idValue': 12]
        !cb.skipSelect
    }

    def "where with number"() {
        when:
        def cb = conditions(
            where("e.id", "idValue", { return 12 })
        )
        then:
        cb.format() == 'and e.id = :idValue'
        cb.parameters() == ['idValue': 12]
        !cb.skipSelect
    }

    def "where with date"() {
        when:
        def date = new Date()
        def cb = conditions(
            where("e.id", "<=", "idValue", { return date })
        )
        then:
        cb.format() == 'and e.id <= :idValue'
        cb.parameters() == ['idValue': date]
        !cb.skipSelect
    }

    def "where with null"() {
        when:
        def cb = conditions(
            where("e.id", "idValue", { return null })
        )
        then:
        cb.format() == ''
        cb.parameters() == [:]
        !cb.skipSelect
    }
}
