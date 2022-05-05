package com.es.lib.entity.dialect

import com.es.lib.entity.PgRunner
import com.es.lib.entity.type.entity.ArrayEntity
import org.hibernate.Session
import org.hibernate.Transaction

class ESPostgresDialectSpec extends PgRunner {

    def "Selection with array"() {
        setup:
        Session session = newSessionFactory().openSession()
        when:
        Transaction txn = session.beginTransaction()

        def date = new Date()

        def entity = new ArrayEntity()
        entity.strings = ["A1", "A2", "A3"]
        entity.integers = [1, 2, 3]
        entity.longs = [1L, 2L, 3L]
        entity.dates = [date, date, date]

        session.persist(entity)
        txn.commit()
        session.clear()
        entity = session.get(ArrayEntity.class, entity.getId())
        def sentity = session.createQuery("select e from ArrayEntity e where es_array_contain(e.strings, es_array_cast('A1,A2','VARCHAR[]')) = true", ArrayEntity.class).getSingleResult()
        def sentity2 = session.createQuery("select e from ArrayEntity e where es_array_contained(es_array_cast('A1,A2','VARCHAR[]'), e.strings) = true", ArrayEntity.class).getSingleResult()
        def sentity3 = session.createQuery("select e from ArrayEntity e where es_array_overlap(es_array_cast('A3','VARCHAR[]'), e.strings) = true", ArrayEntity.class).getSingleResult()
        def sentity4 = session.createQuery("select e from ArrayEntity e where es_array_overlap(es_array_cast('1','INT[]'), e.integers) = true", ArrayEntity.class).getSingleResult()
        def sentity5 = session.createQuery("select e from ArrayEntity e where es_array_overlap(es_array_cast(:values,'INT[]'), e.integers) = true", ArrayEntity.class)
            .setParameter("values", "1,2")
            .getSingleResult()
        def sentity6 = session.createQuery("select e from ArrayEntity e where :values is null or es_array_contain(e.integers, es_array_cast(:values,'INT[]')) = true", ArrayEntity.class)
            .setParameter("values", null)
            .getResultList()
        println(sentity)
        println(sentity2)
        println(sentity3)
        println(sentity4)
        println(sentity5)
        println(sentity6)
        then:
        entity.id != null
        entity.strings != null
        entity.strings.size() == 3
        entity.strings[0] == "A1"
        entity.strings[1] == "A2"
        entity.strings[2] == "A3"
        entity.integers != null
        entity.integers.size() == 3
        entity.integers[0] == 1
        entity.integers[1] == 2
        entity.integers[2] == 3
        entity.longs != null
        entity.longs.size() == 3
        entity.longs[0] == 1L
        entity.longs[1] == 2L
        entity.longs[2] == 3L
        entity.dates != null
        entity.dates.size() == 3
        println(entity.dates[0].class)
        entity.dates[0].time == date.time
        entity.dates[1].time == date.time
        entity.dates[2].time == date.time
        cleanup:
        if (session != null) {
            session.close()
        }
    }

    @Override
    protected Collection<Class<?>> getEntityClasses() {
        return [ArrayEntity.class]
    }
}
