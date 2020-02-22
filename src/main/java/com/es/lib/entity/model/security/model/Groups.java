package com.es.lib.entity.model.security.model;

import com.es.lib.common.collection.CollectionUtil;
import com.es.lib.entity.model.security.code.ISecurityAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 06.09.16
 */
public class Groups extends ArrayList<Group> {

    public Groups() { }

    public Groups(int initialCapacity) {
        super(initialCapacity);
    }

    public Groups(Collection<? extends Group> c) {
        super(c);
    }

    public Group findByCode(String code) {
        for (Group group : this) {
            if (group.getCode().equals(code)) {
                return group;
            }
        }
        return null;
    }

    public boolean isAnyTargetAvailable() {
        if (CollectionUtil.isEmpty(this)) {
            return false;
        }
        boolean result = false;
        for (Group group : this) {
            result = CollectionUtil.isNotEmpty(group.getTargets());
            if (result) {
                break;
            }
        }
        return result;
    }

    public Map<String, Collection<String>> getActions() {
        Map<String, Collection<String>> result = new LinkedHashMap<>();
        for (Group group : this) {
            result.putAll(group.getActions());
        }
        return result;
    }

    public Map<String, Map<String, Collection<String>>> getGroupedActions() {
        Map<String, Map<String, Collection<String>>> result = new LinkedHashMap<>();
        for (Group group : this) {
            result.computeIfAbsent(
                group.getCode(),
                v -> new LinkedHashMap<>()
            ).putAll(group.getActions());
        }
        return result;
    }

    public Collection<String> getKeys() {
        Collection<String> result = new ArrayList<>();
        for (Group group : this) {
            for (Target target : group.getTargets()) {
                for (String action : target.getActions()) {
                    result.add(ISecurityAction.join(target.getCode(), action));
                }
            }
        }
        return result;
    }

    public Groups filter(Collection<String> availableKeys) {
        if (CollectionUtil.isEmpty(availableKeys)) {
            return new Groups();
        }
        Map<String, Map<String, Collection<String>>> filtered = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, Collection<String>>> gentry : getGroupedActions().entrySet()) {
            for (Map.Entry<String, Collection<String>> entry : gentry.getValue().entrySet()) {
                for (String action : entry.getValue()) {
                    String key = ISecurityAction.join(entry.getKey(), action);
                    if (availableKeys.contains(key)) {
                        filtered.computeIfAbsent(
                            gentry.getKey(),
                            v -> new LinkedHashMap<>()
                        ).computeIfAbsent(
                            entry.getKey(),
                            v -> new ArrayList<>()
                        ).add(action);
                    }
                }
            }
        }
        Groups result = new Groups();
        for (Map.Entry<String, Map<String, Collection<String>>> gentry : filtered.entrySet()) {
            Collection<Target> targets = new ArrayList<>();
            for (Map.Entry<String, Collection<String>> entry : gentry.getValue().entrySet()) {
                if (CollectionUtil.isNotEmpty(entry.getValue())) {
                    targets.add(new Target(entry.getKey(), entry.getValue()));
                }
            }
            if (CollectionUtil.isNotEmpty(targets)) {
                result.add(new Group(gentry.getKey(), targets));
            }
        }
        return result;
    }
}
