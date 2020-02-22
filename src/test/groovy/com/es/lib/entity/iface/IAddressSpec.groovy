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

package com.es.lib.entity.iface

import com.es.lib.entity.iface.IAddress
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 25.09.15
 */
class IAddressSpec extends Specification {

    static class RealAddress implements IAddress<Long> {

        private Map<String, String> parts

        RealAddress(Map<String, String> parts) {
            this.parts = parts
        }

        @Override
        Map<String, String> getParts() {
            return parts
        }

        @Override
        void setParts(Map<String, String> parts) {
            this.parts = parts
        }

        @Override
        Long getId() {
            return null
        }

        @Override
        void setId(Long id) {

        }
    }

    static class ObjectWithAddress {

        RealAddress address

        ObjectWithAddress(RealAddress address) {
            this.address = address
        }
    }

    @Shared
    def testAddress = new IAddress<Long>() {

        @Override
        Map<String, String> getParts() {
            return [K1: "V1"]
        }

        @Override
        void setParts(Map<String, String> parts) {

        }

        @Override
        Long getId() {
            return null
        }

        @Override
        void setId(Long id) {

        }
    }

    @Shared
    def testNullPartsAddress = new IAddress<Long>() {

        @Override
        Map<String, String> getParts() {
            return null
        }

        @Override
        void setParts(Map<String, String> parts) {

        }

        @Override
        Long getId() {
            return null
        }

        @Override
        void setId(Long id) {

        }
    }

    @Shared
    def testEmptyPartsAddress = new IAddress<Long>() {

        @Override
        Map<String, String> getParts() {
            return [:]
        }

        @Override
        void setParts(Map<String, String> parts) {

        }

        @Override
        Long getId() {
            return null
        }

        @Override
        void setId(Long id) {

        }
    }

    def "SafeGetPart"() {
        expect:
        IAddress.safeGetPart(address, name) == result
        where:
        address               | name || result
        testAddress           | "K1" || "V1"
        testAddress           | "K2" || null
        testNullPartsAddress  | "K2" || null
        testEmptyPartsAddress | "K2" || null
        null                  | "K1" || null
    }

    def "SafeGetParts"() {
        expect:
        IAddress.safeGetParts(address) == result
        where:
        address               || result
        testAddress           || ["K1": "V1"]
        testNullPartsAddress  || [:]
        testEmptyPartsAddress || [:]
        null                  || [:]
    }

    def "IsNull"() {
        expect:
        IAddress.isNull(address) == result
        where:
        address               || result
        null                  || true
        testNullPartsAddress  || true
        testEmptyPartsAddress || false
    }

    def "IsEmpty"() {
        expect:
        IAddress.isEmpty(address) == result
        where:
        address               || result
        null                  || true
        testNullPartsAddress  || true
        testEmptyPartsAddress || true
        testAddress           || false
    }

    def "ClearNull"() {
        expect:
        IAddress.clear(null) == null
    }

    def "Clear"() {
        expect:
        IAddress.clear(address).parts == result
        where:
        address                     || result
        new RealAddress(null)       || null
        new RealAddress([:])        || [:]
        new RealAddress([K1: "V1"]) || [:]
    }
}
