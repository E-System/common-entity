package com.es.lib.entity.model.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 30.08.16
 */
@Getter
@ToString
@RequiredArgsConstructor
public class Group {

    private final String code;
    private final Collection<Target> targets;

    public Map<String, Collection<String>> getActions() {
        Map<String, Collection<String>> result = new LinkedHashMap<>();
        for (Target target : this.getTargets()) {
            result.computeIfAbsent(
                target.getCode(),
                v -> new ArrayList<>()
            ).addAll(target.getActions());
        }
        return result;
    }
}
