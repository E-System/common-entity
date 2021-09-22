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
    @TypeDef(name = "TestDateJson", typeClass = TestDateJson.UserType.class)
])
class TestDateJsonEntity {

    @Id
    @GeneratedValue
    private Integer id
    @Type(type = "TestDateJson")
    @Column(columnDefinition = "jsonb")
    private TestDateJson json

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    TestDateJson getJson() {
        return json
    }

    void setJson(TestDateJson json) {
        this.json = json
    }
}
