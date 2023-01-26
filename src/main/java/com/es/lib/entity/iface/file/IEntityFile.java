/*
 * Copyright (c) E-System LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2022
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.es.lib.entity.iface.file;

import com.es.lib.entity.iface.IPrimaryKey;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 01.03.16
 */

public interface IEntityFile<T extends IFileStore> extends IPrimaryKey<Long> {

    boolean isDeleted();

    void setDeleted(boolean deleted);

    int getSorting();

    void setSorting(int sorting);

    String getEntity();

    void setEntity(String entity);

    Long getEntityId();

    void setEntityId(Long entityId);

    T getFileStore();
}
