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

    public interface ThumbGenerator {

        void generate(File file, String extension, Thumb thumb);
    }

    /**
     * Generate thumbnail file and return path to generated file
     *
     * @param originalFile Path to original file
     * @param thumb        Thumbnail parameters
     * @return File object with generated file
     */
    public File generate(File originalFile, Thumb thumb, IFileStore fileStore, ThumbGenerator generator) {
        if (thumb == null) {
            return originalFile;
        }

        File thumbTarget = new File(getPath(originalFile, thumb));

        if (thumbTarget.exists() && thumbTarget.canRead()) {
            return thumbTarget;
        }

        return generate(originalFile, thumbTarget, thumb, fileStore, generator);
    }

    private File generate(File originalFile, File thumbTarget, Thumb thumb, IFileStore fileStore, ThumbGenerator generator) {
        try {
            Thumb originalThumb = create(originalFile, fileStore);
            if (thumb.getWidth() > originalThumb.getWidth() && thumb.getHeight() > originalThumb.getHeight()) {
                FileUtils.copyFile(originalFile, thumbTarget);
            } else {
                String extension = getThumbImageExtension(originalFile);
                generator.generate(originalFile, extension, thumb);
            }
            return thumbTarget;
        } catch (IOException e) {
            LOG.warn("Thumb save error for " + originalFile + ": " + e.getMessage());
            return originalFile;
        }
    }

    private Thumb create(File originalFile, IFileStore fileStore) {
        Thumb result = null;
        if (fileStore != null) {
            result = create(fileStore.getAttributes());
        }
        if (result == null) {
            result = create(ImageSizeUtil.get(originalFile.toPath()));
        }
        return result;
    }

    private Thumb create(Map<String, String> attributes) {
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

    private String getThumbImageExtension(File originalFile) {
        final String ext = FilenameUtils.getExtension(originalFile.getAbsolutePath()).toLowerCase();
        return StringUtils.isNotBlank(ext) ? ext : "png";
    }

    private String getPath(File originalFile, Thumb parameters) {
        String postfix = ".thumb";
        if (!parameters.isDefaultSize()) {
            postfix = ".thumb_" + parameters.getWidth() + "_" + parameters.getHeight();
        }
        return FilenameUtils.removeExtension(originalFile.getAbsolutePath()) + postfix + "." + getThumbImageExtension(originalFile);
    }
}
