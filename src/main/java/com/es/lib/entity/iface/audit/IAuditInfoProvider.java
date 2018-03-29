package com.es.lib.entity.iface.audit;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.03.2018
 */
public interface IAuditInfoProvider {

    IAuditInfo getAuditInfo();

    default IAuditInfo stringAuditInfo(String value) {
        return new StringAuditInfo(value);
    }
}
