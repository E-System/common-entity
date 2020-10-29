package com.es.lib.entity.iface.file

import com.es.lib.entity.model.file.code.IFileStoreAttrs
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
        String getAbbreviatedFileName(int maxWidth) {
            return abbreviatedFileName(this, maxWidth)
        }

        @Override
        boolean isImage() {
            return isImage(this)
        }

        @Override
        void setMime(String mime) {

        }

        @Override
        String getFullName() {
            return fullName(this)
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

    def "Get checkers"() {
        expect:
        f.getCheckers() == result
        where:
        f                                                                                                    || result
        new FileStore(null)                                                                                  || new HashSet<String>()
        new FileStore([:])                                                                                   || new HashSet<String>()
        new FileStore([(IFileStoreAttrs.Security.CHECKERS): ""])                                             || new HashSet<String>()
        new FileStore([(IFileStoreAttrs.Security.CHECKERS): (IFileStoreAttrs.Security.CHECKER_LOGGED_CODE)]) || new HashSet<String>([IFileStoreAttrs.Security.CHECKER_LOGGED_CODE])
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
