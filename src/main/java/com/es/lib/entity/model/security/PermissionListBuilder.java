package com.es.lib.entity.model.security;

import com.es.lib.common.collection.Items;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 04.09.16
 */
@ToString
public class PermissionListBuilder {

    private final Map<String, Map<String, Set<String>>> items;

    public PermissionListBuilder() {
        items = new LinkedHashMap<>();
    }

    public PermissionListBuilder add(String group, String target, String... actions) {
        getActions(group, target).addAll(Arrays.asList(actions));
        return this;
    }

    public PermissionListBuilder remove(String group) {
        items.remove(group);
        return this;
    }

    public PermissionListBuilder remove(String group, String target) {
        getTargets(group).remove(target);
        return this;
    }

    public PermissionListBuilder remove(String group, String target, String... actions) {
        getActions(group, target).removeAll(Arrays.asList(actions));
        return this;
    }

    private Set<String> getActions(String group, String target) {
        return getTargets(group).computeIfAbsent(
            target,
            v -> new LinkedHashSet<>()
        );
    }

    private Map<String, Set<String>> getTargets(String group) {
        return items.computeIfAbsent(
            group,
            v -> new LinkedHashMap<>()
        );
    }

    public PermissionGroups build() {
        return items.entrySet().stream()
                    .map(groupEntry -> new PermissionGroup(groupEntry.getKey(), buildTargets(groupEntry.getValue())))
                    .filter(v -> Items.isNotEmpty(v.getTargets())).collect(Collectors.toCollection(PermissionGroups::new));
    }

    private Collection<PermissionTarget> buildTargets(Map<String, Set<String>> targets) {
        return targets.entrySet().stream()
                      .filter(v -> Items.isNotEmpty(v.getValue()))
                      .map(v -> new PermissionTarget(v.getKey(), v.getValue()))
                      .collect(Collectors.toList());
    }
}
