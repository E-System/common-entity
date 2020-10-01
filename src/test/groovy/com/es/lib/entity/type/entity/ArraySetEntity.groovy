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
    @TypeDef(name = "StringSet", typeClass = StringSetType.class),
    @TypeDef(name = "IntegerSet", typeClass = IntegerSetType.class),
    @TypeDef(name = "LongSet", typeClass = LongSetType.class),
    @TypeDef(name = "DateSet", typeClass = DateSetType.class)
])
@Table(name = "ArrayEntity")
class ArraySetEntity {

    @Id
    @GeneratedValue
    private Integer id
    @Type(type = "StringSet")
    @Column(columnDefinition = "VARCHAR[]")
    private TreeSet<String> strings
    @Type(type = "IntegerSet")
    @Column(columnDefinition = "INT[]")
    private TreeSet<Integer> integers
    @Type(type = "LongSet")
    @Column(columnDefinition = "BIGINT[]")
    private TreeSet<Integer> longs
    @Type(type = "DateSet")
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
