package com.es.lib.entity.util;

import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.iface.file.code.IFileStoreAttributes;

import java.nio.file.Path;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 17.03.2018
 */
public final class FileStoreImageUtil {

    private FileStoreImageUtil() {}

    public static void processAttributes(IFileStore fileStore, Path source) {
        if (FileStoreUtil.isImage(fileStore)) {
            fileStore.getAttributes().put(IFileStoreAttributes.Image.IMAGE, String.valueOf(true));
            fillImageInfo(fileStore, source);
        }
    }

    public static void processAttributes(IFileStore fileStore, byte[] source) {
        if (FileStoreUtil.isImage(fileStore)) {
            fileStore.getAttributes().put(IFileStoreAttributes.Image.IMAGE, String.valueOf(true));
            fillImageInfo(fileStore, source);
        }
    }

    public static void fillImageInfo(IFileStore fileStore, Path source) {
        fileStore.getAttributes().putAll(ImageSizeUtil.get(source));
    }

    public static void fillImageInfo(IFileStore fileStore, byte[] source) {
        fileStore.getAttributes().putAll(ImageSizeUtil.get(source));
    }
}
