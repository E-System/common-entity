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
@TypeDefs([
        @TypeDef(name = "StringArrayType", typeClass = StringArrayType.class),
        @TypeDef(name = "IntegerPrimitiveArrayType", typeClass = IntegerPrimitiveArrayType.class),
        @TypeDef(name = "LongPrimitiveArrayType", typeClass = LongPrimitiveArrayType.class),
        @TypeDef(name = "DateArrayType", typeClass = DateArrayType.class)
])
@Table(name = "ArrayEntity")
class ArraySimple2Entity {

    @Id
    @GeneratedValue
    private Integer id
    @Type(type = "StringArrayType")
    @Column(columnDefinition = "VARCHAR[]")
    private String[] strings
    @Type(type = "IntegerPrimitiveArrayType")
    @Column(columnDefinition = "INT[]")
    private int[] integers
    @Type(type = "LongPrimitiveArrayType")
    @Column(columnDefinition = "BIGINT[]")
    private long[] longs
    @Type(type = "DateArrayType")
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
