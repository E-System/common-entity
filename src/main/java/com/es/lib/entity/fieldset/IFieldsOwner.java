package com.es.lib.entity.fieldset;

import com.es.lib.entity.IPrimaryKey;
import com.es.lib.entity.fieldset.json.JsonFields;

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
