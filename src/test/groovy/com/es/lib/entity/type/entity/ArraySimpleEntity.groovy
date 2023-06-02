package com.es.lib.entity.type.entity

import com.es.lib.entity.type.array.IntegerArrayType
import com.es.lib.entity.type.array.LongArrayType
import com.es.lib.entity.type.array.StringArrayType
import com.es.lib.entity.type.array.date.DateArrayType
import com.es.lib.entity.type.array.date.DateTimeArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs

import javax.persistence.*

@Entity
@TypeDefs([
        @TypeDef(name = "StringArray", typeClass = StringArrayType.class),
        @TypeDef(name = "IntegerArray", typeClass = IntegerArrayType.class),
        @TypeDef(name = "LongArray", typeClass = LongArrayType.class),
        @TypeDef(name = "DateTimeArray", typeClass = DateTimeArrayType.class),
        @TypeDef(name = "DateArray", typeClass = DateArrayType.class)
])
@Table(name = "ArrayEntity")
class ArraySimpleEntity {

    @Id
    @GeneratedValue
    private Integer id
    @Type(type = "StringArray")
    @Column(columnDefinition = "VARCHAR[]")
    private String[] strings
    @Type(type = "IntegerArray")
    @Column(columnDefinition = "INT[]")
    private Integer[] integers
    @Type(type = "LongArray")
    @Column(columnDefinition = "BIGINT[]")
    private Long[] longs
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

    Integer[] getIntegers() {
        return integers
    }

    void setIntegers(Integer[] integers) {
        this.integers = integers
    }

    Long[] getLongs() {
        return longs
    }

    void setLongs(Long[] longs) {
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
