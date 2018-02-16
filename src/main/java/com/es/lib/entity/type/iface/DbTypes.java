package com.es.lib.entity.type.iface;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 03.02.2018
 */
public interface DbTypes {

    enum Json {

        JSON("JSON"),
        JSONB("JSONB");

        private String value;

        Json(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    enum Primitive {

        STRING("VARCHAR"),
        INT("INT"),
        BIGINT("BIGINT"),
        TIMESTAMP("TIMESTAMPTZ");

        private String value;

        Primitive(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
