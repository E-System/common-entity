/*
 * Copyright (c) E-System LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2018
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
        if (p == null || (p.x == 0 && p.y == 0)) {
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