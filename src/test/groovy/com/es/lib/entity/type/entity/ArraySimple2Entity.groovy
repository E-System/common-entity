package com.es.lib.entity.type.entity

import com.es.lib.entity.type.array.date.DateArrayType
import com.es.lib.entity.type.array.date.DateTimeArrayType
import com.es.lib.entity.type.array.IntegerPrimitiveArrayType
import com.es.lib.entity.type.array.LongPrimitiveArrayType
import com.es.lib.entity.type.array.StringArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs

import javax.persistence.*

@Entity
@TypeDefs([
    @TypeDef(name = "StringArray", typeClass = StringArrayType.class),
    @TypeDef(name = "IntegerPrimitiveArray", typeClass = IntegerPrimitiveArrayType.class),
    @TypeDef(name = "LongPrimitiveArray", typeClass = LongPrimitiveArrayType.class),
    @TypeDef(name = "DateTimeArray", typeClass = DateTimeArrayType.class),
    @TypeDef(name = "DateArray", typeClass = DateArrayType.class)
])
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
    @Type(type = "DateTimeArray")
    @Column(columnDefinition = "TIMESTAMPTZ[]")
    private Date[] dates
    @Type(type = "DateArray")
    @Column(columnDefinition = "DATE[]")
    private Date[] dates2

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

    Date[] getDates2() {
        return dates2
    }

    void setDates2(Date[] dates2) {
        this.dates2 = dates2
    }
}
