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
package com.es.lib.entity.iface.file;

import com.es.lib.common.collection.Items;
import com.es.lib.common.file.Images;
import com.es.lib.entity.iface.IAttrsOwner;
import com.es.lib.entity.iface.IPrimaryKey;
import com.es.lib.entity.model.file.code.IFileStoreAttrs;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 01.02.16
 */
public interface IFileStore extends IStore, IAttrsOwner, IPrimaryKey<Long> {

    String getFilePath();

    void setFilePath(String filePath);

    void setFileName(String fileName);

    void setFileExt(String fileExt);

    void setCrc32(long crc32);

    void setSize(long size);

    void setMime(String mime);

    boolean isDeleted();

    void setDeleted(boolean deleted);

    default void setCheckers(Set<String> checkers) {
        setAttribute(IFileStoreAttrs.Security.CHECKERS, Items.isEmpty(checkers) ? null : String.join(";", checkers));
    }

    default Set<String> getCheckers() {
        String checkersValue = getAttribute(IFileStoreAttrs.Security.CHECKERS);
        if (StringUtils.isBlank(checkersValue)) {
            return new HashSet<>();
        }
        return new HashSet<>(Arrays.asList(checkersValue.split(";").clone()));
    }

    default Images.Info getImageInfo() {
        Integer width = getIntAttribute(IFileStoreAttrs.Image.WIDTH);
        Integer height = getIntAttribute(IFileStoreAttrs.Image.HEIGHT);
        if (width != null && height != null) {
            return Images.Info.of(width, height);
        }
        return null;
    }
}
