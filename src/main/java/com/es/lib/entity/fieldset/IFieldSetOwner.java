package com.es.lib.entity.fieldset;

import com.es.lib.entity.IPrimaryKey;
import com.es.lib.entity.fieldset.json.FieldSetValuesJson;

import java.io.Serializable;

/**
 * Base interface for field set owner
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 31.05.16
 */
public interface IFieldSetOwner<T extends Serializable> extends IPrimaryKey<T> {

    String getFieldSetOwnerType();

    FieldSetValuesJson getFieldSetValues();

    void setFieldSetValues(FieldSetValuesJson fieldSetValues);
}
