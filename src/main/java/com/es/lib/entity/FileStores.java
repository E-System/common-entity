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

import com.es.lib.common.collection.Items;
import com.es.lib.common.exception.ESRuntimeException;
import com.es.lib.common.file.FileInfo;
import com.es.lib.common.file.FileName;
import com.es.lib.common.file.IO;
import com.es.lib.common.file.Images;
import com.es.lib.common.store.IStore;
import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.model.file.StoreMode;
import com.es.lib.entity.model.file.StorePath;
import com.es.lib.entity.model.file.TemporaryFileStore;
import com.es.lib.entity.model.file.code.IFileStoreAttrs;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * File store util
 *
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 17.03.18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileStores {

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class Attrs {

        private final Set<String> checkers;
        private final Set<String> tags;
        private final Map<String, String> attrs;

        public Attrs(Set<String> checkers, Set<String> tags) {
            this(checkers, tags, null);
        }

        public Attrs(Set<String> checkers) {
            this(checkers, null);
        }
    }

    public interface Source {

        boolean isInvalid();

        FileInfo getAttrs();
    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class TemporaryFileSource implements Source {

        private final TemporaryFileStore value;

        @Override
        public FileInfo getAttrs() {
            return new FileInfo(
                FileName.create(
                    value.getFileName(),
                    value.getFileExt()
                ),
                value.getSize(),
                value.getCrc32(),
                value.getMime()
            );
        }

        @Override
        public boolean isInvalid() {
            return value == null;
        }
    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class ByteSource implements Source {

        private final byte[] value;
        private final FileInfo attrs;

        @Override
        public boolean isInvalid() {
            return value == null;
        }

        public FileInfo load(Path to) throws IOException {
            try (InputStream is = new ByteArrayInputStream(value)) {
                return IO.copy(is, to, true);
            }
        }

    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class PathSource implements Source {

        private final Path value;
        private final FileInfo attrs;

        @Override
        public boolean isInvalid() {
            return value == null || !Files.exists(value) || !Files.isReadable(value);
        }

        public FileInfo load(Path to) throws IOException {
            try (InputStream is = Files.newInputStream(value)) {
                return IO.copy(is, to, true);
            }
        }
    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class StreamSource implements Source {

        private final InputStream value;
        private final FileInfo attrs;

        @Override
        public boolean isInvalid() {
            return value == null;
        }

        public FileInfo load(Path to) throws IOException {
            return IO.copy(value, to, true);
        }
    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class UrlSource implements Source {

        private final String value;
        private final boolean upload;

        private final Function<String, FileName> fileNameCreator;

        public UrlSource(String value, boolean upload) {
            this(value, upload, null);
        }

        @Override
        public boolean isInvalid() {
            return StringUtils.isBlank(value);
        }

        @Override
        public FileInfo getAttrs() {
            return null;
        }
    }

    @RequiredArgsConstructor
    public static class Instance<T extends IFileStore> {

        private final Path basePath;
        private final Supplier<T> creator;
        private final Consumer<IOException> exceptionConsumer;
        private final Function<Source, Source> converter;

        public Instance(Path basePath, Supplier<T> creator, Consumer<IOException> exceptionConsumer) {
            this(basePath, creator, exceptionConsumer, null);
        }

        public T toStore(String scope, Source source, Attrs attrs) {
            return FileStores.toStore(
                basePath,
                scope,
                source,
                attrs,
                creator,
                exceptionConsumer,
                converter
            );
        }

        public TemporaryFileStore toStore(StoreMode mode, String scope, Source source) {
            return FileStores.toStore(
                basePath,
                mode,
                scope,
                source,
                exceptionConsumer,
                converter
            );
        }
    }

    public static <T extends IFileStore> T toStore(Path basePath, String scope, Source source, Attrs attrs, Supplier<T> fileStoreCreator, Consumer<IOException> exceptionConsumer) {
        return toStore(basePath, scope, source, attrs, fileStoreCreator, exceptionConsumer, null);
    }

    public static <T extends IFileStore> T toStore(Path basePath, String scope, Source source, Attrs attrs, Supplier<T> fileStoreCreator, Consumer<IOException> exceptionConsumer, Function<Source, Source> converter) {
        if (source == null || source.isInvalid()) {
            return null;
        }
        if (converter != null) {
            source = converter.apply(source);
        }
        if (source instanceof TemporaryFileSource) {
            TemporaryFileStore temporaryFileStore = ((TemporaryFileSource) source).getValue();
            StorePath storePath;
            try {
                storePath = moveTo(basePath, StoreMode.PERSISTENT, scope, temporaryFileStore, false);
            } catch (IOException e) {
                return exception(e, exceptionConsumer);
            }
            T result = fill(storePath, temporaryFileStore, fileStoreCreator.get());
            processAttributes(result, storePath.toAbsolutePath(), attrs);
            return result;
        } else if (source instanceof ByteSource) {
            ByteSource byteSource = (ByteSource) source;
            FileInfo inputAttrs = byteSource.getAttrs();
            StorePath storePath = StorePath.create(basePath, StoreMode.PERSISTENT, scope, inputAttrs.getFileName().getExt());
            T result = fileStoreCreator.get();
            result.setFilePath(storePath.getRelative().toString());
            result.setFileName(inputAttrs.getFileName().getName());
            result.setFileExt(inputAttrs.getFileName().getExt());
            result.setCrc32(inputAttrs.getCrc32());
            result.setSize(inputAttrs.getSize());
            result.setMime(inputAttrs.getMime());
            try {
                Files.createDirectories(storePath.toAbsolutePath().getParent());
                Files.copy(new ByteArrayInputStream(byteSource.getValue()), storePath.toAbsolutePath());
            } catch (IOException e) {
                return exception(e, exceptionConsumer);
            }
            processAttributes(result, byteSource.getValue(), attrs);
            return result;
        } else if (source instanceof StreamSource) {
            StreamSource streamSource = (StreamSource) source;
            FileInfo inputAttrs = streamSource.getAttrs();
            StorePath storePath = StorePath.create(basePath, StoreMode.PERSISTENT, scope, inputAttrs.getFileName().getExt());
            FileInfo fileInfo;
            try {
                fileInfo = streamSource.load(storePath.toAbsolutePath());
            } catch (IOException e) {
                return exception(e, exceptionConsumer);
            }
            T result = fileStoreCreator.get();
            result.setFilePath(storePath.getRelative().toString());
            result.setFileName(inputAttrs.getFileName().getName());
            result.setFileExt(inputAttrs.getFileName().getExt());
            result.setCrc32(fileInfo.getCrc32());
            result.setSize(fileInfo.getSize());
            result.setMime(fileInfo.getMime());
            processAttributes(result, streamSource.getValue(), attrs);
            return result;
        } else if (source instanceof UrlSource) {
            UrlSource urlSource = (UrlSource) source;
            if (!urlSource.isUpload()) {
                T result = fileStoreCreator.get();
                result.setUrl(urlSource.getValue());
                processAttributes(result, (Path) null, attrs);
                return result;
            }
            Pair<Path, FileName> download = IO.download(urlSource.getValue(), urlSource.getFileNameCreator());
            Path from = download.getKey();
            FileName fileName = download.getValue();
            StorePath storePath = StorePath.create(basePath, StoreMode.PERSISTENT, scope, fileName.getExt());

            FileInfo fileInfo;
            try (InputStream is = Files.newInputStream(from)) {
                Files.createDirectories(storePath.toAbsolutePath().getParent());
                fileInfo = IO.copy(is, storePath.toAbsolutePath(), true);
                Files.deleteIfExists(from);
            } catch (IOException e) {
                return exception(e, exceptionConsumer);
            }

            T result = fileStoreCreator.get();
            result.setFilePath(storePath.getRelative().toString());
            result.setFileName(fileName.getName());
            result.setFileExt(fileName.getExt());
            result.setCrc32(fileInfo.getCrc32());
            result.setSize(fileInfo.getSize());
            result.setMime(fileInfo.getMime());
            processAttributes(result, storePath.toAbsolutePath(), attrs);
            return result;
        }
        throw new ESRuntimeException("Invalid source type: " + source.getClass());
    }

    public static TemporaryFileStore toStore(Path basePath, StoreMode mode, String scope, Source source, Consumer<IOException> exceptionConsumer) {
        return toStore(basePath, mode, scope, source, exceptionConsumer, null);
    }

    public static TemporaryFileStore toStore(Path basePath, StoreMode mode, String scope, Source source, Consumer<IOException> exceptionConsumer, Function<Source, Source> converter) {
        if (source == null || source.isInvalid()) {
            return null;
        }
        if (converter != null) {
            source = converter.apply(source);
        }
        if (source instanceof PathSource) {
            PathSource pathSource = (PathSource) source;
            FileName fileName = FileName.create(pathSource.getValue());
            StorePath storePath = StorePath.create(basePath, mode, scope, fileName.getExt());
            FileInfo fileInfo;
            try {
                fileInfo = pathSource.load(storePath.toAbsolutePath());
            } catch (IOException e) {
                return exception(e, exceptionConsumer);
            }
            return new TemporaryFileStore(
                storePath.toAbsolutePath(),
                storePath.getRelative().toString(),
                fileName.getName(),
                fileName.getExt(),
                fileInfo.getSize(),
                fileInfo.getMime(),
                fileInfo.getCrc32(),
                mode
            );
        } else if (source instanceof ByteSource) {
            ByteSource byteSource = (ByteSource) source;
            String ext = byteSource.getAttrs().getFileName().getExt();
            StorePath storePath = StorePath.create(basePath, mode, scope, ext);
            FileInfo fileInfo;
            try {
                fileInfo = byteSource.load(storePath.toAbsolutePath());
            } catch (IOException e) {
                return exception(e, exceptionConsumer);
            }
            return new TemporaryFileStore(
                storePath.toAbsolutePath(),
                storePath.getRelative().toString(),
                fileInfo.getFileName().getName(),
                fileInfo.getFileName().getExt(),
                fileInfo.getSize(),
                fileInfo.getMime(),
                fileInfo.getCrc32(),
                mode
            );
        } else if (source instanceof StreamSource) {
            StreamSource streamSource = (StreamSource) source;
            String ext = streamSource.getAttrs().getFileName().getExt();
            StorePath storePath = StorePath.create(basePath, mode, scope, ext);
            FileInfo fileInfo;
            try {
                fileInfo = streamSource.load(storePath.toAbsolutePath());
            } catch (IOException e) {
                return exception(e, exceptionConsumer);
            }
            return new TemporaryFileStore(
                storePath.toAbsolutePath(),
                storePath.getRelative().toString(),
                fileInfo.getFileName().getName(),
                fileInfo.getFileName().getExt(),
                fileInfo.getSize(),
                fileInfo.getMime(),
                fileInfo.getCrc32(),
                mode
            );
        } else if (source instanceof UrlSource) {
            UrlSource urlSource = (UrlSource) source;
            if (!urlSource.isUpload()) {
                return new TemporaryFileStore(urlSource.getValue());
            }
            Pair<Path, FileName> download = IO.download(urlSource.getValue(), urlSource.getFileNameCreator());
            Path from = download.getKey();
            FileName fileName = download.getValue();
            StorePath storePath = StorePath.create(basePath, mode, scope, fileName.getExt());

            FileInfo fileInfo;
            try (InputStream is = Files.newInputStream(from)) {
                Files.createDirectories(storePath.toAbsolutePath().getParent());
                fileInfo = IO.copy(is, storePath.toAbsolutePath(), true);
                Files.deleteIfExists(from);
            } catch (IOException e) {
                return exception(e, exceptionConsumer);
            }

            return new TemporaryFileStore(
                storePath.toAbsolutePath(),
                storePath.getRelative().toString(),
                fileName.getName(),
                fileName.getExt(),
                fileInfo.getSize(),
                fileInfo.getMime(),
                fileInfo.getCrc32(),
                mode
            );
        }
        throw new ESRuntimeException("Invalid source type: " + source.getClass());
    }

    public static <T extends IFileStore> T copyInStore(Path basePath, String scope, T fileStore, Supplier<T> fileStoreCreator, Consumer<IOException> exceptionConsumer) {
        StorePath storePath = StorePath.create(basePath, StoreMode.PERSISTENT, scope, fileStore.getFileExt());
        try {
            Files.createDirectories(storePath.toAbsolutePath().getParent());
            Files.copy(Paths.get(basePath.toString(), fileStore.getFilePath()), storePath.toAbsolutePath());
        } catch (IOException e) {
            return exception(e, exceptionConsumer);
        }

        T result = fill(storePath, fileStore, fileStoreCreator.get());
        result.setAttrs(fileStore.getAttrs());
        return result;
    }

    private static <T extends IStore> T exception(IOException e, Consumer<IOException> consumer) {
        if (consumer == null) {
            throw new ESRuntimeException(e);
        }
        consumer.accept(e);
        return null;
    }

    private static <T extends IFileStore> T fill(StorePath storePath, IStore source, T result) {
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

    public static StorePath moveTo(Path basePath, StoreMode mode, String scope, TemporaryFileStore temporaryFile, boolean deleteOriginal) throws IOException {
        StorePath result = StorePath.create(basePath, mode, scope, temporaryFile.getFileExt());
        Path source = Paths.get(basePath.toString(), temporaryFile.getFilePath());
        Files.createDirectories(result.toAbsolutePath().getParent());
        Files.copy(source, result.toAbsolutePath());
        if (deleteOriginal) {
            Files.deleteIfExists(source);
        }
        return result;
    }

    public static void copyContent(Path path, OutputStream outputStream) throws IOException {
        if (Files.exists(path) && Files.isReadable(path)) {
            Files.copy(path, outputStream);
        }
    }

    public static void processAttributes(IFileStore fileStore, Path source, Attrs attrs) {
        processAttributes(fileStore, attrs);
        if (IStore.isImage(fileStore)) {
            fileStore.setAttr(IFileStoreAttrs.Image.IMAGE, String.valueOf(true));
            fillImageInfo(fileStore, Images.info(source));
        }
    }

    public static void processAttributes(IFileStore fileStore, byte[] source, Attrs attrs) {
        processAttributes(fileStore, attrs);
        if (IStore.isImage(fileStore)) {
            fileStore.setAttr(IFileStoreAttrs.Image.IMAGE, String.valueOf(true));
            fillImageInfo(fileStore, Images.info(source));
        }
    }

    public static void processAttributes(IFileStore fileStore, InputStream source, Attrs attrs) {
        processAttributes(fileStore, attrs);
        if (IStore.isImage(fileStore)) {
            fileStore.setAttr(IFileStoreAttrs.Image.IMAGE, String.valueOf(true));
            try {
                fillImageInfo(fileStore, Images.info(source));
            } catch (Exception ignore) {}
        }
    }

    public static void processAttributes(IFileStore fileStore, Attrs attrs) {
        if (attrs == null) {
            return;
        }
        fileStore.setCheckers(attrs.getCheckers());
        fileStore.setTags(attrs.getTags());
        if (Items.isEmpty(attrs.attrs)) {
            return;
        }
        for (Map.Entry<String, String> entry : attrs.attrs.entrySet()) {
            if (!IFileStoreAttrs.RESERVED.contains(entry.getKey())) {
                fileStore.setAttr(entry);
            }
        }

    }

    public static void fillImageInfo(IFileStore fileStore, Images.Info source) {
        if (source == null) {
            return;
        }
        fileStore.setAttrs(Arrays.asList(
            Pair.of(IFileStoreAttrs.Image.WIDTH, String.valueOf(source.getWidth())),
            Pair.of(IFileStoreAttrs.Image.HEIGHT, String.valueOf(source.getHeight())),
            Pair.of(IFileStoreAttrs.Image.VERTICAL, String.valueOf(source.isVertical()))
        ));
    }
}