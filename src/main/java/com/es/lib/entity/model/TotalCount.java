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

package com.es.lib.entity.model;

import com.es.lib.entity.iface.IPrimaryKey;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity for select child elements
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 30.01.16
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TotalCount implements IPrimaryKey<Number> {

    @Id
    private Number id;
    private Number count;

    @Override
    public int hashCode() {
        return _hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return _equals(object, TotalCount.class);
    }
}
