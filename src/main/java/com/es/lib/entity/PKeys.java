package com.es.lib.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PKeys {

    /**
     * Get entity ID or null
     *
     * @param entity Entity
     * @param <PK>   Primary key type
     * @return Entity ID or null
     */
    public static <PK extends Number> PK id(IPrimaryKey<PK> entity) {
        return entity != null ? entity.getId() : null;
    }

    /**
     * Get entity ID collection from entity collection
     *
     * @param list Entity collection
     * @param <PK> Primary key type
     * @return Entity ID collection
     */
    public static <PK extends Number> Collection<PK> id(Collection<? extends IPrimaryKey<PK>> list) {
        return list.stream().map(IPrimaryKey::getId).collect(Collectors.toList());
    }

    /**
     * Check entity ID exist
     *
     * @param instance Entity
     * @param <T>      Entity type
     * @return True if instance != null and id != null
     */
    public static <T extends IPrimaryKey> boolean idExist(final T instance) {
        return id(instance) != null;
    }
}
