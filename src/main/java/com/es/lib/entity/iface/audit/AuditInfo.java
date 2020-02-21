package com.es.lib.entity.iface.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Default IAuditInfo implementation (with only string message)
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.03.2018
 */
@Getter
@ToString
@AllArgsConstructor
public class AuditInfo implements IAuditInfo {

    private final String title;
    private final String value;
}
