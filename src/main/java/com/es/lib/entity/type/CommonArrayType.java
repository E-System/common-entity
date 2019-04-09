package com.es.lib.entity.type;

import com.es.lib.entity.type.iface.IArrayListType;
import com.es.lib.entity.type.iface.IArrayType;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Objects;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 03.02.2018
 */
public abstract class CommonArrayType implements UserType, IArrayType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.ARRAY};
    }

    @Override
    public Class returnedClass() {
        return Object[].class;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) {
        return cached;
    }

    @Override
    public Object deepCopy(Object o) {
        return copyObject(o);
    }

    @Override
    public Serializable disassemble(Object o) {
        return (Serializable) o;
    }

    @Override
    public boolean equals(Object o1, Object o2) {
        return Objects.equals(o1, o2);
    }

    @Override
    public int hashCode(Object o) {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] strings, SessionImplementor si, Object o) throws SQLException {
        return getObject(rs, strings, returnedClass());
    }

    @Override
    public void nullSafeSet(PreparedStatement ps, Object o, int i, SessionImplementor si) throws SQLException {
        setObject(ps, o, i);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) {
        return original;
    }
}
