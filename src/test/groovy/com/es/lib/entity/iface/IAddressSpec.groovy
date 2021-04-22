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

import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 25.09.15
 */
class IAddressSpec extends Specification {

    static class RealAddress implements IAddress<Long> {

        Long id
        Map<String, String> parts

        RealAddress(Long id, Map<String, String> parts) {
            this.id = id
            this.parts = parts
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
        address                        || result
        new RealAddress(1, null)       || null
        new RealAddress(1, [:])        || [:]
        new RealAddress(1, [K1: "V1"]) || [:]
    }

    def "Fetch zipcode"() {
        when:
        def parts = [
            (AddressKeys.ZIPCODE): "ZIPCODE"
        ]
        then:
        IAddress.fetch("", parts, null, null, AddressKeys.ZIPCODE, null, null) == "ZIPCODE"
        IAddress.fetch("PREFIX", parts, null, null, AddressKeys.ZIPCODE, null, null) == "PREFIXZIPCODE"
    }

    def "Fetch region"() {
        when:
        def parts = [
            (AddressKeys.Region.TYPE_SHORT): "REGION_TYPE",
            (AddressKeys.Region.VALUE)     : "REGION_VALUE"
        ]
        then:
        IAddress.fetch("", parts, AddressKeys.Region.TYPE_SHORT, null, AddressKeys.Region.VALUE, ", ", " ") == "REGION_TYPE REGION_VALUE"
        IAddress.fetch("PREFIX", parts, AddressKeys.Region.TYPE_SHORT, null, AddressKeys.Region.VALUE, ", ", " ") == "PREFIX, REGION_TYPE REGION_VALUE"
    }

    def "Fetch area"() {
        when:
        def parts = [
            (AddressKeys.Area.TYPE_SHORT): "AREA_TYPE",
            (AddressKeys.Area.VALUE)     : "AREA_VALUE"
        ]
        then:
        IAddress.fetch("", parts, AddressKeys.Area.TYPE_SHORT, null, AddressKeys.Area.VALUE, ", ", ". ") == "AREA_TYPE. AREA_VALUE"
        IAddress.fetch("PREFIX", parts, AddressKeys.Area.TYPE_SHORT, null, AddressKeys.Area.VALUE, ", ", ". ") == "PREFIX, AREA_TYPE. AREA_VALUE"
        IAddress.fetch("", parts, AddressKeys.Area.TYPE_SHORT, null, AddressKeys.Area.VALUE, null, ". ") == "AREA_TYPE. AREA_VALUE"
        IAddress.fetch("PREFIX", parts, AddressKeys.Area.TYPE_SHORT, null, AddressKeys.Area.VALUE, null, ". ") == "PREFIXAREA_TYPE. AREA_VALUE"
    }

    def "Fetch city"() {
        when:
        def parts = [
            (AddressKeys.City.TYPE_SHORT): "CITY_TYPE",
            (AddressKeys.City.VALUE)     : "CITY_VALUE"
        ]
        then:
        IAddress.fetch("", parts, AddressKeys.City.TYPE_SHORT, null, AddressKeys.City.VALUE, ", ", ". ") == "CITY_TYPE. CITY_VALUE"
        IAddress.fetch("PREFIX", parts, AddressKeys.City.TYPE_SHORT, null, AddressKeys.City.VALUE, ", ", ". ") == "PREFIX, CITY_TYPE. CITY_VALUE"
    }

    def "Fetch city with empty value"() {
        when:
        def parts = [
            (AddressKeys.City.TYPE_SHORT): "CITY_TYPE"
        ]
        then:
        IAddress.fetch("", parts, AddressKeys.City.TYPE_SHORT, null, AddressKeys.City.VALUE, ", ", ". ") == ""
        IAddress.fetch("PREFIX", parts, AddressKeys.City.TYPE_SHORT, null, AddressKeys.City.VALUE, ", ", ". ") == "PREFIX"
    }

    def "Fetch locality"() {
        when:
        def parts = [
            (AddressKeys.Locality.TYPE_SHORT): "LOCALITY_TYPE",
            (AddressKeys.Locality.VALUE)     : "LOCALITY_VALUE"
        ]
        then:
        IAddress.fetch("", parts, AddressKeys.Locality.TYPE_SHORT, null, AddressKeys.Locality.VALUE, ", ", ". ") == "LOCALITY_TYPE. LOCALITY_VALUE"
        IAddress.fetch("PREFIX", parts, AddressKeys.Locality.TYPE_SHORT, null, AddressKeys.Locality.VALUE, ", ", ". ") == "PREFIX, LOCALITY_TYPE. LOCALITY_VALUE"
    }

