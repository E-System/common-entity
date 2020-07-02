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

import com.es.lib.entity.model.file.code.IFileStoreAttrs;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Image size extract util
 *
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 17.03.18
 */
@Slf4j
public final class ImageSizes {

    private ImageSizes() {}

    public static Map<String, String> get(Path source) {
        try (InputStream stream = Files.newInputStream(source)) {
            return get(stream);
        } catch (IOException e) {
            log.info(e.getMessage(), e);
        }
        return Collections.emptyMap();
    }

    public static Map<String, String> get(byte[] data) {
        try (InputStream stream = new ByteArrayInputStream(data)) {
            return get(stream);
        } catch (IOException e) {
            log.info(e.getMessage(), e);
        }
        return Collections.emptyMap();
    }

    public static Map<String, String> get(InputStream stream) throws IOException {
        try (ImageInputStream in = ImageIO.createImageInputStream(stream)) {
            return get(in);
        }
    }

    public static Map<String, String> get(ImageInputStream in) throws IOException {
        Map<String, String> result = new HashMap<>();
        final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
        if (readers.hasNext()) {
            ImageReader reader = readers.next();
            try {
                reader.setInput(in);
                final int width = reader.getWidth(0);
                final int height = reader.getHeight(0);
                result.put(IFileStoreAttrs.Image.WIDTH, String.valueOf(width));
                result.put(IFileStoreAttrs.Image.HEIGHT, String.valueOf(height));
                result.put(IFileStoreAttrs.Image.VERTICAL, String.valueOf(height > width));
            } finally {
                reader.dispose();
            }
        }
        return result;
    }
}