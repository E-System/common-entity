package com.es.lib.entity.type.entity

import com.es.lib.entity.type.array.set.DateSetType
import com.es.lib.entity.type.array.set.IntegerSetType
import com.es.lib.entity.type.array.set.LongSetType
import com.es.lib.entity.type.array.set.StringSetType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs

import javax.persistence.*

@Entity
@TypeDefs([
        @TypeDef(name = "StringSetType", typeClass = StringSetType.class),
        @TypeDef(name = "IntegerSetType", typeClass = IntegerSetType.class),
        @TypeDef(name = "LongSetType", typeClass = LongSetType.class),
        @TypeDef(name = "DateSetType", typeClass = DateSetType.class)
])
@Table(name = "ArrayEntity")
class ArraySetEntity {

    @Id
    @GeneratedValue
    private Integer id
    @Type(type = "StringSetType")
    @Column(columnDefinition = "VARCHAR[]")
    private TreeSet<String> strings
    @Type(type = "IntegerSetType")
    @Column(columnDefinition = "INT[]")
    private TreeSet<Integer> integers
    @Type(type = "LongSetType")
    @Column(columnDefinition = "BIGINT[]")
    private TreeSet<Integer> longs
    @Type(type = "DateSetType")
    @Column(columnDefinition = "TIMESTAMPTZ[]")
    private TreeSet<Date> dates

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    TreeSet<String> getStrings() {
        return strings
    }

    void setStrings(TreeSet<String> strings) {
        this.strings = strings
    }

    TreeSet<Integer> getIntegers() {
        return integers
    }

    void setIntegers(TreeSet<Integer> integers) {
        this.integers = integers
    }

    TreeSet<Integer> getLongs() {
        return longs
    }

    void setLongs(TreeSet<Integer> longs) {
        this.longs = longs
    }

    TreeSet<Date> getDates() {
        return dates
    }

    void setDates(TreeSet<Date> dates) {
        this.dates = dates
    }
}
