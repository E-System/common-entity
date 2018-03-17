package com.es.lib.entity.model.file;

/**
 * File store request data
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 17.03.18
 */
public class FileStoreRequest {

    private String id;
    private boolean generateEmpty;
    private Thumb thumb;

    public FileStoreRequest(String id, boolean generateEmpty, Thumb thumb) {
        this.id = id;
        this.generateEmpty = generateEmpty;
        this.thumb = thumb;
    }

    public String getId() {
        return id;
    }

    public boolean isGenerateEmpty() {
        return generateEmpty;
    }

    public Thumb getThumb() {
        return thumb;
    }

    @Override
    public String toString() {
        return "FileStoreRequest{" +
               "id='" + id + '\'' +
               ", generateEmpty=" + generateEmpty +
               ", thumb=" + thumb +
               '}';
    }
}
