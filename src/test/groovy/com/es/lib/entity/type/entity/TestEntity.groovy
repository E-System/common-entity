package com.es.lib.entity.type.entity


import org.hibernate.annotations.Type

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class TestEntity {

    @Id
    @GeneratedValue
    private Integer id
    @Type(type = "HStore")
    @Column(columnDefinition = "hstore")
    private Map<String, String> attributes

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    Map<String, String> getAttributes() {
        return attributes
    }

    void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes
    }
}
