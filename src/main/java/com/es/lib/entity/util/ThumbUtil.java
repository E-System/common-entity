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
import com.es.lib.entity.iface.file.code.IFileStoreAttributes;
import com.es.lib.entity.model.file.Thumb;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 17.03.2018
 */
public final class ThumbUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ThumbUtil.class);

    public interface Generator {

        void process(File source, String extension, File target, Thumb thumb) throws IOException;
    }

    private ThumbUtil() {}

    /**
     * Generate thumbnail file and return path to generated file
     *
     * @param source    Original file
     * @param thumb     Thumbnail parameters
     * @param fileStore FileStore reference
     * @param generator Thumb generate implementation
     * @return File object with generated file
     */
    public static File generate(File source, Thumb thumb, IFileStore fileStore, Generator generator) {
        if (thumb == null) {
            return source;
        }

        File target = getTarget(source, thumb);

        if (target.exists() && target.canRead()) {
            return target;
        }

        return generate(source, target, thumb, fileStore, generator);
    }

    private static File generate(File source, File target, Thumb thumb, IFileStore fileStore, Generator generator) {
        try {
            Thumb originalThumb = create(source, fileStore);
            if (thumb.getWidth() > originalThumb.getWidth() && thumb.getHeight() > originalThumb.getHeight()) {
                FileUtils.copyFile(source, target);
            } else {
                String extension = getExtension(source);
                generator.process(source, extension, target, thumb);
            }
            return target;
        } catch (IOException e) {
            LOG.warn("Thumb save error for " + source + ": " + e.getMessage());
            return source;
        }
    }

    private static Thumb create(File source, IFileStore fileStore) {
        Thumb result = null;
        if (fileStore != null) {
            result = create(fileStore.getAttributes());
        }
        if (result == null) {
            result = create(ImageSizeUtil.get(source.toPath()));
        }
        return result;
    }

    private static Thumb create(Map<String, String> attributes) {
        Thumb result = null;
        int width = 0;
        int height = 0;
        try {
            width = Integer.parseInt(attributes.get(IFileStoreAttributes.Image.WIDTH));
        } catch (Exception ignore) {}
        try {
            height = Integer.parseInt(attributes.get(IFileStoreAttributes.Image.HEIGHT));
        } catch (Exception ignore) {}
        if (width != 0 && height != 0) {
            result = new Thumb(width, height);
        }
        return result;
    }

    private static String getExtension(File source) {
        final String ext = FilenameUtils.getExtension(source.getAbsolutePath()).toLowerCase();
        return StringUtils.isNotBlank(ext) ? ext : "png";
    }

    private static File getTarget(File source, Thumb thumb) {
        String postfix = ".thumb";
        if (!thumb.isDefaultSize()) {
            postfix = ".thumb_" + thumb.getWidth() + "_" + thumb.getHeight();
        }
        return new File(FilenameUtils.removeExtension(source.getAbsolutePath()) + postfix + "." + getExtension(source));
    }
}
