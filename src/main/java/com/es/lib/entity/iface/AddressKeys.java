package com.es.lib.entity.iface;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 03.03.17
 */
public interface AddressKeys {

    String FULL_NAME = "FULL_NAME";

    interface Region {

        String GUID = "REGION_GUID";
        String TYPE = "REGION_NAME";
        String TYPE_SHORT = "REGION_SNAME";
        String VALUE = "REGION_VALUE";

        String CODE = "REGION_CODE";
    }

    interface Area {

        String GUID = "AREA_GUID";
        String TYPE = "AREA_NAME";
        String TYPE_SHORT = "AREA_SNAME";
        String VALUE = "AREA_VALUE";
    }

    interface City {

        String GUID = "CITY_GUID";
        String TYPE = "CITY_NAME";
        String TYPE_SHORT = "CITY_SNAME";
        String VALUE = "CITY_VALUE";
    }

    interface Locality {

        String GUID = "LOCALITY_GUID";
        String TYPE = "LOCALITY_NAME";
        String TYPE_SHORT = "LOCALITY_SNAME";
        String VALUE = "LOCALITY_VALUE";
    }

    interface Street {

        String GUID = "STREET_GUID";
        String TYPE = "STREET_NAME";
        String TYPE_SHORT = "STREET_SNAME";
        String VALUE = "STREET_VALUE";
    }

    String HOUSE = "HOUSE";
    String HOUSING = "HOUSING";
    String BUILDING = "BUILDING";
    String FLAT_TYPE = "FLAT_TYPE";
    String FLAT = "FLAT";
    String QUALIFICATION = "QUALIFICATION";
    String ZIPCODE = "ZIPCODE";
}
