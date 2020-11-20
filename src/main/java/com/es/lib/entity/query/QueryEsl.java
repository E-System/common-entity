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
package com.es.lib.entity.query;

import com.es.lib.entity.PKeys;
import com.es.lib.entity.iface.IPrimaryKey;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QueryEsl {

    public static Joins joins(Joins.Item... joins) {
        return new Joins(joins);
    }

    public static Joins joins(Collection<Joins.Item> joins) {
        return new Joins(joins);
    }

    public static Conditions conditions(Conditions.Item... conditions) {
        return new Conditions(conditions);
    }

    public static Conditions conditions(Collection<Conditions.Item> conditions) {
        return new Conditions(conditions);
    }

    public static Orders orders(Orders.Item... orders) {
        return new Orders(orders);
    }

    public static Orders orders(Collection<Orders.Item> orders) {
        return new Orders(orders);
    }

    // Joins
    public static Joins.Item inner(String path, String alias) {
        return inner(path, alias, false);
    }

    public static Joins.Item inner(String path, String alias, boolean fetch) {
        return new Joins.Item(Joins.Type.INNER, fetch, path, alias);
    }

    public static Joins.Item right(String path, String alias) {
        return right(path, alias, false);
    }

    public static Joins.Item right(String path, String alias, boolean fetch) {
        return new Joins.Item(Joins.Type.RIGHT, fetch, path, alias);
    }

    public static Joins.Item left(String path, String alias) {
        return left(path, alias, false);
    }

    public static Joins.Item left(String path, String alias, boolean fetch) {
        return new Joins.Item(Joins.Type.LEFT, fetch, path, alias);
    }

    // Conditions
    public static Conditions.Item where(IStatement first) {
        return new Conditions.Item(first);
    }

    public static Conditions.Item where(String expression) {
        return new Conditions.Item(stmt(expression));
    }

    public static Conditions.Item where(String expression, Param... params) {
        return new Conditions.Item(stmt(expression, params));
    }

    public static Conditions.Item where(Supplier<Boolean> predicate, IStatement first) {
        return new Conditions.Item(predicate, first);
    }

    public static Conditions.Item where(String fieldName, String paramName, Supplier<Object> value) {
        return where(fieldName, "=", paramName, value);
    }

    public static Conditions.Item where(String fieldName, String equation, String paramName, Supplier<Object> value) {
        return where(() -> value.get() != null, stmt("and " + fieldName + " " + equation + " :" + paramName, param(paramName, () -> {
            Object val = value.get();
            if (val == null) {
                return null;
            } else if (val instanceof IPrimaryKey<?>) {
                return PKeys.id((IPrimaryKey<? extends Serializable>) val);
            }
            return val;
        })));
    }

    public static Conditions.Item where(Supplier<Boolean> predicate, IStatement first, IStatement second) {
        return new Conditions.Item(predicate, first, second);
    }

    public static IStatement skip() {
        return IStatement.SKIP;
    }

    public static IStatement stmt(String expression) {
        return new Statement(expression);
    }

    public static IStatement stmt(String expression, Param... params) {
        return new Statement(expression, params);
    }

    public static Param param(String name, Supplier<Object> value) {
        return new Param(name, value);
    }

    public static Param like(String name, boolean fromBegin, Supplier<String> value) {
        return new Param(name, () -> Likes.like(value.get(), !fromBegin));
    }

    // Sorting
    public static Collection<Orders.Item> order(String value) {
        return Orders.parse(value);
    }

    public static Orders.Item asc(String expression) {
        return new Orders.Item(expression, false, null);
    }

    public static Orders.Item asc(String expression, boolean nullsLast) {
        return new Orders.Item(expression, false, nullsLast);
    }

    public static Orders.Item desc(String expression) {
        return new Orders.Item(expression, true, null);
    }

    public static Orders.Item desc(String expression, boolean nullsLast) {
        return new Orders.Item(expression, true, nullsLast);
    }
}
