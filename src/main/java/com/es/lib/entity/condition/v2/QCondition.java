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
public class QCondition {

    private static final Supplier<Boolean> ALWAYS_TRUE = () -> true;
    private Supplier<Boolean> decisionFunction;
    private IQStatement first;
    private IQStatement second;

    public QCondition(IQStatement first) {
        this.decisionFunction = ALWAYS_TRUE;
        this.first = first;
    }

    public QCondition(Supplier<Boolean> decisionFunction, IQStatement first) {
        this.decisionFunction = decisionFunction;
        this.first = first;
    }

    public QCondition(Supplier<Boolean> decisionFunction, IQStatement first, IQStatement second) {
        this.decisionFunction = decisionFunction;
        this.first = first;
        this.second = second;
    }

    public Supplier<Boolean> getDecisionFunction() {
        return decisionFunction;
    }

    public IQStatement getFirst() {
        return first;
    }

    public IQStatement getSecond() {
        return second;
    }

    public IQStatement getStatement() {
        return decisionFunction.get() ? first : second;
    }

    @Override
    public String toString() {
        return "QCondition [" +
               "decisionFunction=" + decisionFunction +
               ", first=" + first +
               ", second=" + second +
               ']';
    }
}
