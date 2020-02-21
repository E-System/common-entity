package com.es.lib.entity.iface.audit.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.03.2018
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class AuditEvent {

    private Object initiator;
    private String action;
    private String title;
    private String value;

    public AuditEvent(String action, String title) {
        this(null, action, title, null);
    }

    public AuditEvent(String action, String title, String value) {
        this(null, action, title, value);
    }
}
