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
package com.es.lib.entity.model.file.code;

/**
 * File store element attributes
 *
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 27.05.15
 */
public interface IFileStoreAttrs {

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

        String CHECKER_LOGGED_CODE = "LOGGED";
        String CHECKERS = "S_CHECKERS";
    }

    /**
     * File tags
     */
    String TAGS = "TAGS";
}
