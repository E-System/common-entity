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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
@Getter
@ToString
public class Conditions {

    private final Collection<Item> items = new ArrayList<>();

    Conditions(Item... conditions) {
        this.items.addAll(Arrays.asList(conditions));
    }

    Conditions(Collection<Item> conditions) {
        if (conditions != null) {
            this.items.addAll(conditions);
        }
    }

    public Conditions add(Item... conditions) {
        this.items.addAll(Arrays.asList(conditions));
        return this;
    }

    public Conditions add(Collection<Item> conditions) {
        if (conditions != null) {
            this.items.addAll(conditions);
        }
        return this;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean isSkipSelect() {
        if (isEmpty()) {
            return false;
        }
        for (Item condition : items) {
            IStatement statement = condition.getStatement();
            if (statement != null && statement.isSkip()) {
                return true;
            }
        }
        return false;
    }

    public String format() {
        if (isEmpty()) {
            return "";
        }
        return items.stream().flatMap(v -> Stream.of(v.getStatement()))
            .filter(v -> v != null && !v.isSkip())
            .map(v -> ((Statement) v).getExpression())
            .collect(Collectors.joining(" "));
    }

    public Map<String, Object> parameters() {
        Map<String, Object> result = new HashMap<>();
        for (Item condition : getItems()) {
            IStatement statement = condition.getStatement();
            if (statement != null && !statement.isSkip()) {
                Statement qStatement = (Statement) statement;
                if (!qStatement.isEmptyParams()) {
                    result.putAll(
                        qStatement.getParams()
                            .stream()
                            .map(v -> Pair.of(v.getName(), v.getValue().get()))
                            .filter(v -> v.getValue() != null)
                            .collect(
                                Collectors.toMap(Pair::getKey, Pair::getValue)
                            )
                    );
                }
            }
        }
        return result;
    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class Item {

        private final Supplier<Boolean> predicate;
        private final IStatement first;
        private final IStatement second;

        public Item(IStatement first) {
            this(null, first, null);
        }

        public Item(Supplier<Boolean> predicate, IStatement first) {
            this(predicate, first, null);
        }

        public IStatement getStatement() {
            return predicate == null || predicate.get() ? first : second;
        }
    }
}
