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
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
@Getter
@ToString
public class Statement extends IStatement {

    private final String expression;
    private final Collection<Param> params = new ArrayList<>(1);

    public Statement(String expression) {
        super(false);
        this.expression = expression;
    }

    public Statement(String expression, Param... params) {
        super(false);
        this.expression = expression;
        this.params.addAll(Arrays.asList(params));
    }

    public boolean isEmptyParams() {
        return params.isEmpty();
    }
}
