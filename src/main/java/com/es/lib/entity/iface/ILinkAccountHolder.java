package com.es.lib.entity.iface;

public interface ILinkAccountHolder extends IAttrsOwner {

    /**
     * Active account link
     */
    String ACTIVE_COMPANY_PERSON_ID = "ACTIVE_COMPANY_PERSON_ID";

    default boolean isActiveCompanyPersonFilled() {
        return isAttrFilled(ACTIVE_COMPANY_PERSON_ID);
    }

    default Integer getActiveCompanyPersonId() {
        return getIntAttr(ACTIVE_COMPANY_PERSON_ID);
    }

    default void setActiveCompanyPersonId(Integer id) {
        setAttr(ACTIVE_COMPANY_PERSON_ID, id != null ? String.valueOf(id) : null);
    }
}
