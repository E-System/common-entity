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
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
@ToString
public final class Joins {

    private final Collection<Join> items = new ArrayList<>(4);

    public Joins(Join... joins) {
        this.items.addAll(Arrays.asList(joins));
    }

    public Joins add(Join... joins) {
        this.items.addAll(Arrays.asList(joins));
        return this;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public String format(boolean useFetch) {
        if (isEmpty()) {
            return "";
        }
        return items.stream().map(v -> v.format(useFetch)).collect(Collectors.joining(" "));
    }

    public String findAlias(String path) {
        if (path == null || isEmpty()) {
            return path;
        }
        String result = path;
        for (Join join : items) {
            if (result.startsWith(join.getPath())) {
                result = result.replaceFirst(join.getPath(), join.getAlias());
            }
        }
        return result;
    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class Join {

        private final JoinType joinType;
        private final boolean fetch;
        private final String path;
        private final String alias;

        String format(boolean useFetch) {
            return Stream.of(
                joinType.format(),
                "join",
                fetch && useFetch ? "fetch" : null,
                path,
                StringUtils.isNotBlank(alias) ? alias : null
            ).filter(Objects::nonNull).collect(Collectors.joining(" "));
        }
    }

    public enum JoinType {
        INNER,
        LEFT,
        RIGHT;

        public String format() {
            if (this.equals(INNER)) {
                return null;
            }
            return this.name().toLowerCase();
        }
    }
}
