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

import com.es.lib.entity.util.Likes;

import java.util.function.Supplier;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public final class QueryEsl {

    private QueryEsl() { }

    public static Joins joins(Joins.Join... joins) {
        return new Joins(joins);
    }

    public static Conditions conditions(Condition... conditions) {
        return new Conditions(conditions);
    }

    public static Orders orders(Orders.Order... orders) {
        return new Orders(orders);
    }

    // Joins
    public static Joins.Join inner(String path, String alias) {
        return inner(path, alias, false);
    }

    public static Joins.Join inner(String path, String alias, boolean fetch) {
        return new Joins.Join(Joins.JoinType.INNER, fetch, path, alias);
    }

    public static Joins.Join right(String path, String alias) {
        return right(path, alias, false);
    }

    public static Joins.Join right(String path, String alias, boolean fetch) {
        return new Joins.Join(Joins.JoinType.RIGHT, fetch, path, alias);
    }

    public static Joins.Join left(String path, String alias) {
        return left(path, alias, false);
    }

    public static Joins.Join left(String path, String alias, boolean fetch) {
        return new Joins.Join(Joins.JoinType.LEFT, fetch, path, alias);
    }

    // Conditions
    public static Condition where(IStatement first) {
        return new Condition(first);
    }

    public static Condition where(String expression) {
        return new Condition(stmt(expression));
    }

    public static Condition where(String expression, Param... params) {
        return new Condition(stmt(expression, params));
    }

    public static Condition where(Supplier<Boolean> predicate, IStatement first) {
        return new Condition(predicate, first);
    }

    public static Condition where(Supplier<Boolean> predicate, IStatement first, IStatement second) {
        return new Condition(predicate, first, second);
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
    public static Orders.Order asc(String expression) {
        return new Orders.Order(expression, false, null);
    }

    public static Orders.Order asc(String expression, boolean nullsLast) {
        return new Orders.Order(expression, false, nullsLast);
    }

    public static Orders.Order desc(String expression) {
        return new Orders.Order(expression, true, null);
    }

    public static Orders.Order desc(String expression, boolean nullsLast) {
        return new Orders.Order(expression, true, nullsLast);
    }
}
