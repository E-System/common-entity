package com.es.lib.entity.localization;

import com.es.lib.entity.type.JsonbType;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 20.10.2017
 */
public class LocalizedJsonUserType extends JsonbType {

    @Override
    public Class returnedClass() {
        return LocalizedJson.class;
    }
}
