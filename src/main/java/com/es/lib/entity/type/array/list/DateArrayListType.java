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

package com.es.lib.entity.type.array.list;

import com.es.lib.entity.type.CommonArrayListType;
import com.es.lib.entity.type.iface.DbTypes;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public class DateArrayListType extends CommonArrayListType {

    @Override
    public DbTypes.Primitive getDbType() {
        return DbTypes.Primitive.TIMESTAMP;
    }

    @Override
    public Class<?> getItemClass() {
        return Date.class;
    }

    @Override
    public void setObject(PreparedStatement ps, Object value, int index) throws SQLException {
        if (value == null || ((Collection) value).isEmpty()) {
            ps.setNull(index, Types.ARRAY);
            return;
        }
        ps.setArray(index, ps.getConnection().createArrayOf(getDbType().getValue(), ((List<Date>) value).stream().map(v->new Timestamp(v.getTime())).toArray()));
    }

    @Override
    public <T> Object getTypedArrayObject(ResultSet rs, String[] names, Class<?> returnedClass, Class<T> arrayClass) throws SQLException {
        String col = names[0];
        Array result = rs.getArray(col);
        Collection<T> res = new ArrayList<>();
        if (rs.wasNull()) {
            return res;
        }
        for (Timestamp timestamp : (Timestamp[]) result.getArray()) {
            res.add((T) new Date(timestamp.getTime()));
        }
        return res;
    }
}
