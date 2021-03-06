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

package com.es.lib.entity.iface.periodic;


import com.es.lib.entity.IPrimaryKey;

import java.util.Date;

/**
 * Интерфейс периодического атрибута сущности
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public interface IPropEntity<PK extends Number, O extends IPrimaryKey, VS extends IPrimaryKey> extends IPrimaryKey<PK> {

    Date getDbegin();

    void setDbegin(Date dbegin);

    Date getDend();

    void setDend(Date dend);

    O getOwner();

    void setOwner(O owner);

    VS getVarSet();

    void setVarSet(VS varSet);

    VS createVarSet();
}
