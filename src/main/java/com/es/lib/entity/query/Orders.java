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

public final class Orders {

    private final Collection<Order> items = new ArrayList<>(4);

    public Orders(Order... orders) {
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
