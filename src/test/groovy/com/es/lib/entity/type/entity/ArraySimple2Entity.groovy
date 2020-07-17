package com.es.lib.entity.type.entity

import com.es.lib.entity.type.array.DateArrayType
import com.es.lib.entity.type.array.IntegerPrimitiveArrayType
import com.es.lib.entity.type.array.LongPrimitiveArrayType
import com.es.lib.entity.type.array.StringArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs

import javax.persistence.*

@Entity
@Table(name = "ArrayEntity")
class ArraySimple2Entity {

    @Id
    @GeneratedValue
    private Integer id
    @Type(type = "StringArray")
    @Column(columnDefinition = "VARCHAR[]")
    private String[] strings
    @Type(type = "IntegerPrimitiveArray")
    @Column(columnDefinition = "INT[]")
    private int[] integers
    @Type(type = "LongPrimitiveArray")
    @Column(columnDefinition = "BIGINT[]")
    private long[] longs
    @Type(type = "DateArray")
    @Column(columnDefinition = "TIMESTAMPTZ[]")
    private Date[] dates

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    String[] getStrings() {
        return strings
    }

    void setStrings(String[] strings) {
        this.strings = strings
    }

    int[] getIntegers() {
        return integers
    }

    void setIntegers(int[] integers) {
        this.integers = integers
    }

    long[] getLongs() {
        return longs
    }

    void setLongs(long[] longs) {
        this.longs = longs
    }

    Date[] getDates() {
        return dates
    }

    void setDates(Date[] dates) {
        this.dates = dates
    }
}
