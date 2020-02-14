package com.es.lib.entity.type

import com.es.lib.entity.PgRunner
import com.es.lib.entity.type.entity.TestEntity
import org.hibernate.Session
import org.hibernate.Transaction

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 03.02.2018
 */
class HStoreTypeSpec extends PgRunner {

    def "Convert from Map<?,?> in hstore string"() {
        expect:
        HStoreType.toString(map as Map<?, ?>) == result
        where:
        map                                           || result
        null                                          || ""
        []                                            || ""
        ["a": "a"]                                    || '"a"=>"a"'
        ["a": null]                                   || '"a"=>NULL'
        ["a": "a", "b": "b"]                          || '"a"=>"a", "b"=>"b"'
        ["a": "a\"b", "b": "b\"a"]                    || '"a"=>"a\\"b", "b"=>"b\\"a"'
        ["a": "a\"\\b", "b": "b\\\"a"]                || '"a"=>"a\\"\\\\b", "b"=>"b\\\\\\"a"'
        ["a": "a\\b", "b": "b\\a"]                    || '"a"=>"a\\\\b", "b"=>"b\\\\a"'
        ["name": "ПАО \"КЕРЧЕНСКОЕ АТП№14313\" (13)"] || '"name"=>"ПАО \\"КЕРЧЕНСКОЕ АТП№14313\\" (13)"'
    }

    def "Insert"() {
        setup:
        Session session = newSessionFactory().openSession()
        when:
        Transaction txn = session.beginTransaction()

        def entity = new TestEntity()
        entity.attributes = ["asd": "asd1", "bcd": "bcd1"]

        session.persist(entity)
        txn.commit()
        session.clear()
        entity = session.get(TestEntity.class, entity.getId())
        then:
        entity.id != null
        entity.attributes != null
        entity.attributes["asd"] == "asd1"
        entity.attributes["bcd"] == "bcd1"
        cleanup:
        if (session != null) {
            session.close()
        }
    }

    def "Insert with escape"() {
        setup:
        Session session = newSessionFactory().openSession()
        when:
        Transaction txn = session.beginTransaction()

        def entity = new TestEntity()
        entity.attributes = ["asd": "as\"d1", "bcd": "bc\"\\d1", "def": "b\\\"a"]

        session.persist(entity)
        txn.commit()
        session.clear()
        entity = session.get(TestEntity.class, entity.getId())
        then:
        entity.id != null
        entity.attributes != null
        entity.attributes["asd"] == "as\"d1"
        entity.attributes["bcd"] == "bc\"\\d1"
        entity.attributes["def"] == "b\\\"a"
        cleanup:
        if (session != null) {
            session.close()
        }
    }

    def "Update"() {
        setup:
        Session session = newSessionFactory().openSession()
        when:
        Transaction txn = session.beginTransaction()

        def entity = session.get(TestEntity.class, 1)

        entity.attributes["asd"] = "asd2"
        entity.attributes["qwe"] = "qwe1"
        entity.attributes.remove("bcd")

        session.persist(entity)
        txn.commit()
        session.clear()
        entity = session.get(TestEntity.class, entity.getId())
        then:
        entity.id != null
        entity.attributes != null
        entity.attributes["asd"] == "asd2"
        !entity.attributes.containsKey("bcd")
        entity.attributes["qwe"] == "qwe1"
        cleanup:
        if (session != null) {
            session.close()
        }
    }


    def "Update with native"() {
        setup:
        Session session = newSessionFactory().openSession()
        when:
        Transaction txn = session.beginTransaction()
        session.createNativeQuery("update TestEntity set attributes = cast(:attrs as hstore) where id = :id")
            .setParameter("attrs", HStoreType.toString(["asd": "a\"\\b", "qwe": "b\\\"a", "name": "ПАО \"КЕРЧЕНСКОЕ АТП№14313\" (13)"]))
            .setParameter("id", 1).executeUpdate()
        txn.commit()
        session.clear()
        def entity = session.get(TestEntity.class, 1)
        then:
        entity.id != null
        entity.attributes != null
        entity.attributes["asd"] == "a\"\\b"
        !entity.attributes.containsKey("bcd")
        entity.attributes["qwe"] == "b\\\"a"
        entity.attributes["name"] == "ПАО \"КЕРЧЕНСКОЕ АТП№14313\" (13)"
        cleanup:
        if (session != null) {
            session.close()
        }
    }

    @Override
    protected Collection<Class<?>> getEntityClasses() {
        return [TestEntity.class]
    }
}
