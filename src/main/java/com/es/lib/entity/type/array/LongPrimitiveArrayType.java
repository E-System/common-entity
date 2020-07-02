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
package com.es.lib.entity.type.array;

import com.es.lib.entity.type.CommonArrayType;
import com.es.lib.entity.type.iface.DbTypes;
import org.apache.commons.lang3.ArrayUtils;

import java.sql.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public class LongPrimitiveArrayType extends CommonArrayType {

    @Override
    public Class returnedClass() {
        return long[].class;
    }

    @Override
    public DbTypes.Primitive getDbType() {
        return DbTypes.Primitive.BIGINT;
    }

    @Override
    public Object copyObject(Object o, Class<?> returnedClass) {
        if (o == null) {
            return null;
        }
        return Arrays.copyOf((long[]) o, ((long[]) o).length);
    }

    @Override
    public Object getObject(ResultSet rs, String[] names, Class<?> returnedClass) throws SQLException {
        String col = names[0];
        Array result = rs.getArray(col);
        if (rs.wasNull()) {
            return null;
        }
        return ArrayUtils.toPrimitive(result.getArray());
    }

    @Override
    public void setObject(PreparedStatement ps, Object value, int index) throws SQLException {
        if (value == null || ((Collection) value).isEmpty()) {
            ps.setNull(index, Types.ARRAY);
            return;
        }
        long[] castObject = (long[]) value;
        Long[] values = ArrayUtils.toObject(castObject);

        ps.setArray(index, ps.getConnection().createArrayOf(getDbType().getValue(), values));
    }
}
