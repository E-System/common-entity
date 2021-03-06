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

package com.es.lib.entity;


import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 20.07.15
 */
public final class HStoreUtil {

    private static final String SEPARATOR = "=>";

    private HStoreUtil() {}

    public static String toString(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder(map.size() * 8);
        map.forEach((key, value) -> sb
            .append(escape(key))
            .append(SEPARATOR)
            .append(escape(value))
            .append(", "));
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    private static String escape(Object val) {
        if (val == null) {
            return "NULL";
        }
        return "\"" + escapeInner(val.toString()) + "\"";
    }

    public static boolean isFlagEnabled(Map<String, String> attributes, String code) {
        if (attributes == null || attributes.isEmpty()) {
            return false;
        }
        Object value = ((Map) (attributes)).get(code);
        return value != null && Boolean.parseBoolean(value.toString());
    }

    private static String escapeInner(String val) {
        return val.replaceAll("\"", "\\\\\"").replaceAll("\\\\", "\\\\\\\\");
    }
}
