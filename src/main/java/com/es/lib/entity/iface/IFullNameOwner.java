package com.es.lib.entity.iface;

import com.es.lib.common.model.FullName;

public interface IFullNameOwner {

    String getSurname();

    String getName();

    String getPatronymic();

    default FullName getFullName() {
        return new FullName(getSurname(), getName(), getPatronymic());
    }

    default String getFio() {
        return getFullName().getFull();
    }
}
