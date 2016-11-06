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

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public class Conditions {

    private Collection<Condition.Builder> builders;
    private Collection<Condition> items;

    public Conditions() {
    }

    public Condition.Builder add(boolean firstActive) {
        Condition.Builder builder = new Condition.Builder(this);
        builder.firstActive(firstActive);
        getBuilders().add(builder);
        return builder;
    }

    public Condition.Builder add() {
        return add(true);
    }

    Collection<Condition.Builder> getBuilders() {
        if (builders == null) {
            builders = new LinkedList<>();
        }
        return builders;
    }

    private Collection<Condition> getItems() {
        if (items == null) {
            items = getBuilders().
                    stream().
                    map(Condition.Builder::build).
                    collect(Collectors.toList());
        }
        return items;
    }

    public boolean isEmpty() {
        return getItems().isEmpty();
    }

    public String getStatement() {
        StringBuilder result = new StringBuilder();
        for (Condition condition : getItems()) {
            Statement statement = condition.getStatement();
            result.append(" ");
            if (statement != null && statement.getEquation() != null) {
                result.append(statement.getEquation());
            }
        }
        return result.toString();
    }

    public Map<String, Object> getParameters() {
        Map<String, Object> result = new HashMap<>();
        for (Condition condition : getItems()) {
            Statement statement = condition.getStatement();
            if (statement != null && statement.getParams() != null) {
                result.putAll(statement.getParams());
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Conditions [" +
               "items=" + getItems() +
               ']';
    }
}
