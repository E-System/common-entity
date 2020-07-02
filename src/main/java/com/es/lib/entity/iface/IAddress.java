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
package com.es.lib.entity.iface;

import com.es.lib.common.collection.Items;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Интерфейс адресного объекта
 *
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 25.09.15
 */
public interface IAddress<PK extends Serializable> extends IPrimaryKey<PK> {

    Map<String, String> getParts();

    void setParts(Map<String, String> parts);

    /**
     * Безопасно получить часть адреса по имени
     *
     * @param <PK>    тип уникального идентификатора объекта адреса
     * @param address объект адреса
     * @param name    имя части адреса
     * @return часть адреса
     */
    static <PK extends Serializable> String safeGetPart(IAddress<PK> address, String name) {
        if (isNull(address)) {
            return null;
        }
        return address.getParts().get(name);
    }

    /**
     * Проверка на пустой адрес
     *
     * @param <PK>    тип уникального идентификатора объекта адреса
     * @param address объект адреса
     * @return true - если address == null || address.parts == null
     */
    static <PK extends Serializable> boolean isNull(IAddress<PK> address) {
        return address == null || address.getParts() == null;
    }

    /**
     * Проверка на пустой адрес
     *
     * @param <PK>    тип уникального идентификатора объекта адреса
     * @param address объект адреса
     * @return true - если address == null || address.parts == null || address.parts is empty
     */
    static <PK extends Serializable> boolean isEmpty(IAddress<PK> address) {
        return isNull(address) || Items.isEmpty(address.getParts());
    }

    /**
     * Очистить элементы адреса
     *
     * @param address объект адреса
     * @return объект адреса с пустым списком частей
     */
    static IAddress<?> clear(IAddress<?> address) {
        if (address == null) {
            return null;
        }
        if (address.getParts() == null) {
            return address;
        }
        address.getParts().clear();
        return address;
    }

    /**
     * Safe get parts of address
     *
     * @param address address object
     * @param <PK>    address unique identifier type
     * @return parts of address or empty parts
     */
    static <PK extends Serializable> Map<String, String> safeGetParts(IAddress<PK> address) {
        if (isNull(address)) {
            return new HashMap<>();
        }
        return new HashMap<>(address.getParts());
    }
}
