/*
 * Copyright 2020 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.es.lib.entity.model.security;

import com.es.lib.common.collection.Items;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
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
