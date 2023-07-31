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
package com.es.lib.entity.iface.security;

import com.es.lib.entity.model.security.code.ISecurityAction;

import java.io.Serializable;

public interface IDomainPermissionItem extends Serializable {

    String DOMAIN_OWNER = "OWNER";
    String DOMAIN_TEAM = "TEAM";

    Integer getIdRole();

    String getTarget();

    String getAction();

    String getDomain();

    default String getKey() {
        return ISecurityAction.join(getTarget(), getAction());
    }
}
