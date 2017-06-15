/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.entity.iface.security.code;

import com.es.lib.entity.iface.security.PermissionRow;

import java.util.regex.Pattern;

/**
 * Action code for targets
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public interface ISecurityAction {

    String _JOIN_STRING = "$_#_$";

    /**
     * View elements
     */
    String VIEW = "VIEW";
    /**
     * Edit elements (create, update, delete)
     */
    String EDIT = "EDIT";
    /**
     * Close, block, ending
     */
    String CLOSE = "CLOSE";

    static String join(PermissionRow pair) {
        return join(pair.getTarget(), pair.getAction());
    }

    static String join(String target, String action) {
        return target + _JOIN_STRING + action;
    }

    static PermissionRow split(String key) {
        if (key == null) {
            return null;
        }
        final String[] split = key.split(Pattern.quote(_JOIN_STRING));
        if (split.length < 2) {
            return null;
        }
        return new PermissionRow(null, split[0], split[1]);
    }
}
