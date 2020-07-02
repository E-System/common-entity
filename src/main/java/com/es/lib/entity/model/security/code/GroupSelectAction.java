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
package com.es.lib.entity.model.security.code;

import com.es.lib.common.collection.Items;
import com.es.lib.common.exception.ESRuntimeException;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Action with permission group items
 *
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 30.05.15
 */
public enum GroupSelectAction {
    /**
     * Set view for all
     */
    SET_VIEW_ALL,
    /**
     * Set edit for all
     */
    SET_EDIT_ALL,
    /**
     * Set delete for all
     */
    SET_DELETE_ALL,
    /**
     * Set all for all
     */
    SET_ALL,

    /**
     * Clear view for all
     */
    CLEAR_VIEW_ALL,
    /**
     * Clear edit for all
     */
    CLEAR_EDIT_ALL,
    /**
     * Clear delete for all
     */
    CLEAR_DELETE_ALL,
    /**
     * Clear all for all
     */
    CLEAR_ALL;

    public static void selectGroup(GroupSelectAction action, Collection<String> targets, Map<String, Collection<String>> actions, Map<String, String[]> selection) {
        if (Items.isEmpty(targets)) {
            return;
        }
        for (String target : targets) {
            final Collection<String> targetActions = actions.get(target);

            final String[] selectedActions = selection.get(target);

            Set<String> selectionSet = new HashSet<>(Arrays.asList(selectedActions));
            switch (action) {
                case SET_VIEW_ALL:
                    selection.put(target, addSelection(ISecurityAction.VIEW::equals, selectionSet, target, targetActions));
                    break;
                case CLEAR_VIEW_ALL:
                    selection.put(target, removeSelection(ISecurityAction.VIEW::equals, selectionSet, target, targetActions));
                    break;
                case SET_EDIT_ALL:
                    selection.put(target, addSelection(ISecurityAction.EDIT::equals, selectionSet, target, targetActions));
                    break;
                case CLEAR_EDIT_ALL:
                    selection.put(target, removeSelection(ISecurityAction.EDIT::equals, selectionSet, target, targetActions));
                    break;
                case SET_DELETE_ALL:
                    selection.put(target, addSelection(ISecurityAction.DELETE::equals, selectionSet, target, targetActions));
                    break;
                case CLEAR_DELETE_ALL:
                    selection.put(target, removeSelection(ISecurityAction.DELETE::equals, selectionSet, target, targetActions));
                    break;
                case SET_ALL:
                    selection.put(target, addSelection(v -> true, selectionSet, target, targetActions));
                    break;
                case CLEAR_ALL:
                    selection.put(target, new String[]{});
                    break;
                default:
                    throw new ESRuntimeException("Указан неизвестный тип операции");
            }
        }
    }

    private static String[] addSelection(Predicate<String> filter, Set<String> current, String target, Collection<String> allActions) {
        return changeSelection(
            filter,
            current::add,
            current,
            target,
            allActions
        );
    }

    private static String[] removeSelection(Predicate<String> filter, Set<String> current, String target, Collection<String> allActions) {
        return changeSelection(
            filter,
            current::remove,
            current,
            target,
            allActions
        );
    }

    private static String[] changeSelection(Predicate<String> filter, Consumer<? super String> setConsumer, Set<String> current, String target, Collection<String> allActions) {
        final List<String> resultList = allActions.stream().filter(filter).map(v -> ISecurityAction.join(target, v)).collect(Collectors.toList());
        resultList.forEach(setConsumer);
        return current.toArray(new String[0]);
    }
}
