package com.es.lib.entity.fieldset;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 08.04.19
 */
public interface IFieldTemplateEvaluator {

    String evaluateVelocity(String template, Map<String, Object> context);

    default Map<String, Object> createGeneralContext() {
        return new HashMap<>();
    }
}
