package com.es.lib.entity.type.entity

import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@TypeDefs([
        @TypeDef(name = "TestJsonType", typeClass = TestJsonType.class)
])
class TestJsonEntity {

    @Id
    @GeneratedValue
    private Integer id
    @Type(type = "TestJsonType")
    @Column(columnDefinition = "jsonb")
    private TestJson json

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    TestJson getJson() {
        return json
    }

    void setJson(TestJson json) {
        this.json = json
    }
}
