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

package com.es.lib.entity.result;

import com.es.lib.entity.IPrimaryKey;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Сущность для выборки кол-ва дочерних элементов
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 30.01.16
 */
@Entity
public class TotalCount implements IPrimaryKey<Number> {

    /**
     * ID записи для которой делаем выборку
     */
    @Id
    private Number id;
    /**
     * Кол-во дочерних элементов
     */
    private Number count;


    public TotalCount() {
    }

    public TotalCount(Number id, Number count) {
        this.id = id;
        this.count = count;
    }

    @Override
    public Number getId() {
        return id;
    }

    @Override
    public void setId(Number id) {
        this.id = id;
    }

    public Number getCount() {
        return count;
    }

    public void setCount(Number count) {
        this.count = count;
    }

    @Override
    public int hashCode() {
        return _hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return _equals(object, TotalCount.class);
    }

    @Override
    public String toString() {
        return "TotalCount{" +
               "id=" + id +
               ", count=" + count +
               '}';
    }
}
