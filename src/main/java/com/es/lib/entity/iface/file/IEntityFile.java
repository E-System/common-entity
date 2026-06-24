package com.es.lib.entity.iface.file;

import com.es.lib.entity.IPrimaryKey;

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