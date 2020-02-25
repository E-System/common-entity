package com.es.lib.entity.model.audit.event;

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
        this(action, title, null);
    }

    public AuditEvent(String action, String title, String value) {
        this(null, action, title, value, null);
    }
}
