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
