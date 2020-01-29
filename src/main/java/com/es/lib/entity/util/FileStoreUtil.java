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
package com.es.lib.entity.util;

import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.model.file.FileParts;
import com.es.lib.entity.model.file.FileStoreMode;
import com.es.lib.entity.model.file.FileStorePath;
import com.es.lib.entity.model.file.TemporaryFileStore;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * File store util
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 17.03.18
 */
public class FileStoreUtil {

    public static <T extends IFileStore> T toStore(String basePath, TemporaryFileStore temporaryFile, Supplier<T> fileStoreCreator) {
        FileStorePath storePath;
        try {
            storePath = moveTo(temporaryFile, basePath, FileStoreMode.PERSISTENT, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        T result = fileStoreCreator.get();
        result.setFilePath(storePath.getPath());
        result.setFileName(temporaryFile.getBaseName());
        result.setFileExt(temporaryFile.getExt());
        result.setCrc32(temporaryFile.getCrc32());
        result.setSize(temporaryFile.getSize());
        result.setMime(temporaryFile.getMime());
        FileStoreImageUtil.processAttributes(result, temporaryFile.getFile().toPath());
        return result;
    }

    public static <T extends IFileStore> T toStore(String basePath, long crc32, long size, String fileName, String ext, String mime, byte[] data, Supplier<T> fileStoreCreator) {
        FileStorePath storePath = getUniquePath(basePath, FileStoreMode.PERSISTENT, ext);
        T result = fileStoreCreator.get();
        result.setFilePath(storePath.getPath());
        result.setFileName(fileName);
        result.setFileExt(ext);
        result.setCrc32(crc32);
        result.setSize(size);
        result.setMime(mime);
        try {
            FileUtils.writeByteArrayToFile(
                new File(storePath.getFullPath()),
                data
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileStoreImageUtil.processAttributes(result, data);
        return result;
    }

    public static <T extends IFileStore> T copyInStore(String basePath, T fileStore, Supplier<T> fileStoreCreator) {
        FileStorePath storePath = getUniquePath(basePath, FileStoreMode.PERSISTENT, fileStore.getFileExt());
        try {
            FileUtils.copyFile(
                new File(basePath, fileStore.getFilePath()),
                new File(storePath.getFullPath())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        T result = fileStoreCreator.get();
        result.setFilePath(storePath.getPath());
        result.setFileName(fileStore.getFileName());
        result.setFileExt(fileStore.getFileExt());
        result.setCrc32(fileStore.getCrc32());
        result.setSize(fileStore.getSize());
        result.setMime(fileStore.getMime());
        result.setAttributes(fileStore.getAttributes());
        return result;
    }

    public static <T extends IFileStore> T fromStore(String base64Path, Supplier<T> fileStoreCreator) {
        T result = fileStoreCreator.get();
        result.setFilePath(new String(Base64.getUrlDecoder().decode(base64Path)));
        return result;
    }

    public static FileStorePath moveTo(TemporaryFileStore temporaryFile, String basePath, FileStoreMode mode, boolean deleteOriginal) throws IOException {
        FileStorePath result = getUniquePath(basePath, mode, temporaryFile.getExt());
        File source = new File(basePath, temporaryFile.getPath());
        FileUtils.copyFile(source, new File(result.getFullPath()));
        if (deleteOriginal) {
            FileUtils.deleteQuietly(source);
        }

        return result;
    }

    public static FileStorePath getPath(String basePath, FileStoreMode mode, String name, String ext) {
        return new FileStorePath(basePath, FileStoreUtil.getLocalPath(mode.getPrefix(), name, ext));
    }

    public static FileStorePath getUniquePath(String basePath, FileStoreMode mode, String ext) {
        return getPath(basePath, mode, UUID.randomUUID().toString(), ext);
    }

    public static boolean isMime(String mime, String part) {
        return mime != null && mime.contains(part);
    }

    public static boolean isImage(String mime) {
        return isMime(mime, "image");
    }

    public static boolean isImage(TemporaryFileStore file) {
        return file != null && isImage(file.getMime());
    }

    public static boolean isImage(IFileStore file) {
        return file != null && isImage(file.getMime());
    }

    public static String getPathPart(String part) {
        return File.separator + part;
    }

    public static void copyContent(File path, OutputStream outputStream) throws IOException {
        if (path.exists() && path.canRead()) {
            FileUtils.copyFile(path, outputStream);
        }
    }

    public static String getLocalPath(String prefix, String name, String ext) {
        LocalDateTime dateTime = LocalDateTime.now();
        return Stream.of(
            prefix,
            String.valueOf(dateTime.getYear()),
            String.valueOf(dateTime.getMonthValue()),
            String.valueOf(dateTime.getDayOfMonth()),
            dateTime.format(DateTimeFormatter.ofPattern("N")),
            name + "." + ext
        ).filter(Objects::nonNull)
                     .map(FileStoreUtil::getPathPart)
                     .collect(Collectors.joining());
    }

    public static FileParts extractFileParts(String fileName) {
        return new FileParts(
            FilenameUtils.getBaseName(fileName),
            FilenameUtils.getExtension(fileName).toLowerCase()
        );
    }
}