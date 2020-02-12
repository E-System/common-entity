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

import com.es.lib.common.FileUtil;
import com.es.lib.common.MimeUtil;
import com.es.lib.common.exception.ESRuntimeException;
import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.model.file.*;
import org.apache.commons.io.FilenameUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
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

    public static <T extends IFileStore> T toStore(String basePath, TemporaryFileStore temporaryFile, Supplier<T> fileStoreCreator, Consumer<IOException> exceptionConsumer) {
        FileStorePath storePath;
        try {
            storePath = moveTo(temporaryFile, basePath, FileStoreMode.PERSISTENT, true);
        } catch (IOException e) {
            if (exceptionConsumer != null) {
                exceptionConsumer.accept(e);
                return null;
            } else {
                throw new ESRuntimeException(e);
            }
        }
        T result = fileStoreCreator.get();
        result.setFilePath(storePath.getPath());
        result.setFileName(temporaryFile.getBaseName());
        result.setFileExt(temporaryFile.getExt());
        result.setCrc32(temporaryFile.getCrc32());
        result.setSize(temporaryFile.getSize());
        result.setMime(temporaryFile.getMime());
        FileStoreImageUtil.processAttributes(result, temporaryFile.getFile());
        return result;
    }

    public static <T extends IFileStore> T toStore(String basePath, long crc32, long size, String fileName, String ext, String mime, byte[] data, Supplier<T> fileStoreCreator, Consumer<IOException> exceptionConsumer) {
        FileStorePath storePath = getUniquePath(basePath, FileStoreMode.PERSISTENT, ext);
        T result = fileStoreCreator.get();
        result.setFilePath(storePath.getPath());
        result.setFileName(fileName);
        result.setFileExt(ext);
        result.setCrc32(crc32);
        result.setSize(size);
        result.setMime(mime);
        try {
            Path target = Paths.get(storePath.getFullPath());
            Files.createDirectories(target.getParent());
            Files.copy(
                new ByteArrayInputStream(data),
                target
            );
        } catch (IOException e) {
            if (exceptionConsumer != null) {
                exceptionConsumer.accept(e);
                return null;
            } else {
                throw new ESRuntimeException(e);
            }
        }
        FileStoreImageUtil.processAttributes(result, data);
        return result;
    }

    public static <T extends IFileStore> T copyInStore(String basePath, T fileStore, Supplier<T> fileStoreCreator, Consumer<IOException> exceptionConsumer) {
        FileStorePath storePath = getUniquePath(basePath, FileStoreMode.PERSISTENT, fileStore.getFileExt());
        try {
            Path target = Paths.get(storePath.getFullPath());
            Files.createDirectories(target.getParent());
            Files.copy(
                Paths.get(basePath, fileStore.getFilePath()),
                target
            );
        } catch (IOException e) {
            if (exceptionConsumer != null) {
                exceptionConsumer.accept(e);
                return null;
            } else {
                throw new ESRuntimeException(e);
            }
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
        Path source = Paths.get(basePath, temporaryFile.getPath());
        Path target = Paths.get(result.getFullPath());
        Files.createDirectories(target.getParent());
        Files.copy(source, target);
        if (deleteOriginal) {
            Files.deleteIfExists(source);
        }

        return result;
    }

    public static TemporaryFileStore createTemporary(String basePath, Path from, FileStoreMode mode, Consumer<IOException> exceptionConsumer) {
        if (from == null || !Files.exists(from) || !Files.isReadable(from)) {
            return null;
        }
        String fileName = from.getFileName().toString();
        FileParts fileParts = FileStoreUtil.extractFileParts(fileName);
        FileStorePath path = getUniquePath(basePath, mode, fileParts.getExt());
        Path resultFile = Paths.get(path.getFullPath());
        long crc32;
        long size;
        try (InputStream is = Files.newInputStream(from)) {
            crc32 = FileUtil.copyWithCrc32(is, resultFile);
            size = Files.size(from);
        } catch (IOException e) {
            if (exceptionConsumer != null) {
                exceptionConsumer.accept(e);
                return null;
            } else {
                throw new ESRuntimeException(e);
            }
        }
        return new TemporaryFileStore(
            resultFile,
            path.getPath(),
            fileParts.getFileName(),
            fileParts.getExt(),
            size,
            MimeUtil.get(fileName),
            crc32,
            mode
        );
    }

    public static TemporaryFileStore createTemporary(String basePath, byte[] from, String ext, FileStoreMode mode, Consumer<IOException> exceptionConsumer) {
        if (from == null) {
            return null;
        }
        FileStorePath path = getUniquePath(basePath, mode, ext);
        FileParts fileParts = FileStoreUtil.extractFileParts(path.getPath());
        Path resultFile = Paths.get(path.getFullPath());
        long crc32;
        try (InputStream is = new ByteArrayInputStream(from)) {
            crc32 = FileUtil.copyWithCrc32(is, resultFile);
        } catch (IOException e) {
            if (exceptionConsumer != null) {
                exceptionConsumer.accept(e);
                return null;
            } else {
                throw new ESRuntimeException(e);
            }
        }
        return new TemporaryFileStore(
            resultFile,
            path.getPath(),
            fileParts.getFileName(),
            fileParts.getExt(),
            from.length,
            MimeUtil.getByExt(ext),
            crc32,
            mode
        );
    }

    public static TemporaryFileStore createTemporary(String basePath, InputStream from, String ext, long size, FileStoreMode mode, Consumer<IOException> exceptionConsumer) {
        if (from == null) {
            return null;
        }
        FileStorePath path = getUniquePath(basePath, mode, ext);
        FileParts fileParts = FileStoreUtil.extractFileParts(path.getPath());
        Path resultFile = Paths.get(path.getFullPath());
        long crc32;
        try {
            crc32 = FileUtil.copyWithCrc32(from, resultFile);
        } catch (IOException e) {
            if (exceptionConsumer != null) {
                exceptionConsumer.accept(e);
                return null;
            } else {
                throw new ESRuntimeException(e);
            }
        }
        return new TemporaryFileStore(
            resultFile,
            path.getPath(),
            fileParts.getFileName(),
            fileParts.getExt(),
            size,
            MimeUtil.getByExt(ext),
            crc32,
            mode
        );
    }

    public static TemporaryFileStore createTemporary(String basePath, InputStream from, FileParts fileParts, long size, String mime, ThumbUtil.Generator thumbGenerator, Consumer<IOException> exceptionConsumer) {
        FileStorePath path = getUniquePath(basePath, FileStoreMode.TEMPORARY, fileParts.getExt());
        Path resultFile = Paths.get(path.getFullPath());
        long crc32;
        try {
            crc32 = FileUtil.copyWithCrc32(from, resultFile);
            if (FileStoreUtil.isImage(mime)) {
                ThumbUtil.generate(resultFile, new Thumb(), null, thumbGenerator);
            }
        } catch (IOException e) {
            if (exceptionConsumer != null) {
                exceptionConsumer.accept(e);
                return null;
            } else {
                throw new ESRuntimeException(e);
            }
        }
        return new TemporaryFileStore(
            resultFile,
            path.getPath(),
            fileParts.getFileName(),
            fileParts.getExt(),
            size,
            mime,
            crc32
        );
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
        return FileSystems.getDefault().getSeparator() + part;
    }

    public static void copyContent(Path path, OutputStream outputStream) throws IOException {
        if (Files.exists(path) && Files.isReadable(path)) {
            Files.copy(path, outputStream);
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