    def "Fetch street"() {
        when:
        def parts = [
            (AddressKeys.Street.TYPE_SHORT): "STREET_TYPE",
            (AddressKeys.Street.VALUE)     : "STREET_VALUE"
        ]
        then:
        IAddress.fetch("", parts, AddressKeys.Street.TYPE_SHORT, null, AddressKeys.Street.VALUE, ", ", ". ") == "STREET_TYPE. STREET_VALUE"
        IAddress.fetch("PREFIX", parts, AddressKeys.Street.TYPE_SHORT, null, AddressKeys.Street.VALUE, ", ", ". ") == "PREFIX, STREET_TYPE. STREET_VALUE"
    }

    def "Fetch house"() {
        when:
        def parts = [
            (AddressKeys.HOUSE): "HOUSE"
        ]
        then:
        IAddress.fetch("", parts, null, null, AddressKeys.HOUSE, " ", null) == "HOUSE"
        IAddress.fetch("PREFIX", parts, null, null, AddressKeys.HOUSE, " ", null) == "PREFIX HOUSE"
    }

    def "Fetch housing"() {
        when:
        def parts = [
            (AddressKeys.HOUSING): "HOUSING"
        ]
        then:
        IAddress.fetch("", parts, null, null, AddressKeys.HOUSING, " ", "кор. ") == "кор. HOUSING"
        IAddress.fetch("PREFIX", parts, null, null, AddressKeys.HOUSING, " ", "кор. ") == "PREFIX кор. HOUSING"
    }

    def "Fetch building"() {
        when:
        def parts = [
            (AddressKeys.BUILDING): "BUILDING"
        ]
        then:
        IAddress.fetch("", parts, null, null, AddressKeys.BUILDING, " ", "стр. ") == "стр. BUILDING"
        IAddress.fetch("PREFIX", parts, null, null, AddressKeys.BUILDING, " ", "стр. ") == "PREFIX стр. BUILDING"
    }

    def "Fetch flat"() {
        when:
        def parts = [
            (AddressKeys.FLAT): "FLAT"
        ]
        def partsWithFlatType = [
            (AddressKeys.FLAT_TYPE): "офис",
            (AddressKeys.FLAT)     : "FLAT"
        ]
        then:
        IAddress.fetch("", parts, AddressKeys.FLAT_TYPE, "кв", AddressKeys.FLAT, " ", ". ") == "кв. FLAT"
        IAddress.fetch("PREFIX", parts, AddressKeys.FLAT_TYPE, "кв", AddressKeys.FLAT, " ", ". ") == "PREFIX кв. FLAT"
        IAddress.fetch("", partsWithFlatType, AddressKeys.FLAT_TYPE, "кв", AddressKeys.FLAT, " ", ". ") == "офис. FLAT"
        IAddress.fetch("PREFIX", partsWithFlatType, AddressKeys.FLAT_TYPE, "кв", AddressKeys.FLAT, " ", ". ") == "PREFIX офис. FLAT"
    }

    def "Fetch qualification"() {
        when:
        def parts = [
            (AddressKeys.QUALIFICATION): "QUALIFICATION"
        ]
        then:
        IAddress.fetch("", parts, null, null, AddressKeys.QUALIFICATION, " ", null) == "QUALIFICATION"
        IAddress.fetch("PREFIX", parts, null, null, AddressKeys.QUALIFICATION, " ", null) == "PREFIX QUALIFICATION"
    }

