package com.es.lib.entity.model.security;

import com.es.lib.common.collection.Cols;
import com.es.lib.entity.model.security.code.ISecurityAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 06.09.16
 */
public class PermissionGroups extends ArrayList<PermissionGroup> {

    public PermissionGroups() { }

    public PermissionGroups(int initialCapacity) {
        super(initialCapacity);
    }

    public PermissionGroups(Collection<? extends PermissionGroup> c) {
        super(c);
    }

    public PermissionGroup findByCode(String code) {
        for (PermissionGroup group : this) {
            if (group.getCode().equals(code)) {
                return group;
            }
        }
        return null;
    }

    public boolean isAnyTargetAvailable() {
        if (Cols.isEmpty(this)) {
            return false;
        }
        boolean result = false;
        for (PermissionGroup group : this) {
            result = Cols.isNotEmpty(group.getTargets());
            if (result) {
                break;
            }
        }
        return result;
    }

    public Map<String, Collection<String>> getActions() {
        Map<String, Collection<String>> result = new LinkedHashMap<>();
        for (PermissionGroup group : this) {
            result.putAll(group.getActions());
        }
        return result;
    }

    public Map<String, Map<String, Collection<String>>> getGroupedActions() {
        Map<String, Map<String, Collection<String>>> result = new LinkedHashMap<>();
        for (PermissionGroup group : this) {
            result.computeIfAbsent(
                group.getCode(),
                v -> new LinkedHashMap<>()
            ).putAll(group.getActions());
        }
        return result;
    }

    public Collection<String> getKeys() {
        Collection<String> result = new ArrayList<>();
        for (PermissionGroup group : this) {
            for (PermissionTarget target : group.getTargets()) {
                for (String action : target.getActions()) {
                    result.add(ISecurityAction.join(target.getCode(), action));
                }
            }
        }
        return result;
    }

    public PermissionGroups filter(Collection<String> availableKeys) {
        if (Cols.isEmpty(availableKeys)) {
            return new PermissionGroups();
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
        PermissionGroups result = new PermissionGroups();
        for (Map.Entry<String, Map<String, Collection<String>>> gentry : filtered.entrySet()) {
            Collection<PermissionTarget> targets = new ArrayList<>();
            for (Map.Entry<String, Collection<String>> entry : gentry.getValue().entrySet()) {
                if (Cols.isNotEmpty(entry.getValue())) {
                    targets.add(new PermissionTarget(entry.getKey(), entry.getValue()));
                }
            }
            if (Cols.isNotEmpty(targets)) {
                result.add(new PermissionGroup(gentry.getKey(), targets));
            }
        }
        return result;
    }
}
