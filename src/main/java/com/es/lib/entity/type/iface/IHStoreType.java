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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.05.15
 */
public interface IHStoreType extends IType {

    @Override
    default Object copyObject(Object o, Class<?> returnedClass) {
        if (Objects.isNull(o)) {
            return null;
        }
        Map m = (Map) o;
        return new HashMap(m);
    }

    @Override
    default Object getObject(ResultSet rs, String[] names, Class<?> returnedClass) throws SQLException {
        String col = names[0];
        Object result = rs.getObject(col);
        if (rs.wasNull()) {
            return new HashMap<>();
        }
        return result;
    }

    @Override
    default void setObject(PreparedStatement ps, Object value, int index) throws SQLException {
        if (Objects.isNull(value) || ((Map) value).isEmpty()) {
            ps.setNull(index, Types.OTHER);
        } else {
            ps.setObject(index, value);
        }
    }
}
