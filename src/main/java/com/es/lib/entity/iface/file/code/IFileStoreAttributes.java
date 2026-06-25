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

package com.es.lib.entity.iface.file.code;

import java.util.Arrays;
import java.util.Collection;

/**
 * File store element attributes
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 27.05.15
 */
public interface IFileStoreAttributes {

    /**
     * Image attributes
     */
    interface Image {

        /**
         * Image flag
         */
        String IMAGE = "IMAGE";
        /**
         * Image width in pixels
         */
        String WIDTH = "WIDTH";
        /**
         * Image height in pixels
         */
        String HEIGHT = "HEIGHT";
        /**
         * Vertical image flag (height greater than width)
         */
        String VERTICAL = "VERTICAL";
    }

    /**
     * Security attributes
     */
    interface Security {

        @Deprecated
        String OWNER_LOGGED_CODE = "LOGGED";
        @Deprecated
        String OWNER = "S_OWNER";
        @Deprecated
        String OWNER_ID = "S_OWNER_ID";

        String CHECKER_LOGGED_CODE = "LOGGED";
        String CHECKER_OR_CODE = "OR";
        String CHECKER_ROLE_CODE = "ROLE";
        String CHECKERS = "S_CHECKERS";
    }

    /**
     * File tags
     */
    String TAGS = "TAGS";

    Collection<String> RESERVED = Arrays.asList(
        Image.IMAGE,
        Image.WIDTH,
        Image.HEIGHT,
        Image.VERTICAL,
        Security.CHECKERS,
        TAGS
    );
}
