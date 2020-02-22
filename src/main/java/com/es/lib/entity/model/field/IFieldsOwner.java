package com.es.lib.entity.model.field;

import com.es.lib.entity.iface.IPrimaryKey;
import com.es.lib.entity.model.field.json.JsonFields;

import java.io.Serializable;

/**
 * Base interface for fields owner
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 31.05.16
 */
public interface IFieldsOwner<T extends Serializable> extends IPrimaryKey<T> {

    String getFieldsOwnerType();

    JsonFields getFields();

    void setFields(JsonFields fieldSet);
}
