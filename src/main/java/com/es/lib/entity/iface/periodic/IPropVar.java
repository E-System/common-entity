package com.es.lib.entity.iface.periodic;

import java.io.Serializable;


/**
 * Periodic entity attribute var interface for JSONB
 *
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 27.03.2020
 */
public interface IPropVar extends Serializable {

    <T extends IPropVar> T copy();
}
