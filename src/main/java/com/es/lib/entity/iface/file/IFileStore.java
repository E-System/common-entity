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

import com.es.lib.entity.IAttributeOwner;
import com.es.lib.entity.IPrimaryKey;
import com.es.lib.entity.iface.file.code.IFileStoreAttributes;

import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 01.02.16
 */
public interface IFileStore extends IAttributeOwner, IPrimaryKey<Long> {

    String getFilePath();

    void setFilePath(String filePath);

    String getMime();

    void setMime(String mime);

    String getFullName();

    boolean isDeleted();

    void setDeleted(boolean deleted);

    default boolean isPublicVisible() {
        return getOwner() == null;
    }

    default boolean isLoggedVisible() {
        String owner = getOwner();
        return IFileStoreAttributes.Security.OWNER_LOGGED_CODE.equals(owner);
    }

    default boolean isOwnedVisible() {
        String owner = getOwner();
        return owner != null && !owner.isEmpty() && !IFileStoreAttributes.Security.OWNER_LOGGED_CODE.equals(owner);
    }

    default boolean isVisible(String code, String id) {
        return code.equals(getOwner()) && id.equals(getOwnerId());
    }

    default String getOwnerId() {
        return getAttr(IFileStoreAttributes.Security.OWNER_ID);
    }

    default String getOwner() {
        return getAttr(IFileStoreAttributes.Security.OWNER);
    }

    default String getAttr(String code) {
        Map<String, String> attributes = getAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.get(code);
    }
}
