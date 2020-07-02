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

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 03.02.2018
 */
public interface DbTypes {

    enum Json {

        JSON("json"),
        JSONB("jsonb");

        private String value;

        Json(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    enum Primitive {

        VARCHAR("varchar"),
        SMALLINT("smallint"),
        INT("int"),
        BIGINT("bigint"),
        TIMESTAMPTZ("timestamptz");

        private String value;

        Primitive(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
