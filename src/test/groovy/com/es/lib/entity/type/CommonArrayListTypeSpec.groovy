package com.es.lib.entity.type

import com.es.lib.entity.PgRunner
import com.es.lib.entity.type.entity.ArrayEntity
import com.es.lib.entity.type.entity.ArraySetEntity
import com.es.lib.entity.type.entity.ArraySimple2Entity
import com.es.lib.entity.type.entity.ArraySimpleEntity
import org.hibernate.Session
import org.hibernate.Transaction

class CommonArrayListTypeSpec extends PgRunner {

    def "Insert"() {
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

    def "Update"() {
        setup:
        Session session = newSessionFactory().openSession()
        when:
        Transaction txn = session.beginTransaction()

        def entity = session.get(ArrayEntity.class, 1)

        def date = new Date()
        entity.strings.add("A4")
        entity.strings.remove("A1")
        entity.strings.add("A5")

        entity.integers.add(4)
        entity.integers.remove((Object) 1)
        entity.integers.add(5)

        entity.longs.add(4L)
        entity.longs.remove((Object) 1L)
        entity.longs.add(5L)

        entity.dates.add(date)
        entity.dates.remove(0)
        entity.dates.add(date)

        session.persist(entity)
        txn.commit()
        session.clear()
        entity = session.get(ArrayEntity.class, entity.getId())
        def ent = session.get(ArraySimpleEntity.class, entity.getId())
        def entSet = session.get(ArraySetEntity.class, entity.getId())
        def entSimple = session.get(ArraySimple2Entity.class, entity.getId())
        then:
        entity.id != null
        entity.strings != null
        entity.strings.size() == 4
        entity.strings[0] == "A2"
        entity.strings[1] == "A3"
        entity.strings[2] == "A4"
        entity.strings[3] == "A5"
        entity.integers != null
        entity.integers.size() == 4
        entity.integers[0] == 2
        entity.integers[1] == 3
        entity.integers[2] == 4
        entity.integers[3] == 5
        entity.longs != null
        entity.longs.size() == 4
        entity.longs[0] == 2
        entity.longs[1] == 3
        entity.longs[2] == 4
        entity.longs[3] == 5
        entity.dates != null
        entity.dates.size() == 4
        entity.dates[0] != date
        entity.dates[1] != date
        entity.dates[2] == date
        entity.dates[3] == date

        ent.id != null
        ent.strings != null
        ent.strings.length == 4
        ent.strings[0] == "A2"
        ent.strings[1] == "A3"
        ent.strings[2] == "A4"
        ent.strings[3] == "A5"
        ent.integers != null
        ent.integers.length == 4
        ent.integers[0] == 2
        ent.integers[1] == 3
        ent.integers[2] == 4
        ent.integers[3] == 5
        ent.longs != null
        ent.longs.length == 4
        ent.longs[0] == 2
        ent.longs[1] == 3
        ent.longs[2] == 4
        ent.longs[3] == 5
        ent.dates != null
        ent.dates.length == 4
        ent.dates[0] != date
        ent.dates[1] != date
        ent.dates[2] == date
        ent.dates[3] == date

        entSet.id != null
        entSet.strings != null
        entSet.strings.size() == 4
        entSet.strings[0] == "A2"
        entSet.strings[1] == "A3"
        entSet.strings[2] == "A4"
        entSet.strings[3] == "A5"
        entSet.integers != null
        entSet.integers.size() == 4
        entSet.integers[0] == 2
        entSet.integers[1] == 3
        entSet.integers[2] == 4
        entSet.integers[3] == 5
        entSet.longs != null
        entSet.longs.size() == 4
        entSet.longs[0] == 2
        entSet.longs[1] == 3
        entSet.longs[2] == 4
        entSet.longs[3] == 5
        entSet.dates != null
        entSet.dates.size() == 2
        entSet.dates[0] != date
        entSet.dates[1] == date

        entSimple.id != null
        entSimple.strings != null
        entSimple.strings.length == 4
        entSimple.strings[0] == "A2"
        entSimple.strings[1] == "A3"
        entSimple.strings[2] == "A4"
        entSimple.strings[3] == "A5"
        entSimple.integers != null
        entSimple.integers.length == 4
        entSimple.integers[0] == 2
        entSimple.integers[1] == 3
        entSimple.integers[2] == 4
        entSimple.integers[3] == 5
        entSimple.longs != null
        entSimple.longs.length == 4
        entSimple.longs[0] == 2
        entSimple.longs[1] == 3
        entSimple.longs[2] == 4
        entSimple.longs[3] == 5
        entSimple.dates != null
        entSimple.dates.length == 4
        entSimple.dates[0] != date
        entSimple.dates[1] != date
        entSimple.dates[2] == date
        entSimple.dates[3] == date

        cleanup:
        if (session != null) {
            session.close()
        }
    }

    @Override
    protected Collection<Class<?>> getConfigClasses() {
        return [ArrayEntity.class, ArraySimpleEntity.class, ArraySetEntity.class, ArraySimple2Entity.class]
    }
}
