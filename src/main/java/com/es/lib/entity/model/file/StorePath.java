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
package com.es.lib.entity.model.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * File store path data
 *
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 17.03.18
 */
@Getter
@ToString
@RequiredArgsConstructor
public class StorePath {

    private final Path root;
    private final Path relative;

    public static StorePath create(Path basePath, StoreMode mode, String scope, String ext) {
        return create(basePath, mode, scope, UUID.randomUUID().toString(), ext);
    }

    public static StorePath create(Path basePath, StoreMode mode, String scope, String name, String ext) {
        return new StorePath(
            basePath,
            relative(mode.getPrefix(), scope, name, ext)
        );
    }

    public static Path relative(String prefix, String scope, String name, String ext) {
        LocalDateTime dateTime = LocalDateTime.now();
        return Paths.get(
            (prefix == null ? Paths.get("/") : Paths.get("/", prefix)).toString(),
            (scope == null ? Paths.get("/") : Paths.get("/", scope)).toString(),
            String.valueOf(dateTime.getYear()),
            String.valueOf(dateTime.getMonthValue()),
            String.valueOf(dateTime.getDayOfMonth()),
            dateTime.format(DateTimeFormatter.ofPattern("N")),
            name + "." + ext
        ).normalize();
    }

    public Path toAbsolutePath() {
        return Paths.get(root.toString(), relative.toString());
    }
}
