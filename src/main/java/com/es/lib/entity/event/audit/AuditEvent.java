package com.es.lib.entity.event.audit;

import com.es.lib.entity.iface.audit.IAuditInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
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
