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

package com.es.lib.entity.model.total;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 16.04.15
 */
@Entity
public class Total2 implements Serializable {

    @Id
    private long sum1;
    @Id
    private long sum2;

    public Total2() {}

    public Total2(long sum1, long sum2) {
        this.sum1 = sum1;
        this.sum2 = sum2;
    }

    public long getSum1() {
        return sum1;
    }

    public void setSum1(long sum1) {
        this.sum1 = sum1;
    }

    public long getSum2() {
        return sum2;
    }

    public void setSum2(long sum2) {
        this.sum2 = sum2;
    }

    public long getDiff() {
        return sum1 - sum2;
    }

    public long getSum() {
        return sum1 + sum2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Total2 total2 = (Total2) o;
        return sum1 == total2.sum1 &&
               sum2 == total2.sum2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum1, sum2);
    }

    @Override
    public String toString() {
        return "Total2 [" +
               "sum1=" + sum1 +
               ", sum2=" + sum2 +
               ']';
    }
}
