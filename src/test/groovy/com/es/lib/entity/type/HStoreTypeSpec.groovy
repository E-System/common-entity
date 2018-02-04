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

    @Override
    protected Collection<Class<?>> getConfigClasses() {
        return [TestEntity.class]
    }
}
