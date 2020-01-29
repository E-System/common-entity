package com.es.lib.entity.iface.file

import com.es.lib.entity.iface.file.code.IFileStoreAttributes
import spock.lang.Specification

class IFileStoreSpec extends Specification {

    class FileStore implements IFileStore {
        Map<String, String> attrs

        FileStore(attrs) {
            this.attrs = attrs
        }

        @Override
        String getFilePath() {
            return null
        }

        @Override
        void setFilePath(String filePath) {

        }

        @Override
        String getFileName() {
            return "Very long file name"
        }

        @Override
        void setFileName(String fileName) {

        }

        @Override
        String getFileExt() {
            return "jpeg"
        }

        @Override
        void setFileExt(String fileExt) {

        }

        @Override
        long getCrc32() {
            return 0
        }

        @Override
        void setCrc32(long crc32) {

        }

        @Override
        long getSize() {
            return 0
        }

        @Override
        void setSize(long size) {

        }

        @Override
        String getMime() {
            return null
        }

        @Override
        void setMime(String mime) {

        }

        @Override
        String getFullName() {
            return getFileName() + "." + getFileExt()
        }

        @Override
        boolean isDeleted() {
            return false
        }

        @Override
        void setDeleted(boolean deleted) {

        }

        @Override
        Map<String, String> getAttributes() {
            return attrs
        }

        @Override
        void setAttributes(Map<String, String> attributes) {
            attrs = attributes
        }

        @Override
        Long getId() {
            return null
        }

        @Override
        void setId(Long id) {

        }
    }

    def "IsPublicVisible"() {
        expect:
        f.isPublicVisible() == result
        where:
        f                                                          || result
        new FileStore(null)                                        || true
        new FileStore([:])                                         || true
        new FileStore([(IFileStoreAttributes.Security.OWNER): ""]) || (false)
    }

    def "IsLoggedVisible"() {
        expect:
        f.isLoggedVisible() == result
        where:
        f                                                                                                         || result
        new FileStore(null)                                                                                       || (false)
        new FileStore([:])                                                                                        || (false)
        new FileStore([(IFileStoreAttributes.Security.OWNER): ""])                                                || (false)
        new FileStore([(IFileStoreAttributes.Security.OWNER): (IFileStoreAttributes.Security.OWNER_LOGGED_CODE)]) || (true)
    }

    def "IsOwnedVisible"() {
        expect:
        f.isOwnedVisible() == result
        where:
        f                                                                                                         || result
        new FileStore(null)                                                                                       || (false)
        new FileStore([:])                                                                                        || (false)
        new FileStore([(IFileStoreAttributes.Security.OWNER): ""])                                                || (false)
        new FileStore([(IFileStoreAttributes.Security.OWNER): (IFileStoreAttributes.Security.OWNER_LOGGED_CODE)]) || (false)
        new FileStore([(IFileStoreAttributes.Security.OWNER): "ASD"])                                             || (true)
    }

    def "IsVisible"() {
        expect:
        f.isVisible(code, id) == result
        where:
        f                                                                                                             | code  | id   || result
        new FileStore(null)                                                                                           | "ASD" | "22" || (false)
        new FileStore([:])                                                                                            | "ASD" | "22" || (false)
        new FileStore([(IFileStoreAttributes.Security.OWNER): ""])                                                    | "ASD" | "22" || (false)
        new FileStore([(IFileStoreAttributes.Security.OWNER): (IFileStoreAttributes.Security.OWNER_LOGGED_CODE)])     | "ASD" | "22" || (false)
        new FileStore([(IFileStoreAttributes.Security.OWNER): "ASD"])                                                 | "ASD" | "22" || (false)
        new FileStore([(IFileStoreAttributes.Security.OWNER): "ASD", (IFileStoreAttributes.Security.OWNER_ID): "21"]) | "ASD" | "22" || (false)
        new FileStore([(IFileStoreAttributes.Security.OWNER): "ASD", (IFileStoreAttributes.Security.OWNER_ID): "22"]) | "ASD" | "22" || (true)
    }

    def "GetOwnerId"() {
        expect:
        f.getOwnerId() == result
        where:
        f                                                               || result
        new FileStore(null)                                             || null
        new FileStore([:])                                              || null
        new FileStore([(IFileStoreAttributes.Security.OWNER_ID): "22"]) || "22"
        new FileStore([(IFileStoreAttributes.Security.OWNER_ID): "0"])  || "0"
    }

    def "GetOwner"() {
        expect:
        f.getOwner() == result
        where:
        f                                                                                                         || result
        new FileStore(null)                                                                                       || null
        new FileStore([:])                                                                                        || null
        new FileStore([(IFileStoreAttributes.Security.OWNER): ""])                                                || ""
        new FileStore([(IFileStoreAttributes.Security.OWNER): (IFileStoreAttributes.Security.OWNER_LOGGED_CODE)]) || (IFileStoreAttributes.Security.OWNER_LOGGED_CODE)
        new FileStore([(IFileStoreAttributes.Security.OWNER): "ASD"])                                             || "ASD"
    }

    def "Abbreviated name"() {
        when:
        def size = 20
        def value = new FileStore(null).getAbbreviatedFileName(size)
        then:
        value == "Very lo..e name.jpeg"
        value.length() == size
    }
}
