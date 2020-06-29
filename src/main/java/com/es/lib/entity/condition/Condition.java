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

package com.es.lib.entity.condition;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
@ToString
@RequiredArgsConstructor
public class Condition {

    private final boolean firstActive;
    private final Statement statement1;
    private final Statement statement2;

    public Condition(Statement statement1) {
        this(true, statement1, null);
    }

    public Statement getStatement() {
        return firstActive ? statement1 : statement2;
    }

    public static class Builder {

        Conditions conditions;
        private boolean firstActive;
        private Statement.Builder statement1;
        private Statement.Builder statement2;

        public Builder(Conditions conditions) {
            this.conditions = conditions;
        }

        Builder firstActive(final boolean value) {
            this.firstActive = value;
            return this;
        }

        public Statement.Builder first(final String eq) {
            if (statement1 == null) {
                statement1 = new Statement.Builder(this, eq);
            }
            return statement1;
        }

        public Conditions end() {
            return conditions;
        }

        Statement.Builder statement2(final String eq) {
            if (statement2 == null) {
                statement2 = new Statement.Builder(this, eq);
            }
            return statement2;
        }

        Condition build() {
            return new Condition(firstActive, statement1 != null ? statement1.internalBuild() : null, statement2 != null ? statement2.internalBuild() : null);
        }
    }
}
