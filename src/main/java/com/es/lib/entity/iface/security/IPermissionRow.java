package com.es.lib.entity.iface.security;

import com.es.lib.entity.iface.security.code.ISecurityAction;

import java.io.Serializable;

public interface IPermissionRow extends Serializable {

    Integer getIdRole();

    String getTarget();

    String getAction();

    default String getKey() {
        return ISecurityAction.join(getTarget(), getAction());
    }
}
