/*
 * Copyright 2020 E-System LLC
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
package com.es.lib.entity.util;

import com.es.lib.entity.IPrimaryKey;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 14.02.2020
 */
public final class EntityUtil {

    private EntityUtil() { }

    /**
     * Get entity ID or null
     *
     * @param instance Entity
     * @param <R>      Primary key type
     * @param <T>      Entity type
     * @return Entity ID or null
     */
    static <R extends Serializable, T extends IPrimaryKey<? extends R>> R id(final T instance) {
        return instance != null ? instance.getId() : null;
    }

    /**
     * Check entity ID exist
     *
     * @param instance Entity
     * @param <R>      Primary key type
     * @param <T>      Entity type
     * @return True if instance != null and id != null
     */
    static <R extends Serializable, T extends IPrimaryKey<? extends R>> boolean idExist(final T instance) {
        return id(instance) != null;
    }

    /**
     * Get entity ID collection from entity collection
     *
     * @param list Entity collection
     * @param <R>  Primary key type
     * @param <T>  Entity type
     * @return Entity ID collection
     */
    static <R extends Serializable, T extends IPrimaryKey<? extends R>> Collection<R> id(Collection<T> list) {
        return list.stream().map(IPrimaryKey::getId).collect(Collectors.toList());
    }
}
