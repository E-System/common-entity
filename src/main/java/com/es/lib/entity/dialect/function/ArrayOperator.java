package com.es.lib.entity.dialect.function;

import lombok.RequiredArgsConstructor;
import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import java.util.List;

@RequiredArgsConstructor
public class ArrayOperator implements SQLFunction {

    private final String operator;

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
        return StandardBasicTypes.BOOLEAN;
    }

    @Override
    public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory) throws QueryException {
        final Object first = arguments.get(0);
        final Object second = arguments.get(1);
        return "(" + first + operator + second + ")";
    }
}
