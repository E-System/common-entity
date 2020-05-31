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

import com.es.lib.entity.util.Likes;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public class Statement {

    private String equation;
    private Map<String, Object> params;

    public Statement(String equation, Map<String, Object> params) {
        this.equation = equation;
        this.params = params;
    }

    public String getEquation() {
        return equation;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "Statement [" +
               "equation='" + equation + "'" +
               ", params=" + params +
               ']';
    }

    public static class Builder {

        private Condition.Builder conditionBuilder;
        private String equation;
        private ParamsBuilder paramsBuilder;

        Builder(final Condition.Builder conditionBuilder, final String equation) {
            this.conditionBuilder = conditionBuilder;
            this.equation = equation;
        }

        public ParamsBuilder param(final String key, final Object value) {
            if (paramsBuilder == null) {
                paramsBuilder = new ParamsBuilder(conditionBuilder, key, value);
            }
            return paramsBuilder;
        }

        public Condition.Builder add(final boolean firstActive) {
            return conditionBuilder.conditions.add(firstActive);
        }

        public Condition.Builder add() {
            return conditionBuilder.conditions.add();
        }

        public Builder second(final String eq) {
            return conditionBuilder.statement2(eq);
        }

        Statement internalBuild() {
            return new Statement(equation, paramsBuilder != null ? paramsBuilder.params : null);
        }

        public Condition build() {
            return conditionBuilder.build();
        }

        public Conditions end() {
            return conditionBuilder.conditions;
        }

        public static class ParamsBuilder {

            private Condition.Builder conditionBuilder;
            private Map<String, Object> params;

            ParamsBuilder(final Condition.Builder conditionBuilder, final String key, final Object value) {
                this.conditionBuilder = conditionBuilder;
                getParams().put(key, value);
            }

            public ParamsBuilder param(final String key, final Object value) {
                getParams().put(key, value);
                return this;
            }

            public ParamsBuilder likeAny(final String key, final String value) {
                return param(key, Likes.any(value));
            }

            public ParamsBuilder likeBegin(final String key, final String value) {
                return param(key, Likes.begin(value));
            }

            public ParamsBuilder like(final String key, final String value, boolean anyMatch) {
                return param(key, Likes.like(value, anyMatch));
            }

            public Builder second(final String eq) {
                return conditionBuilder.statement2(eq);
            }

            private Map<String, Object> getParams() {
                if (params == null) {
                    params = new HashMap<>();
                }
                return params;
            }

            Condition internalBuild() {
                return conditionBuilder.build();
            }

            public Condition.Builder add(final boolean firstActive) {
                return conditionBuilder.conditions.add(firstActive);
            }

            public Condition.Builder add() {
                return conditionBuilder.conditions.add();
            }

            public Conditions end() {
                return conditionBuilder.conditions;
            }
        }
    }
}
