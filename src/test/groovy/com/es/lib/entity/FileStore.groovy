package com.es.lib.entity

import com.es.lib.entity.iface.file.IFileStore

class FileStore implements IFileStore {

    Long id
    String filePath
    String fileName
    String fileExt
    long crc32
    long size
    String mime
    boolean deleted
    Map<String, String> attributes

    @Override
    String getFullName() {
        return getFileName() + "." + getFileExt()
    }
}
