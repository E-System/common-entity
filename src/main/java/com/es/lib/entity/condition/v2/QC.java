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

package com.es.lib.entity.condition.v2;

import java.util.function.Supplier;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public final class QC {

    public static final IQStatement STATEMENT_EMPTY = new QStatementEmpty();

    private QC() { }

    public static QCondition always(IQStatement first) {
        return new QCondition(first);
    }

    public static QCondition ifTrue(Supplier<Boolean> decisionFunction, IQStatement first) {
        return new QCondition(decisionFunction, first);
    }

    public static QCondition ifTrue(Supplier<Boolean> decisionFunction, IQStatement first, IQStatement second) {
        return new QCondition(decisionFunction, first, second);
    }

    public static IQStatement stmt() {
        return STATEMENT_EMPTY;
    }

    public static IQStatement stmt(String expression) {
        return new QStatement(expression);
    }

    public static IQStatement stmt(String expression, QParam... params) {
        return new QStatement(expression, params);
    }

    public static QParam param(String name, Supplier<Object> value) {
        return new QParam(name, value);
    }
}
