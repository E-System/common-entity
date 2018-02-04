package com.es.lib.entity.type.entity

import com.es.lib.entity.type.array.DateArrayType
import com.es.lib.entity.type.array.IntegerArrayType
import com.es.lib.entity.type.array.StringArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs

import javax.persistence.*

@Entity
@TypeDefs([
        @TypeDef(name = "StringArrayType", typeClass = StringArrayType.class),
        @TypeDef(name = "IntegerArrayType", typeClass = IntegerArrayType.class),
        @TypeDef(name = "DateArrayType", typeClass = DateArrayType.class)
])
@Table(name = "ArrayEntity")
class ArraySimpleEntity {

    @Id
    @GeneratedValue
    private Integer id
    @Type(type = "StringArrayType")
    @Column(columnDefinition = "varchar[]")
    private String[] strings
    @Type(type = "IntegerArrayType")
    @Column(columnDefinition = "integer[]")
    private Integer[] integers
    @Type(type = "DateArrayType")
    @Column(columnDefinition = "timestamptz[]")
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

    Integer[] getIntegers() {
        return integers
    }

    void setIntegers(Integer[] integers) {
        this.integers = integers
    }

    Date[] getDates() {
        return dates
    }

    void setDates(Date[] dates) {
        this.dates = dates
    }
}
