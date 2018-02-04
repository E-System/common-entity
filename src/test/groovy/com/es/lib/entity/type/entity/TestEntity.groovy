package com.es.lib.entity.type.entity

import com.es.lib.entity.type.HStoreType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@TypeDefs([
        @TypeDef(name = "hstore", typeClass = HStoreType.class)
])
class TestEntity {

    @Id
    @GeneratedValue
    private Integer id
    @Type(type = "hstore")
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
