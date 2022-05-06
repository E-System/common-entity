package com.es.lib.entity.dialect.function;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

import java.util.List;

public class ArrayCast implements SQLFunction {

    public static final String NAME = "es_array_cast";

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public boolean hasParenthesesIfNoArguments() {
        return true;
    }

    @Override
    public Type getReturnType(Type firstArgumentType, Mapping mapping) throws QueryException {
        return firstArgumentType;
    }

    @Override
    public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory) throws QueryException {
        final Object first = arguments.get(0);
        final Object second = arguments.get(1);
        return "cast(string_to_array(cast(" + first + " AS TEXT),',') AS " + second.toString().replaceAll("'", "") + ")";
    }
}
