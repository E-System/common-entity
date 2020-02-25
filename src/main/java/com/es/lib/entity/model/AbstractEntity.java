package com.es.lib.entity.model;

import com.es.lib.entity.iface.IPrimaryKey;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 12.04.2018
 */
@MappedSuperclass
public class AbstractEntity<PK extends Serializable> implements IPrimaryKey<PK> {

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