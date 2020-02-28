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
package com.es.lib.entity.type.geo;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 10.12.17
 */
public class GeoPointType implements UserType {

    @Override
    public int[] sqlTypes() { return new int[]{Types.OTHER}; }

    @Override
    public Class returnedClass() { return GeoPoint.class; }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        if (o == null && o1 == null)
            return true;
        else if (o == null || o1 == null)
            return false;
        return o.equals(o1);
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        if (names.length != 1)
            throw new IllegalArgumentException("names.length != 1, names = " + names);

        String value = resultSet.getString(names[0]);
        if (value == null) {
            return null;
        } else {
            return new GeoPoint(value);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int i, SharedSessionContractImplementor sessionImplementor) throws HibernateException, SQLException {
        GeoPoint p = (GeoPoint) value;
        if (p == null || (p.getX() == 0 && p.getY() == 0)) {
            preparedStatement.setNull(i, Types.OTHER);
        } else {
            preparedStatement.setObject(i, p.getValue(), Types.OTHER);
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        if (o == null)
            return null;

        try {
            return ((GeoPoint) o).clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}