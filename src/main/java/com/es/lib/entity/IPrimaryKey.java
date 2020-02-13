/*
 * Copyright 2016 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.es.lib.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public interface IPrimaryKey<PK extends Serializable> extends Serializable {

    /**
     * Default alias for HQL entity
     */
    String PREFIX = "e";
    /**
     * Field separator in HQL
     */
    String SEPARATOR = ".";

    /**
     * Get entity unique ID
     *
     * @return Entity unique ID
     */
    PK getId();

    /**
     * Set entity unique ID
     *
     * @param id Entity unique ID
     */
    void setId(PK id);

    default int _hashCode() {
        return Objects.hashCode(getId());
    }

    default boolean _equals(Object object, Class _class) {
        if (!_class.isInstance(object)) {
            return false;
        }
        IPrimaryKey<PK> other = (IPrimaryKey<PK>) object;
        return !((getId() == null && other.getId() != null) || (getId() != null && !getId().equals(other.getId())));
    }

    default boolean _equals(Object object) {
        return _equals(object, getClass());
    }

    default String _toString(Class _class) {
        return _class.getName() + "[ id=" + getId() + " ]";
    }

    default String _toString() {
        return _toString(getClass());
    }

    /**
     * Get entity ID or null
     *
     * @param entity Entity
     * @param <T>    Primary key type
     * @return Entity ID or null
     */
    static <T extends Serializable> T getNullOrId(IPrimaryKey<T> entity) {
        return entity != null ? entity.getId() : null;
    }

    /**
     * Check entity ID exist
     *
     * @param instance Entity
     * @param <T>      Entity type
     * @return True if instance != null and id != null
     */
    static <R extends Serializable, T extends IPrimaryKey<R>> boolean isManaged(final T instance) {
        return getNullOrId(instance) != null;
    }

    /**
     * Get entity ID collection from entity collection
     *
     * @param list Entity collection
     * @param <T>  Primary key type
     * @return Entity ID collection
     */
    static <T extends Serializable> Collection<T> getIds(Collection<? extends IPrimaryKey<T>> list) {
        return list.stream().map(IPrimaryKey::getId).collect(Collectors.toList());
    }
}
