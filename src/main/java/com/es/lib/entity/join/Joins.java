/*
 * Copyright 2016 E-System LLC
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

package com.es.lib.entity.join;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public final class Joins {

    final Collection<Join> list;

    public Joins() {
        list = new ArrayList<>(4);
    }

    /**
     * Добавить элемент
     *
     * @param joinType тип соединения
     * @param fetch    подтягивать соединение в сущность или нет
     * @param path     путь соединения
     * @param alias    альяс соединения
     * @return
     */
    private Joins add(JoinType joinType, boolean fetch, String path, String alias) {
        list.add(new Join(joinType, fetch, path, alias));
        return this;
    }

    /**
     * Полное соединение
     *
     * @param path  путь соединения
     * @param alias альяс соединения
     * @return конструктор соединений
     */
    public Joins inner(String path, String alias) {
        return add(JoinType.INNER, false, path, alias);
    }

    /**
     * Полное соединение с подтягиванием сущности
     *
     * @param path  путь соединения
     * @param alias альяс соединения
     * @return конструктор соединений
     */
    public Joins innerFetch(String path, String alias) {
        return add(JoinType.INNER, true, path, alias);
    }

    /**
     * Правое соединение
     *
     * @param path  путь соединения
     * @param alias альяс соединения
     * @return конструктор соединений
     */
    public Joins right(String path, String alias) {
        return add(JoinType.RIGHT, false, path, alias);
    }

    /**
     * Правое соединение с подтягиванием сущности
     *
     * @param path  путь соединения
     * @param alias альяс соединения
     * @return конструктор соединений
     */
    public Joins rightFetch(String path, String alias) {
        return add(JoinType.RIGHT, true, path, alias);
    }

    /**
     * Левое соединение
     *
     * @param path  путь соединения
     * @param alias альяс соединения
     * @return конструктор соединений
     */
    public Joins left(String path, String alias) {
        return add(JoinType.LEFT, false, path, alias);
    }

    /**
     * Левое соединение с подтягиванием сущности
     *
     * @param path  путь соединения
     * @param alias альяс соединения
     * @return конструктор соединений
     */
    public Joins leftFetch(String path, String alias) {
        return add(JoinType.LEFT, true, path, alias);
    }


    public boolean isEmpty() {
        return list.isEmpty();
    }

    public String findAlias(String path) {
        if (path == null || isEmpty()) {
            return path;
        }
        String internalPath = path;
        for (Join join : list) {
            if (internalPath.startsWith(join.getPath())) {
                internalPath = internalPath.replaceFirst(join.getPath(), join.getAlias());
            }
        }
        return internalPath;
    }

    public String format(boolean useFetch) {
        if (isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Join join : list) {
            sb.append(join.getJoinType().getPrefix()).append(" join");
            if (useFetch && join.isFetch()) {
                sb.append(" fetch");
            }
            sb.append(" ").append(join.getPath());
            if (StringUtils.isNotBlank(join.getAlias())) {
                sb.append(" ").append(join.getAlias());
            }
        }
        return sb.toString();
    }
}
