package com.es.lib.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 12.04.2018
 */
@MappedSuperclass
public class AbstractEntity<PK extends Number> implements IPrimaryKey<PK> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private PK id;

    @Override
    public PK getId() {
        return id;
    }

    @Override
    public void setId(PK id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return _hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return _equals(object);
    }

    @Override
    public String toString() {
        return _toString();
    }
}
