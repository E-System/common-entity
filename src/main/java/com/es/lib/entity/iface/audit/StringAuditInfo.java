package com.es.lib.entity.iface.audit;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.03.2018
 */
public class StringAuditInfo implements IAuditInfo {

    private String value;

    StringAuditInfo(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
