package com.es.lib.entity.type.iface;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 03.02.2018
 */
public interface DbTypes {

    enum Json {

        JSON("json"),
        JSONB("jsonb");

        private String value;

        Json(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    enum Primitive {

        VARCHAR("varchar"),
        SMALLINT("smallint"),
        INT("int"),
        BIGINT("bigint"),
        TIMESTAMPTZ("timestamptz");

        private String value;

        Primitive(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
