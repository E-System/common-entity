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
package com.es.lib.entity.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ToString
public final class Orders {

    private final Collection<Order> items = new ArrayList<>(4);

    Orders(Order... orders) {
        this.items.addAll(Arrays.asList(orders));
    }

    public Orders add(Order... orders) {
        this.items.addAll(Arrays.asList(orders));
        return this;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public String format() {
        if (items.isEmpty()) {
            return "";
        }
        return items.stream().map(Order::format).collect(Collectors.joining(", "));
    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class Order {

        private final String expression;
        private final boolean desc;
        private final Boolean nullsLast;

        String format() {
            return Stream.of(
                expression,
                (desc ? "desc" : "asc"),
                nullsLast == null ? null : (nullsLast ? "nulls last" : "nulls first")
            ).filter(Objects::nonNull).collect(Collectors.joining(" "));
        }
    }
}
