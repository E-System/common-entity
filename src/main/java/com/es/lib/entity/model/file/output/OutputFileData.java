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
package com.es.lib.entity.model.file.output;

import lombok.Getter;

import java.io.File;

@Getter
public class OutputFileData extends OutputData {

    private String relativePath;
    private File file;

    @Deprecated
    public OutputFileData(String fileName, File file) {
        super(fileName);
        this.file = file;
    }

    public OutputFileData(String fileName, String relativePath, File file) {
        super(fileName);
        this.relativePath = relativePath;
        this.file = file;
    }
}