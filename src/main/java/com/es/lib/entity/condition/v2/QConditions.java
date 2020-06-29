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

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
@Getter
@ToString
public class QConditions {

    private Collection<QCondition> conditions;

    public QConditions() {
    }

    public QConditions(QCondition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    public QConditions add(QCondition... conditions) {
        this.conditions.addAll(Arrays.asList(conditions));
        return this;
    }

    public boolean isEmpty() {
        return conditions == null || conditions.isEmpty();
    }

    public boolean isBreakSelect() {
        if (isEmpty()) {
            return false;
        }
        for (QCondition condition : conditions) {
            IQStatement statement = condition.getStatement();
            if (statement != null && statement.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public String getStatement() {
        if (isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (QCondition condition : getConditions()) {
            IQStatement statement = condition.getStatement();
            result.append(" ");
            if (statement != null && !statement.isEmpty()) {
                QStatement qStatement = (QStatement) statement;
                result.append(qStatement.getExpression());
            }
        }
        return result.toString();
    }

    public Map<String, Object> getParameters() {
        Map<String, Object> result = new HashMap<>();
        for (QCondition condition : getConditions()) {
            IQStatement statement = condition.getStatement();
            if (statement != null && !statement.isEmpty()) {
                QStatement qStatement = (QStatement) statement;
                if (!qStatement.isEmptyParams()) {
                    result.putAll(
                        qStatement.getParams()
                                  .stream()
                                  .collect(
                                      Collectors.toMap(
                                          QParam::getName,
                                          v -> v.getValue().get()
                                      )
                                  )
                    );
                }
            }
        }
        return result;
    }
}
