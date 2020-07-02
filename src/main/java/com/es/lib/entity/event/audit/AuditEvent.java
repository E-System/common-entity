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
package com.es.lib.entity.event.audit;

import com.es.lib.entity.iface.audit.IAuditInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 29.03.2018
 */
@Getter
@ToString
@RequiredArgsConstructor
public class AuditEvent {

    private final Object initiator;
    private final String action;
    private final String title;
    private final String value;
    private final String valueType;

    public AuditEvent(String action, String title) {
        this(null, action, title, null, null);
    }

    public AuditEvent(String action, String title, String value) {
        this(action, title, value, null);
    }

    public AuditEvent(String action, String title, String value, String valueType) {
        this(null, action, title, value, valueType);
    }

    public AuditEvent(String action, IAuditInfo auditInfo) {
        this(null, action, auditInfo);
    }

    public AuditEvent(Object initiator, String action, IAuditInfo auditInfo) {
        this(initiator, action, auditInfo.getTitle(), auditInfo.getValue(), auditInfo.getValueType());
    }
}