    def "Create full address"() {
        when:
        def parts = [
            (AddressKeys.ZIPCODE)            : "ZIPCODE",
            (AddressKeys.Region.TYPE_SHORT)  : "REGION_TYPE",
            (AddressKeys.Region.VALUE)       : "REGION_VALUE",
            (AddressKeys.Area.TYPE_SHORT)    : "AREA_TYPE",
            (AddressKeys.Area.VALUE)         : "AREA_VALUE",
            (AddressKeys.City.TYPE_SHORT)    : "CITY_TYPE",
            (AddressKeys.City.VALUE)         : "CITY_VALUE",
            (AddressKeys.Locality.TYPE_SHORT): "LOCALITY_TYPE",
            (AddressKeys.Locality.VALUE)     : "LOCALITY_VALUE",
            (AddressKeys.Street.TYPE_SHORT)  : "STREET_TYPE",
            (AddressKeys.Street.VALUE)       : "STREET_VALUE",
            (AddressKeys.HOUSE)              : "HOUSE",
            (AddressKeys.HOUSING)            : "HOUSING",
            (AddressKeys.BUILDING)           : "BUILDING",
            (AddressKeys.FLAT)               : "FLAT"
        ]
        def partsWithFlatType = [
            (AddressKeys.ZIPCODE)            : "ZIPCODE",
            (AddressKeys.Region.TYPE_SHORT)  : "REGION_TYPE",
            (AddressKeys.Region.VALUE)       : "REGION_VALUE",
            (AddressKeys.Area.TYPE_SHORT)    : "AREA_TYPE",
            (AddressKeys.Area.VALUE)         : "AREA_VALUE",
            (AddressKeys.City.TYPE_SHORT)    : "CITY_TYPE",
            (AddressKeys.City.VALUE)         : "CITY_VALUE",
            (AddressKeys.Locality.TYPE_SHORT): "LOCALITY_TYPE",
            (AddressKeys.Locality.VALUE)     : "LOCALITY_VALUE",
            (AddressKeys.Street.TYPE_SHORT)  : "STREET_TYPE",
            (AddressKeys.Street.VALUE)       : "STREET_VALUE",
            (AddressKeys.HOUSE)              : "HOUSE",
            (AddressKeys.HOUSING)            : "HOUSING",
            (AddressKeys.BUILDING)           : "BUILDING",
            (AddressKeys.FLAT_TYPE)          : "FLAT_TYPE",
            (AddressKeys.FLAT)               : "FLAT"
        ]
        then:
        IAddress.toStringShort(new RealAddress(1, parts)) == "AREA_TYPE. AREA_VALUE, CITY_TYPE. CITY_VALUE, LOCALITY_TYPE. LOCALITY_VALUE, STREET_TYPE. STREET_VALUE HOUSE кор. HOUSING стр. BUILDING кв. FLAT"
        IAddress.toStringShort(parts) == "AREA_TYPE. AREA_VALUE, CITY_TYPE. CITY_VALUE, LOCALITY_TYPE. LOCALITY_VALUE, STREET_TYPE. STREET_VALUE HOUSE кор. HOUSING стр. BUILDING кв. FLAT"
        IAddress.toStringShort(new RealAddress(1, partsWithFlatType)) == "AREA_TYPE. AREA_VALUE, CITY_TYPE. CITY_VALUE, LOCALITY_TYPE. LOCALITY_VALUE, STREET_TYPE. STREET_VALUE HOUSE кор. HOUSING стр. BUILDING FLAT_TYPE. FLAT"
        IAddress.toStringShort(partsWithFlatType) == "AREA_TYPE. AREA_VALUE, CITY_TYPE. CITY_VALUE, LOCALITY_TYPE. LOCALITY_VALUE, STREET_TYPE. STREET_VALUE HOUSE кор. HOUSING стр. BUILDING FLAT_TYPE. FLAT"
        IAddress.toStringFull(new RealAddress(1, parts)) == "ZIPCODE, REGION_TYPE REGION_VALUE, AREA_TYPE. AREA_VALUE, CITY_TYPE. CITY_VALUE, LOCALITY_TYPE. LOCALITY_VALUE, STREET_TYPE. STREET_VALUE HOUSE кор. HOUSING стр. BUILDING кв. FLAT"
        IAddress.toStringFull(parts) == "ZIPCODE, REGION_TYPE REGION_VALUE, AREA_TYPE. AREA_VALUE, CITY_TYPE. CITY_VALUE, LOCALITY_TYPE. LOCALITY_VALUE, STREET_TYPE. STREET_VALUE HOUSE кор. HOUSING стр. BUILDING кв. FLAT"
        IAddress.toStringFull(new RealAddress(1, partsWithFlatType)) == "ZIPCODE, REGION_TYPE REGION_VALUE, AREA_TYPE. AREA_VALUE, CITY_TYPE. CITY_VALUE, LOCALITY_TYPE. LOCALITY_VALUE, STREET_TYPE. STREET_VALUE HOUSE кор. HOUSING стр. BUILDING FLAT_TYPE. FLAT"
        IAddress.toStringFull(partsWithFlatType) == "ZIPCODE, REGION_TYPE REGION_VALUE, AREA_TYPE. AREA_VALUE, CITY_TYPE. CITY_VALUE, LOCALITY_TYPE. LOCALITY_VALUE, STREET_TYPE. STREET_VALUE HOUSE кор. HOUSING стр. BUILDING FLAT_TYPE. FLAT"
    }
}
