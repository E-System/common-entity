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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Map;

/**
 * Thumb generate info
 *
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 17.03.18
 */
@Getter
@ToString
@RequiredArgsConstructor
public class Thumb {

    public static final int DEFAULT_WIDTH = 128;
    public static final int DEFAULT_HEIGHT = 128;

    private final int width;
    private final int height;

    public Thumb() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public boolean isDefaultSize() {
        return getWidth() == DEFAULT_WIDTH && getHeight() == DEFAULT_HEIGHT;
    }

    public static Thumb create(boolean need, String tw, String th) {
        if (!need) {
            return null;
        }
        return new Thumb(
            NumberUtils.toInt(tw, Thumb.DEFAULT_WIDTH),
            NumberUtils.toInt(th, Thumb.DEFAULT_HEIGHT)
        );
    }

    public static Thumb create(Map<String, String> params) {
        return create(
            params.containsKey("thumb"),
            params.get("tw"),
            params.get("th")
        );
    }
}
