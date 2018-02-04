package com.es.lib.entity.type.entity

import com.es.lib.entity.type.array.set.DateSetType
import com.es.lib.entity.type.array.set.IntegerSetType
import com.es.lib.entity.type.array.set.StringSetType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs

import javax.persistence.*

@Entity
@TypeDefs([
        @TypeDef(name = "StringSetType", typeClass = StringSetType.class),
        @TypeDef(name = "IntegerSetType", typeClass = IntegerSetType.class),
        @TypeDef(name = "DateSetType", typeClass = DateSetType.class)
])
@Table(name = "ArrayEntity")
class ArraySetEntity {

    @Id
    @GeneratedValue
    private Integer id
    @Type(type = "StringSetType")
    @Column(columnDefinition = "varchar[]")
    private TreeSet<String> strings
    @Type(type = "IntegerSetType")
    @Column(columnDefinition = "integer[]")
    private TreeSet<Integer> integers
    @Type(type = "DateSetType")
    @Column(columnDefinition = "timestamptz[]")
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

    TreeSet<Date> getDates() {
        return dates
    }

    void setDates(TreeSet<Date> dates) {
        this.dates = dates
    }
}
