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

import com.es.lib.entity.util.FileStoreUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 01.02.16
 */
public interface IStore extends Serializable {

    String getFileName();

    String getFileExt();

    long getCrc32();

    long getSize();

    String getMime();

    //TODO: Check jsf for default interface getter
    default String getFullName() {
        return getFileName() + "." + getFileExt();
    }

    default String getAbbreviatedFileName(int maxWidth) {
        int extSize = getFileExt().length();
        int nameSize = getFileName().length();
        if ((nameSize + extSize + 1) < maxWidth) {
            return getFullName();
        }
        return StringUtils.abbreviateMiddle(getFileName(), "..", maxWidth - extSize - 1) + "." + getFileExt();
    }

    //TODO: Check jsf for default interface getter
    default boolean isImage() {
        return FileStoreUtil.isImage(this);
    }

}
