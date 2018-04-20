package com.es.lib.entity.fieldset;

import com.es.lib.entity.IPrimaryKey;
import com.es.lib.entity.fieldset.json.FieldSetValuesJson;

/**
 * Base interface for field set owner
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 31.05.16
 */
public interface IFieldSetValuesOwner<T extends Number> extends IPrimaryKey<T> {

    FieldSetValuesJson getFieldSetValues();

    void setFieldSetValues(FieldSetValuesJson fieldSetValues);

    String getFieldSetOwnerType();
}
