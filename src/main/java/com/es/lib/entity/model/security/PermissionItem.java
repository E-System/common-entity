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
package com.es.lib.entity.model.security;

import com.es.lib.entity.iface.security.IPermissionItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 05.09.16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PermissionItem implements IPermissionItem {

    @Id
    @Column(name = "id_role")
    private Integer idRole;
    @Id
    private String target;
    @Id
    private String action;

    public PermissionItem(IPermissionItem item) {
        this.idRole = item.getIdRole();
        this.target = item.getTarget();
        this.action = item.getAction();
    }
}
