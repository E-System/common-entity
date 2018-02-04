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

package com.es.lib.entity.type.array.set;

import com.es.lib.entity.type.CommonSetType;
import com.es.lib.entity.type.iface.DbTypes;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public class IntegerSetType extends CommonSetType {

    @Override
    public DbTypes.Primitive getDbType() {
        return DbTypes.Primitive.INTEGER;
    }

    @Override
    public Class<?> getItemClass() {
        return Integer.class;
    }
}
