package com.es.lib.entity.event;

import com.es.lib.entity.iface.IPrimaryKey;

import java.io.Serializable;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 28.02.2020
 */
public interface IEntityEvent {

    IPrimaryKey<? extends Serializable> getInstance();

    default <T extends IPrimaryKey<? extends Serializable>> T getTypedInstance(Class<T> clz) {
        return (T) getInstance();
    }

    default boolean isInstanceEmpty() {
        return getInstance() == null;
    }
}
