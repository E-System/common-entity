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

package com.es.lib.entity.type.iface;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 12.10.15
 */
public interface IJsonType extends IType {

    ObjectMapper OBJECT_MAPPER = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    JsonDbType getDbType();

    default boolean isCreateInstance() {
        return true;
    }

    @Override
    default Object copyObject(Object o, Class<?> returnedClass) {
        try {
            return asObject(asString(o), returnedClass);
        } catch (IOException e) {
            throw new HibernateException("Error copy JSON", e);
        }
    }

    @Override
    default Object getObject(ResultSet rs, String[] names, Class<?> returnedClass) throws SQLException {
        if (rs.getObject(names[0]) == null) {
            if (!isCreateInstance()) {
                return null;
            }
            try {
                return returnedClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new HibernateException("Error create empty object", e);
            }
        }
        PGobject pGobject = (PGobject) rs.getObject(names[0]);
        try {
            return asObject(pGobject.getValue(), returnedClass);
        } catch (IOException e) {
            throw new HibernateException("Error read JSON", e);
        }
    }

    @Override
    default void setObject(PreparedStatement ps, Object value, int index) throws SQLException {
        if (value == null) {
            ps.setNull(index, Types.NULL);
            return;
        }
        String jsonString;
        try {
            jsonString = asString(value);
        } catch (IOException e) {
            throw new HibernateException("Error save JSON", e);
        }
        PGobject pGobject = new PGobject();
        pGobject.setType(getDbType().getValue());
        pGobject.setValue(jsonString);
        ps.setObject(index, pGobject);
    }

    default String asString(Object value) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(value);
    }

    default Object asObject(String value, Class<?> returnedClass) throws IOException {
        return OBJECT_MAPPER.readValue(value, returnedClass);
    }
}
