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

package com.es.lib.entity.util;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public final class LikeUtil {

    public static final String ANY_STRING = "%";
    public static final String ANY_CHARACTER = "_";

    private LikeUtil() { }

    public static String like(String text, boolean anyMatch) {
        return anyMatch ? any(text) : begin(text);
    }

    public static String any(String text) {
        return like(text, ANY_STRING, ANY_STRING, true);
    }

    public static String begin(String text) {
        return like(text, "", ANY_STRING, true);
    }

    private static String like(String text, String leftSymbol, String rightSymbol, boolean trim) {
        return leftSymbol + (text == null ? "" : (trim ? text.toLowerCase().trim() : text.toLowerCase())) + rightSymbol;
    }
}
