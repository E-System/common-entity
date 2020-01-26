package com.es.lib.entity.model.file;

/**
 * Path type
 */
public enum FileStoreMode {
    /**
     * For stored files
     */
    PERSISTENT(null),
    /**
     * For temporary upload files
     */
    TEMPORARY("temporary"),
    /**
     * For internal usage (email send, etc....)
     */
    SYSTEM("system");

    private String prefix;

    FileStoreMode(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}