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
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ToString
public final class Orders {

    private final Collection<Item> items = new ArrayList<>(4);

    Orders(Item... orders) {
        this.items.addAll(Arrays.asList(orders));
    }

    Orders(Collection<Item> orders) {
        if (orders != null) {
            this.items.addAll(orders);
        }
    }

    public Orders add(Item... orders) {
        this.items.addAll(Arrays.asList(orders));
        return this;
    }

    public Orders add(Collection<Item> orders) {
        if (orders != null) {
            this.items.addAll(orders);
        }
        return this;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public String format() {
        if (items.isEmpty()) {
            return "";
        }
        return items.stream().map(Item::format).collect(Collectors.joining(", "));
    }

    public static Collection<Item> parse(String value) {
        if (StringUtils.isBlank(value)) {
            return new ArrayList<>();
        }
        Collection<Item> result = new ArrayList<>();
        for (String part : value.trim().split(",")) {
            Item item = parseItem(part);
            if (item != null) {
                result.add(item);
            }
        }
        return result;
    }

    private static Item parseItem(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        String expression = value
            .trim()
            .replaceAll("desc", "")
            .replaceAll("asc", "")
            .replaceAll("nulls last", "")
            .replaceAll("nulls first", "").trim();
        boolean desc = value.contains("desc");
        Boolean nullsLast = null;
        if (value.contains("nulls last")) {
            nullsLast = true;
        } else if (value.contains("nulls first")) {
            nullsLast = false;
        }
        return new Item(expression, desc, nullsLast);
    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class Item {

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
