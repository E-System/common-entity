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
package com.es.lib.entity;

import com.es.lib.common.exception.ESRuntimeException;
import com.es.lib.common.file.FileName;
import com.es.lib.common.file.IO;
import com.es.lib.common.file.Images;
import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.iface.file.IStore;
import com.es.lib.entity.model.file.StoreMode;
import com.es.lib.entity.model.file.StorePath;
import com.es.lib.entity.model.file.TemporaryFileStore;
import com.es.lib.entity.model.file.Thumb;
import com.es.lib.entity.model.file.code.IFileStoreAttrs;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * File store util
 *
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 17.03.18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileStores {

    public static <T extends IFileStore> T toStore(Path basePath, String scope, TemporaryFileStore temporaryFile, Set<String> checkers, Supplier<T> fileStoreCreator, Consumer<IOException> exceptionConsumer) {
        StorePath storePath;
        try {
            storePath = moveTo(temporaryFile, basePath, StoreMode.PERSISTENT, scope, false);
        } catch (IOException e) {
            if (exceptionConsumer != null) {
                exceptionConsumer.accept(e);
                return null;
            } else {
                throw new ESRuntimeException(e);
            }
        }
        T result = fill(temporaryFile, storePath, fileStoreCreator.get());
        processAttributes(result, storePath.toAbsolutePath(), checkers);
        return result;
    }

    public static <T extends IFileStore> T toStore(Path basePath, String scope, long crc32, long size, String fileName, String ext, String mime, byte[] data, Set<String> checkers, Supplier<T> fileStoreCreator, Consumer<IOException> exceptionConsumer) {
        StorePath storePath = StorePath.create(basePath, StoreMode.PERSISTENT, scope, ext);
        T result = fileStoreCreator.get();
        result.setFilePath(storePath.getRelative().toString());
        result.setFileName(fileName);
        result.setFileExt(ext);
        result.setCrc32(crc32);
        result.setSize(size);
        result.setMime(mime);
        try {
            Files.createDirectories(storePath.toAbsolutePath().getParent());
            Files.copy(new ByteArrayInputStream(data), storePath.toAbsolutePath());
        } catch (IOException e) {
            if (exceptionConsumer != null) {
                exceptionConsumer.accept(e);
                return null;
            } else {
                throw new ESRuntimeException(e);
            }
        }
        processAttributes(result, data, checkers);
        return result;
    }

    public static <T extends IFileStore> T copyInStore(Path basePath, String scope, T fileStore, Supplier<T> fileStoreCreator, Consumer<IOException> exceptionConsumer) {
        StorePath storePath = StorePath.create(basePath, StoreMode.PERSISTENT, scope, fileStore.getFileExt());
        try {
            Files.createDirectories(storePath.toAbsolutePath().getParent());
            Files.copy(Paths.get(basePath.toString(), fileStore.getFilePath()), storePath.toAbsolutePath());
        } catch (IOException e) {
            if (exceptionConsumer != null) {
                exceptionConsumer.accept(e);
                return null;
            } else {
                throw new ESRuntimeException(e);
            }
        }

        T result = fill(fileStore, storePath, fileStoreCreator.get());
        result.setAttributes(fileStore.getAttributes());
        return result;
    }

    private static <T extends IFileStore> T fill(IStore source, StorePath storePath, T result) {
        result.setFilePath(storePath.getRelative().toString());
        result.setFileName(source.getFileName());
        result.setFileExt(source.getFileExt());
        result.setCrc32(source.getCrc32());
        result.setSize(source.getSize());
        result.setMime(source.getMime());
        return result;
    }

    public static <T extends IFileStore> T fromStore(String base64Path, Supplier<T> fileStoreCreator) {
        T result = fileStoreCreator.get();
        result.setFilePath(new String(Base64.getUrlDecoder().decode(base64Path)));
        return result;
    }

    public static StorePath moveTo(TemporaryFileStore temporaryFile, Path basePath, StoreMode mode, String scope, boolean deleteOriginal) throws IOException {
        StorePath result = StorePath.create(basePath, mode, scope, temporaryFile.getFileExt());
        Path source = Paths.get(basePath.toString(), temporaryFile.getFilePath());
        Files.createDirectories(result.toAbsolutePath().getParent());
        Files.copy(source, result.toAbsolutePath());
        if (deleteOriginal) {
            Files.deleteIfExists(source);
        }
        return result;
    }

    public static TemporaryFileStore createTemporary(Path basePath, Path from, StoreMode mode, String scope, Consumer<IOException> exceptionConsumer) {
        if (from == null || !Files.exists(from) || !Files.isReadable(from)) {
            return null;
        }
        FileName fileName = FileName.create(from);
        StorePath path = StorePath.create(basePath, mode, scope, fileName.getExt());
        long crc32;
        long size;
        try (InputStream is = Files.newInputStream(from)) {
            crc32 = IO.copyWithCrc32(is, path.toAbsolutePath());
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
            path.toAbsolutePath(),
            path.getRelative().toString(),
            fileName.getName(),
            fileName.getExt(),
            size,
            IO.mime(from),
            crc32,
            mode
        );
    }

    public static TemporaryFileStore createTemporary(Path basePath, byte[] from, String ext, StoreMode mode, String scope, Consumer<IOException> exceptionConsumer) {
        if (from == null) {
            return null;
        }
        StorePath path = StorePath.create(basePath, mode, scope, ext);
        FileName fileName = FileName.create(path.getRelative());
        long crc32;
        try (InputStream is = new ByteArrayInputStream(from)) {
            crc32 = IO.copyWithCrc32(is, path.toAbsolutePath());
        } catch (IOException e) {
            if (exceptionConsumer != null) {
                exceptionConsumer.accept(e);
                return null;
            } else {
                throw new ESRuntimeException(e);
            }
        }
        return new TemporaryFileStore(
            path.toAbsolutePath(),
            path.getRelative().toString(),
            fileName.getName(),
            fileName.getExt(),
            from.length,
            IO.mime(ext),
            crc32,
            mode
        );
    }

    public static TemporaryFileStore createTemporary(Path basePath, InputStream from, String ext, long size, StoreMode mode, String scope, Consumer<IOException> exceptionConsumer) {
        if (from == null) {
            return null;
        }
        StorePath path = StorePath.create(basePath, mode, scope, ext);
        FileName fileName = FileName.create(path.getRelative());
        long crc32;
        try {
            crc32 = IO.copyWithCrc32(from, path.toAbsolutePath());
        } catch (IOException e) {
            if (exceptionConsumer != null) {
                exceptionConsumer.accept(e);
                return null;
            } else {
                throw new ESRuntimeException(e);
            }
        }
        return new TemporaryFileStore(
            path.toAbsolutePath(),
            path.getRelative().toString(),
            fileName.getName(),
            fileName.getExt(),
            size,
            IO.mime(ext),
            crc32,
            mode
        );
    }

    public static TemporaryFileStore createTemporary(Path basePath, String scope, InputStream from, FileName fileName, long size, String mime, Thumbs.Generator thumbGenerator, Consumer<IOException> exceptionConsumer) {
        StorePath path = StorePath.create(basePath, StoreMode.TEMPORARY, scope, fileName.getExt());
        long crc32;
        try {
            crc32 = IO.copyWithCrc32(from, path.toAbsolutePath());
            if (IStore.isImage(mime)) {
                Thumbs.generate(path.toAbsolutePath(), new Thumb(), null, thumbGenerator);
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
            path.toAbsolutePath(),
            path.getRelative().toString(),
            fileName.getName(),
            fileName.getExt(),
            size,
            mime,
            crc32
        );
    }

    public static void copyContent(Path path, OutputStream outputStream) throws IOException {
        if (Files.exists(path) && Files.isReadable(path)) {
            Files.copy(path, outputStream);
        }
    }

    public static void processAttributes(IFileStore fileStore, Path source, Set<String> checkers) {
        fileStore.setCheckers(checkers);
        if (IStore.isImage(fileStore)) {
            fileStore.getAttributes().put(IFileStoreAttrs.Image.IMAGE, String.valueOf(true));
            fillImageInfo(fileStore, Images.info(source));
        }
    }

    public static void processAttributes(IFileStore fileStore, byte[] source, Set<String> checkers) {
        fileStore.setCheckers(checkers);
        if (IStore.isImage(fileStore)) {
            fileStore.getAttributes().put(IFileStoreAttrs.Image.IMAGE, String.valueOf(true));
            fillImageInfo(fileStore, Images.info(source));
        }
    }

    public static void fillImageInfo(IFileStore fileStore, Images.Info source) {
        if (source == null) {
            return;
        }
        fileStore.setAttributes(Arrays.asList(
            Pair.of(IFileStoreAttrs.Image.WIDTH, String.valueOf(source.getWidth())),
            Pair.of(IFileStoreAttrs.Image.HEIGHT, String.valueOf(source.getHeight())),
            Pair.of(IFileStoreAttrs.Image.VERTICAL, String.valueOf(source.isVertical()))
        ));
    }
}