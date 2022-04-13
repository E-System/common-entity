package com.es.lib.entity.type

import com.es.lib.entity.PgRunner
import com.es.lib.entity.type.entity.TestDateJson
import com.es.lib.entity.type.entity.TestDateJsonEntity
import org.hibernate.Session
import org.hibernate.Transaction

import java.time.OffsetDateTime
import java.time.ZonedDateTime

class JsonbTypeDateSpec extends PgRunner {

    def "Insert"() {
        setup:
        Session session = newSessionFactory().openSession()
        when:
        Transaction txn = session.beginTransaction()
        def offsetDateTime = OffsetDateTime.now()
        def zonedDateTime = ZonedDateTime.now()
        def entity = new TestDateJsonEntity()
        entity.json = new TestDateJson("ABC", offsetDateTime, zonedDateTime, 1)
        def entity2 = new TestDateJsonEntity()

        session.persist(entity)
        session.persist(entity2)
        txn.commit()
        session.clear()
        entity = session.get(TestDateJsonEntity.class, entity.getId())
        then:
        entity.id != null
        entity.json != null
        entity.json.code == "ABC"
        entity.json.offsetDateTime != null
        entity.json.offsetDateTime == offsetDateTime
        entity.json.zonedDateTime != null
        entity.json.zonedDateTime == zonedDateTime
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

        def entity = session.get(TestDateJsonEntity.class, 1)
        def offsetDateTime = OffsetDateTime.now()
        def zonedDateTime = ZonedDateTime.now()
        entity.json.code = "abc"
        entity.json.offsetDateTime = offsetDateTime
        entity.json.zonedDateTime = zonedDateTime

        session.persist(entity)
        txn.commit()
        session.clear()
        entity = session.get(TestDateJsonEntity.class, entity.getId())
        then:
        entity.id != null
        entity.json != null
        entity.json.code == "abc"
        entity.json.offsetDateTime != null
        entity.json.offsetDateTime == offsetDateTime
        entity.json.zonedDateTime != null
        entity.json.zonedDateTime == zonedDateTime
        cleanup:
        if (session != null) {
            session.close()
        }
    }

    def "Select json null"(){
        setup:
        Session session = newSessionFactory().openSession()
        when:
        Transaction txn = session.beginTransaction()

        def entity = session.get(TestDateJsonEntity.class, 2)
        txn.commit()
        session.clear()
        then:
        entity.id != null
        entity.json == null
        cleanup:
        if (session != null) {
            session.close()
        }
    }

    @Override
    protected Collection<Class<?>> getEntityClasses() {
        return [TestDateJsonEntity.class]
    }
}
