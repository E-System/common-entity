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

import com.es.lib.entity.util.FileStoreUtil;

import java.io.File;
import java.util.Base64;

/**
 * Temporary upload file info
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 17.03.18
 */
public class TemporaryFileStore {

    private File file;

    private String path;
    private String baseName;
    private String ext;

    private long size;
    private String mime;
    private long crc32;

    private String base64Path;

    public TemporaryFileStore(File file, String path, String baseName, String ext, long size, String mime, long crc32) {
        this.file = file;
        this.path = path;
        this.baseName = baseName;
        this.ext = ext;
        this.size = size;
        this.mime = mime;
        this.crc32 = crc32;
        this.base64Path = Base64.getUrlEncoder().encodeToString(path.getBytes());
    }

    public File getFile() {
        return file;
    }

    public String getPath() {
        return path;
    }

    public String getBaseName() {
        return baseName;
    }

    public String getExt() {
        return ext;
    }

    public String getFullName() {
        return getBaseName() + "." + getExt();
    }

    public long getSize() {
        return size;
    }

    public String getMime() {
        return mime;
    }

    public long getCrc32() {
        return crc32;
    }

    public String getBase64Path() {
        return base64Path;
    }

    public boolean isImage() {
        return FileStoreUtil.isImage(this);
    }

    @Override
    public String toString() {
        return "TemporaryFileStore{" +
               "file=" + file +
               ", path='" + path + '\'' +
               ", baseName='" + baseName + '\'' +
               ", ext='" + ext + '\'' +
               ", size=" + size +
               ", mime='" + mime + '\'' +
               ", crc32=" + crc32 +
               ", base64Path='" + base64Path + '\'' +
               '}';
    }
}