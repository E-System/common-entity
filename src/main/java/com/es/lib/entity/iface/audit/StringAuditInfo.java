package com.es.lib.entity.iface.audit;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * Default IAuditInfo implementation (with only string message)
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.03.2018
 */
@AllArgsConstructor
@ToString
public class StringAuditInfo implements IAuditInfo {

    private String value;
}
