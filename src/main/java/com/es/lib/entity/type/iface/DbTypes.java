/*
 * Copyright 2020 E-System LLC
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
package com.es.lib.entity.type.iface;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 03.02.2018
 */
public interface DbTypes {

    @Getter
    @RequiredArgsConstructor
    enum Json {

        JSON("json"),
        JSONB("jsonb");

        private final String value;
    }

    @Getter
    @RequiredArgsConstructor
    enum Primitive {

        VARCHAR("varchar"),
        SMALLINT("smallint"),
        INT("int"),
        BIGINT("bigint"),
        TIMESTAMPTZ("timestamptz");

        private final String value;
    }
}
