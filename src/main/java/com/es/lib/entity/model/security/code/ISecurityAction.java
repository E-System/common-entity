/*
 * Copyright 2020 E-System LLC
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
package com.es.lib.entity.model.security.code;

import com.es.lib.entity.iface.security.IDomainPermissionItem;
import com.es.lib.entity.iface.security.IPermissionItem;
import com.es.lib.entity.model.security.PermissionItem;

import java.util.regex.Pattern;

/**
 * Action code for targets
 *
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public interface ISecurityAction {

    String _JOIN_STRING = "$_#_$";

    /**
     * Select elements
     */
    String SELECT = "SELECT";
    /**
     * View elements
     */
    String VIEW = "VIEW";
    /**
     * Edit elements (create, update, delete - optional if DELETE action not used)
     */
    String EDIT = "EDIT";
    /**
     * Close, block, ending
     */
    String CLOSE = "CLOSE";
    /**
     * Delete elements
     */
    String DELETE = "DELETE";

    static String join(IDomainPermissionItem item) {
        if (item == null) {
            return null;
        }
        return join(item.getTarget(), item.getAction());
    }

    static String join(IPermissionItem item) {
        if (item == null) {
            return null;
        }
        return join(item.getTarget(), item.getAction());
    }

    static String join(String target, String action) {
        if (target == null || action == null) {
            return null;
        }
        return target + _JOIN_STRING + action;
    }

    static PermissionItem split(String key) {
        return split(null, key);
    }

    static PermissionItem split(Integer idRole, String key) {
        if (key == null) {
            return null;
        }
        final String[] split = key.split(Pattern.quote(_JOIN_STRING));
        if (split.length < 2) {
            return null;
        }
        return new PermissionItem(idRole, split[0], split[1]);
    }
}
