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
package com.es.lib.entity.model.file;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Path type
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum StoreMode {
    /**
     * For stored files
     */
    PERSISTENT(null),
    /**
     * For temporary upload files
     */
    TEMPORARY("temporary"),
    /**
     * For internal usage (email send, etc....)
     */
    SYSTEM("system");

    private final String prefix;
}