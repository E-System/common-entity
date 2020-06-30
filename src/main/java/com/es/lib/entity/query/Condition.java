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

import java.util.function.Supplier;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
@Getter
@ToString
@RequiredArgsConstructor
public class Condition {

    private final Supplier<Boolean> predicate;
    private final IStatement first;
    private final IStatement second;

    public Condition(IStatement first) {
        this(null, first, null);
    }

    public Condition(Supplier<Boolean> predicate, IStatement first) {
        this(predicate, first, null);
    }

    public IStatement getStatement() {
        return predicate == null || predicate.get() ? first : second;
    }
}
