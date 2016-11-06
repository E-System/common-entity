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

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.05.15
 */
public interface IType {

    Object copyObject(Object o, Class<?> returnedClass);

    default Object copyObject(Object o) {
        return copyObject(o, void.class);
    }

    Object getObject(ResultSet rs, String[] names, Class<?> returnedClass) throws SQLException;

    default Object getObject(ResultSet rs, String[] names) throws SQLException {
        return getObject(rs, names, void.class);
    }

    void setObject(PreparedStatement ps, Object value, int index) throws SQLException;
}
