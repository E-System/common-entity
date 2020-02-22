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

import com.es.lib.entity.model.file.IFileStore;
import com.es.lib.entity.model.file.code.IFileStoreAttrs;
import com.es.lib.entity.model.file.Thumb;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 17.03.2018
 */
@Slf4j
public final class ThumbUtil {

    public interface Generator {

        void process(Path source, String extension, Path target, Thumb thumb) throws IOException;
    }

    private ThumbUtil() {}

    /**
     * Generate thumbnail file and return path to generated file
     *
     * @param source    Original file
     * @param thumb     Thumbnail parameters
     * @param fileStore FileStore reference
     * @param generator Thumb generate implementation
     * @return Generated file path
     */
    public static Path generate(Path source, Thumb thumb, IFileStore fileStore, Generator generator) {
        if (thumb == null) {
            return source;
        }
        Path target = getTarget(source, thumb);
        if (Files.exists(target) && Files.isReadable(target)) {
            return target;
        }
        return generate(source, target, thumb, fileStore, generator);
    }

    private static Path generate(Path source, Path target, Thumb thumb, IFileStore fileStore, Generator generator) {
        try {
            Thumb originalThumb = create(source, fileStore);
            if (originalThumb != null && thumb.getWidth() > originalThumb.getWidth() && thumb.getHeight() > originalThumb.getHeight()) {
                Files.copy(source, target);
            } else {
                String extension = getExtension(source);
                generator.process(source, extension, target, thumb);
            }
            return target;
        } catch (IOException e) {
            log.warn("Thumb save error for " + source + ": " + e.getMessage());
            return source;
        }
    }

    private static Thumb create(Path source, IFileStore fileStore) {
        Thumb result = null;
        if (fileStore != null) {
            result = create(fileStore.getAttributes());
        }
        if (result == null) {
            result = create(ImageSizeUtil.get(source));
        }
        return result;
    }

    private static Thumb create(Map<String, String> attributes) {
        Thumb result = null;
        int width = 0;
        int height = 0;
        try {
            width = Integer.parseInt(attributes.get(IFileStoreAttrs.Image.WIDTH));
        } catch (Exception ignore) {}
        try {
            height = Integer.parseInt(attributes.get(IFileStoreAttrs.Image.HEIGHT));
        } catch (Exception ignore) {}
        if (width != 0 && height != 0) {
            result = new Thumb(width, height);
        }
        return result;
    }

    private static String getExtension(Path source) {
        final String ext = FilenameUtils.getExtension(source.toString()).toLowerCase();
        return StringUtils.isNotBlank(ext) ? ext : "png";
    }

    private static Path getTarget(Path source, Thumb thumb) {
        String postfix = ".thumb";
        if (!thumb.isDefaultSize()) {
            postfix = ".thumb_" + thumb.getWidth() + "_" + thumb.getHeight();
        }
        return Paths.get(FilenameUtils.removeExtension(source.toAbsolutePath().toString()) + postfix + "." + getExtension(source));
    }
}
