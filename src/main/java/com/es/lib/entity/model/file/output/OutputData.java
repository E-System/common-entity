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

import java.io.File;
import java.io.InputStream;

public abstract class OutputData {

    private String fileName;

    public OutputData(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isStream() {
        return false;
    }

    public boolean isBytes() {
        return false;
    }

    @Deprecated
    public static OutputData create(String fileName, File file) {
        return new OutputFileData(fileName, file);
    }
    
    public static OutputData create(String fileName, String relativePath, File file) {
        return new OutputFileData(fileName, relativePath, file);
    }

    public static OutputData create(String fileName, String contentType, InputStream stream) {
        return new OutputStreamData(fileName, contentType, stream);
    }

    public static OutputData create(String fileName, String contentType, byte[] bytes) {
        return new OutputByteData(fileName, contentType, bytes);
    }
}