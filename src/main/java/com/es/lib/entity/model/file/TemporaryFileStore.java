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

import com.es.lib.entity.iface.file.IStore;
import com.es.lib.entity.util.FileStoreUtil;
import lombok.Getter;
import lombok.ToString;

import java.io.File;
import java.nio.file.Path;
import java.util.Base64;

/**
 * Temporary upload file info
 *
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 17.03.18
 */
@Getter
@ToString
public class TemporaryFileStore implements IStore {

    private Path file;

    private String filePath;
    private String fileName;
    private String fileExt;

    private long size;
    private String mime;
    private long crc32;
    private FileStoreMode mode;

    private String base64Path;

    public TemporaryFileStore(Path file, String filePath, String fileName, String fileExt, long size, String mime, long crc32) {
        this(file, filePath, fileName, fileExt, size, mime, crc32, FileStoreMode.TEMPORARY);
    }

    public TemporaryFileStore(Path file, String filePath, String fileName, String fileExt, long size, String mime, long crc32, FileStoreMode mode) {
        this.file = file;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileExt = fileExt;
        this.size = size;
        this.mime = mime;
        this.crc32 = crc32;
        this.mode = mode;
        this.base64Path = Base64.getUrlEncoder().encodeToString(filePath.getBytes());
    }

    public String getModeRelativePath() {
        String result = getFilePath();
        if (mode == null || mode.equals(FileStoreMode.PERSISTENT)) {
            return result;
        }
        if (result.startsWith("/" + mode.getPrefix())) {
            result = result.replaceFirst("/" + mode.getPrefix(), "");
        }
        return result;
    }

    public String getFullName() {
        return getFileName() + "." + getFileExt();
    }

    public boolean isImage() {
        return FileStoreUtil.isImage(this);
    }
}