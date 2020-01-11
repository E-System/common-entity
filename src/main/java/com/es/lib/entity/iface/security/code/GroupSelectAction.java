/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.entity.iface.security.code;

/**
 * Action with permission group items
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 30.05.15
 */
public enum GroupSelectAction {
    /**
     * Set view for all
     */
    SET_VIEW_ALL,
    /**
     * Set edit for all
     */
    SET_EDIT_ALL,
    /**
     * Set delete for all
     */
    SET_DELETE_ALL,
    /**
     * Set all for all
     */
    SET_ALL,

    /**
     * Clear view for all
     */
    CLEAR_VIEW_ALL,
    /**
     * Clear edit for all
     */
    CLEAR_EDIT_ALL,
    /**
     * Clear delete for all
     */
    CLEAR_DELETE_ALL,
    /**
     * Clear all for all
     */
    CLEAR_ALL
}
