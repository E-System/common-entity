package com.es.lib.entity.type.entity


import org.hibernate.annotations.Type

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
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
    @Type(type = "DateList")
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
