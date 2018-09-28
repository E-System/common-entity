/*
 * Copyright (c) E-System LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2018
 */

package com.es.lib.entity.type.array.set;

import com.es.lib.entity.type.CommonSetType;
import com.es.lib.entity.type.iface.DbTypes;

/**
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 28.09.18
 */
public class ShortSetType extends CommonSetType {

    @Override
    public DbTypes.Primitive getDbType() {
        return DbTypes.Primitive.SMALLINT;
    }

    @Override
    public Class<?> getItemClass() {
        return Short.class;
    }
}
