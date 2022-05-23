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

import com.es.lib.common.file.Images;
import com.es.lib.common.store.IStore;
import com.es.lib.entity.iface.IAttrsOwner;
import com.es.lib.entity.iface.IPrimaryKey;
import com.es.lib.entity.model.file.code.IFileStoreAttrs;

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

    default void setUrl(String url) {}

    boolean isDeleted();

    void setDeleted(boolean deleted);

    default void setCheckers(Set<String> checkers) {
        setCollectionAttr(IFileStoreAttrs.Security.CHECKERS, checkers);
    }

    default Set<String> getCheckers() {
        return getSetAttr(IFileStoreAttrs.Security.CHECKERS, v -> v);
    }

    default void setTags(Set<String> tags) {
        setCollectionAttr(IFileStoreAttrs.TAGS, tags);
    }

    default Set<String> getTags() {
        return getSetAttr(IFileStoreAttrs.TAGS, v -> v);
    }

    default Images.Info getImageInfo() {
        Integer width = getIntAttr(IFileStoreAttrs.Image.WIDTH);
        Integer height = getIntAttr(IFileStoreAttrs.Image.HEIGHT);
        if (width != null && height != null) {
            return Images.Info.of(width, height);
        }
        return null;
    }
}
