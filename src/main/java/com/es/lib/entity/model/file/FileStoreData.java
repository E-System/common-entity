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

public class FileStoreData {

    private byte[] bytes;
    private String fileName;
    private String ext;
    private String mime;

    public FileStoreData(byte[] bytes, String fileName, String ext, String mime) {
        this.bytes = bytes;
        this.fileName = fileName;
        this.ext = ext;
        this.mime = mime;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getFileName() {
        return fileName;
    }

    public String getExt() {
        return ext;
    }

    public String getMime() {
        return mime;
    }

    @Override
    public String toString() {
        return "FileStoreData{" +
               "bytes=" + bytes +
               ", fileName='" + fileName + '\'' +
               ", ext='" + ext + '\'' +
               ", mime='" + mime + '\'' +
               '}';
    }
}