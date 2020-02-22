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

package com.es.lib.entity.model.audit;

import com.es.lib.common.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
        return new AuditInfo(title, null);
    }

    default IAuditInfo auditInfo(String title, String value) {
        return new AuditInfo(title, value);
    }

    default IAuditInfo jsonAuditInfo(String title, Object value) {
        return new AuditInfo(title, JsonUtil.toJson(value));
    }
}
