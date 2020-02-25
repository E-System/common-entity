package com.es.lib.entity.model.audit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Default IAuditInfo implementation (with only string message)
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.03.2018
 */
@Getter
@ToString
@RequiredArgsConstructor
public class AuditInfo implements IAuditInfo {

    private final String title;
    private final String value;
    private final String valueType;
}
