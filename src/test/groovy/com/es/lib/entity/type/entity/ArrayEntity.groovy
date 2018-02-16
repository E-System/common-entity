package com.es.lib.entity.type.entity

import com.es.lib.entity.type.array.list.DateArrayListType
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
        @TypeDef(name = "StringArrayListType", typeClass = StringArrayListType.class),
        @TypeDef(name = "IntegerArrayListType", typeClass = IntegerArrayListType.class),
        @TypeDef(name = "LongArrayListType", typeClass = LongArrayListType.class),
        @TypeDef(name = "DateArrayListType", typeClass = DateArrayListType.class)
])
class ArrayEntity {

    @Id
    @GeneratedValue
    private Integer id
    @Type(type = "StringArrayListType")
    @Column(columnDefinition = "VARCHAR[]")
    private List<String> strings
    @Type(type = "IntegerArrayListType")
    @Column(columnDefinition = "INT[]")
    private List<Integer> integers
    @Type(type = "LongArrayListType")
    @Column(columnDefinition = "BIGINT[]")
    private List<Long> longs
    @Type(type = "DateArrayListType")
    @Column(columnDefinition = "TIMESTAMPTZ[]")
    private List<Date> dates

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
}
