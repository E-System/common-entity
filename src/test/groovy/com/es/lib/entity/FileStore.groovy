package com.es.lib.entity

import com.es.lib.common.store.IStore
import com.es.lib.entity.iface.file.IFileStore

class FileStore implements IFileStore {

    Long id
    String filePath
    String fileName
    String fileExt
    long crc32
    long size
    String mime
    String url
    boolean deleted
    Map<String, String> attrs

    @Override
    String getFullName() {
        return IStore.fullName(this)
    }

    @Override
    String getAbbreviatedFileName(int maxWidth) {
        return IStore.abbreviatedFileName(this, maxWidth)
    }

    @Override
    boolean isImage() {
        return IStore.isImage(this)
    }
}
