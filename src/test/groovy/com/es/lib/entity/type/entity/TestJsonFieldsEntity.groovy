package com.es.lib.entity.type.entity

import com.es.lib.entity.model.field.json.JsonFields
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@TypeDefs([
    @TypeDef(name = "JsonFields", typeClass = JsonFields.UserType.class)
])
class TestJsonFieldsEntity {

    @Id
    @GeneratedValue
    private Integer id
    @Type(type = "JsonFields")
    @Column(columnDefinition = "jsonb")
    private JsonFields json

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    JsonFields getJson() {
        return json
    }

    void setJson(JsonFields json) {
        this.json = json
    }
}
