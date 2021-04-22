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
import org.apache.commons.lang3.StringUtils;

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

    static String toStringShort(IAddress<? extends Serializable> item) {
        return toStringShort(item.getParts());
    }

    static String toStringShort(Map<String, String> parts) {
        return process("", parts, false);
    }

    static String toStringFull(IAddress<? extends Serializable> item) {
        return toStringFull(item.getParts());
    }

    static String toStringFull(Map<String, String> parts) {
        String result = "";
        result = fetch(result, parts, null, null, AddressKeys.ZIPCODE, null, null);
        result = fetch(result, parts, AddressKeys.Region.TYPE_SHORT, null, AddressKeys.Region.VALUE, ", ", " ");
        return process(result, parts, true);
    }

    static String process(String result, Map<String, String> parts, boolean additional) {
        result = fetch(result, parts, AddressKeys.Area.TYPE_SHORT, null, AddressKeys.Area.VALUE, additional ? ", " : null, ". ");
        result = fetch(result, parts, AddressKeys.City.TYPE_SHORT, null, AddressKeys.City.VALUE, ", ", ". ");
        result = fetch(result, parts, AddressKeys.Locality.TYPE_SHORT, null, AddressKeys.Locality.VALUE, ", ", ". ");
        result = fetch(result, parts, AddressKeys.Street.TYPE_SHORT, null, AddressKeys.Street.VALUE, ", ", ". ");
        result = fetch(result, parts, null, null, AddressKeys.HOUSE, " ", null);
        result = fetch(result, parts, null, null, AddressKeys.HOUSING, " ", "кор. ");
        result = fetch(result, parts, null, null, AddressKeys.BUILDING, " ", "стр. ");
        result = fetch(result, parts, AddressKeys.FLAT_TYPE, "кв", AddressKeys.FLAT, " ", ". ");
        return fetch(result, parts, null, null, AddressKeys.QUALIFICATION, " ", null);
    }

    static String fetch(String result, Map<String, String> parts, String typeCode, String defaultType, String valueCode, String divider, String valuePrefix) {
        String value = parts.get(valueCode);
        if (typeCode == null && StringUtils.isBlank(value)) {
            return result;
        }
        String type = parts.get(typeCode);
        if (StringUtils.isBlank(type)) {
            type = defaultType;
        }
        if (StringUtils.isBlank(type) && StringUtils.isBlank(value)) {
            return result;
        }
        if (divider != null && StringUtils.isNotBlank(result)) {
            result += divider;
        }
        result += StringUtils.defaultString(type, "");
        if (valuePrefix != null) {
            result += valuePrefix;
        }
        result += value;
        return result;
    }
}
