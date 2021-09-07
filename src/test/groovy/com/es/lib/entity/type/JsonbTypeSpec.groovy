package com.es.lib.entity.type

import com.es.lib.entity.PgRunner
import com.es.lib.entity.model.field.code.FieldType
import com.es.lib.entity.model.field.json.JsonField
import com.es.lib.entity.model.field.json.JsonFields
import com.es.lib.entity.model.field.json.value.AttrsFieldValue
import com.es.lib.entity.type.entity.TestJsonFieldsEntity
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

    def "Fields"() {
        setup:
        Session session = newSessionFactory().openSession()
        when:
        Transaction txn = session.beginTransaction()

        def entity = new TestJsonFieldsEntity()
        entity.json = new JsonFields(['ABC' : new JsonField(FieldType.TEXT, "ABC", "ABC", [new AttrsFieldValue(["A": "1", "B": "2"])])])

        session.persist(entity)
        txn.commit()
        session.clear()
        entity = session.get(TestJsonFieldsEntity.class, entity.getId())
        then:
        entity.id != null
        entity.json != null
        entity.json['ABC'].code == "ABC"
        entity.json['ABC'].values[0] != null
        entity.json['ABC'].values[0].value["A"] == "1"
        entity.json['ABC'].values[0].value["B"] == "2"
        cleanup:
        if (session != null) {
            session.close()
        }
    }

    def "Insert empty object"() {
        setup:
        Session session = newSessionFactory().openSession()
        when:
        Transaction txn = session.beginTransaction()

        def entity = new TestJsonEntity()
        entity.json = new TestJson(null, null)

        session.persist(entity)
        txn.commit()
        session.clear()
        entity = session.get(TestJsonEntity.class, entity.getId())
        then:
        entity.id != null
        entity.json != null
        entity.json.code == null
        entity.json.attrs == null
        cleanup:
        if (session != null) {
            session.close()
        }
    }

    @Override
    protected Collection<Class<?>> getEntityClasses() {
        return [TestJsonEntity.class, TestJsonFieldsEntity.class]
    }
}
