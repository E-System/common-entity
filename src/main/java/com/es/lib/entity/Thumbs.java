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

import com.es.lib.common.file.Images;
import com.es.lib.common.number.Numbers;
import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.model.file.Thumb;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 17.03.2018
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Thumbs {

    public interface Generator {

        void process(Path source, String extension, Path target, Thumb thumb) throws IOException;
    }

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
            result = create(fileStore.getImageInfo());
        }
        if (result == null) {
            result = create(Images.info(source));
        }
        return result;
    }

    private static Thumb create(Images.Info info) {
        if (info == null) {
            return null;
        }
        return new Thumb(info.getWidth(), info.getWidth());
    }

    private static String getExtension(Path source) {
        final String ext = FilenameUtils.getExtension(source.toString()).toLowerCase();
        return StringUtils.isNotBlank(ext) ? ext : "png";
    }

    private static Path getTarget(Path source, Thumb thumb) {
        String postfix = ".thumb";
        if (!thumb.isDefaultSize()) {
            postfix += ("_" + thumb.getWidth() + "_" + thumb.getHeight());
        }
        if (!Float.isNaN(thumb.getQuality())) {
            postfix += ("_" + Numbers.formatter("_").format(thumb.getQuality()));
        }
        return Paths.get(FilenameUtils.removeExtension(source.toAbsolutePath().toString()) + postfix + "." + getExtension(source));
    }
}
