package com.es.lib.entity.type.entity

import com.es.lib.entity.type.array.date.DateArrayListType
import com.es.lib.entity.type.array.date.DateTimeArrayListType
import com.es.lib.entity.type.array.list.IntegerArrayListType
import com.es.lib.entity.type.array.list.LongArrayListType
import com.es.lib.entity.type.array.list.StringArrayListType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@TypeDefs([
    @TypeDef(name = "StringList", typeClass = StringArrayListType.class),
    @TypeDef(name = "IntegerList", typeClass = IntegerArrayListType.class),
    @TypeDef(name = "LongList", typeClass = LongArrayListType.class),
    @TypeDef(name = "DateTimeList", typeClass = DateTimeArrayListType.class),
    @TypeDef(name = "DateList", typeClass = DateArrayListType.class)
])
class ArrayEntity {

    @Id
    @GeneratedValue
    private Integer id
    @Type(type = "StringList")
    @Column(columnDefinition = "VARCHAR[]")
    private List<String> strings
    @Type(type = "IntegerList")
    @Column(columnDefinition = "INT[]")
    private List<Integer> integers
    @Type(type = "LongList")
    @Column(columnDefinition = "BIGINT[]")
    private List<Long> longs
    @Type(type = "DateTimeList")
    @Column(columnDefinition = "TIMESTAMPTZ[]")
    private List<Date> dates
    @Type(type = "DateList")
    @Column(columnDefinition = "DATE[]")
    private List<Date> dates2

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    List<String> getStrings() {
        return strings
    }

    void setStrings(List<String> strings) {
        this.strings = strings
    }

    List<Integer> getIntegers() {
        return integers
    }

    void setIntegers(List<Integer> integers) {
        this.integers = integers
    }

    List<Long> getLongs() {
        return longs
    }

    void setLongs(List<Long> longs) {
        this.longs = longs
    }

    List<Date> getDates() {
        return dates
    }

    void setDates(List<Date> dates) {
        this.dates = dates
    }

    List<Date> getDates2() {
        return dates2
    }

    void setDates2(List<Date> dates2) {
        this.dates2 = dates2
    }
}
