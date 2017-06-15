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

package com.es.lib.entity.iface.security;

import com.es.lib.entity.iface.security.code.ISecurityAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 05.09.16
 */
@Entity
public class PermissionRow implements Serializable {

    @Id
    @Column(name = "id_role")
    private Integer idRole;
    @Id
    private String target;
    @Id
    private String action;

    public PermissionRow() {
    }

    public PermissionRow(Integer idRole, String target, String action) {
        this.idRole = idRole;
        this.target = target;
        this.action = action;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getKey() {
        return ISecurityAction.join(getTarget(), getAction());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionRow that = (PermissionRow) o;

        return idRole != null ?
                idRole.equals(that.idRole)
                : that.idRole == null && target.equals(that.target) && action.equals(that.action);

    }

    @Override
    public int hashCode() {
        int result = idRole != null ? idRole.hashCode() : 0;
        result = 31 * result + target.hashCode();
        result = 31 * result + action.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PermissionRow{" +
               "idRole=" + idRole +
               ", target='" + target + '\'' +
               ", action='" + action + '\'' +
               '}';
    }
}
