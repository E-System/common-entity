/*
 * Copyright 2018 E-System LLC
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

package com.es.lib.entity.iface.audit;

import com.es.lib.common.Jsons;
import com.es.lib.entity.iface.IPrimaryKey;
import com.es.lib.entity.model.audit.AuditInfo;
import com.es.lib.entity.util.PKeys;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;

/**
 * Audit info provider
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.03.2018
 */
public interface IAuditInfoProvider {

    @JsonIgnore
    IAuditInfo getAuditInfo();

    default IAuditInfo auditInfo(String title) {
        return auditInfo(title, null, null);
    }

    default IAuditInfo auditInfo(String title, String value) {
        return auditInfo(title, value, String.class.getCanonicalName());
    }

    default IAuditInfo auditInfo(String title, String value, String valueType) {
        return new AuditInfo(title, value, valueType);
    }

    default IAuditInfo jsonAuditInfo(String title, Object value) {
        return jsonAuditInfo(title, value, null);
    }

    default IAuditInfo jsonAuditInfo(String title, Object value, Collection<String> exclude) {
        if (value instanceof IPrimaryKey) {
            return auditInfo(title, Jsons.toJson(PKeys.toMap((IPrimaryKey<?>) value, exclude)), value.getClass().getCanonicalName());
        }
        return auditInfo(title, Jsons.toJson(value), value.getClass().getCanonicalName());
    }
}
