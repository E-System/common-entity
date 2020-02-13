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

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * File store path data
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 17.03.18
 */
public class FileStorePath {

    private Path basePath;
    private Path path;

    public FileStorePath(Path basePath, Path path) {
        this.basePath = basePath;
        this.path = path;
    }

    public Path getBasePath() {
        return basePath;
    }

    public Path getPath() {
        return path;
    }

    public Path getFullPath() {
        return Paths.get(basePath.toString(), path.toString());
    }

    @Override
    public String toString() {
        return "FileStorePath{" +
               "basePath='" + basePath + '\'' +
               ", path='" + path + '\'' +
               '}';
    }
}
