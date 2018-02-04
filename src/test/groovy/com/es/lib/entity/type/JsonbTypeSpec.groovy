package com.es.lib.entity.type

import com.es.lib.entity.PgRunner
import com.es.lib.entity.type.entity.TestJson
import com.es.lib.entity.type.entity.TestJsonEntity
import org.hibernate.Session
import org.hibernate.Transaction

class JsonbTypeSpec extends PgRunner {

    def "Insert"() {
        setup:
        Session session = newSessionFactory().openSession()
        when:
        Transaction txn = session.beginTransaction()

        def entity = new TestJsonEntity()
        entity.json = new TestJson("ABC", ["A": 1, "B": 2])

        session.persist(entity)
        txn.commit()
        session.clear()
        entity = session.get(TestJsonEntity.class, entity.getId())
        then:
        entity.id != null
        entity.json != null
        entity.json.code == "ABC"
        entity.json.attrs != null
        entity.json.attrs["A"] == 1
        entity.json.attrs["B"] == 2
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

        def entity = session.get(TestJsonEntity.class, 1)

        entity.json.code = "abc"
        entity.json.attrs["A"] = 2
        entity.json.attrs.remove("B")
        entity.json.attrs["C"] = 4

        session.persist(entity)
        txn.commit()
        session.clear()
        entity = session.get(TestJsonEntity.class, entity.getId())
        then:
        entity.id != null
        entity.json != null
        entity.json.code == "abc"
        entity.json.attrs != null
        entity.json.attrs["A"] == 2
        !entity.json.attrs.containsKey("B")
        entity.json.attrs["C"] == 4
        cleanup:
        if (session != null) {
            session.close()
        }
    }

    @Override
    protected Collection<Class<?>> getConfigClasses() {
        return [TestJsonEntity.class]
    }
}
