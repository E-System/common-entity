/*
 * Copyright (c) E-System LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2019
 */

package com.es.lib.entity.fieldset.json

import com.es.lib.entity.fieldset.code.FieldTypeCode
import com.es.lib.entity.fieldset.json.field.Field
import com.es.lib.entity.fieldset.json.field.FieldValue
import spock.lang.Specification

class JsonbTypeSpec extends Specification {

    def "Equals"() {
        setup:
        FieldSetValuesJson o1 = new FieldSetValuesJson()
        FieldSetValuesJson o2 = new FieldSetValuesJson()
        FieldSetValuesJson o3 = new FieldSetValuesJson()
        FieldSetValuesJson o4 = new FieldSetValuesJson()
        FieldSetValuesJson o5 = new FieldSetValuesJson()
        FieldSetValuesJson o6 = new FieldSetValuesJson()
        Field f = new Field(FieldTypeCode.TEXT, "1", "1", Arrays.asList(new FieldValue("1", "1", "1"), new FieldValue("1", "1", "1")))
        o3.put("1", f)
        o4.put("1", f)
        Field f1 = new Field(FieldTypeCode.TEXT, "2", "2", Arrays.asList(new FieldValue("1", "1", "1"), new FieldValue("1", "1", "1")))
        Field f2 = new Field(FieldTypeCode.TEXT, "2", "2", Arrays.asList(new FieldValue("1", "1", "1"), new FieldValue("1", "1", "1")))
        o5.put("2", f1)
        o6.put("2", f2)
        expect:
        o1.equals(o2) && o3.equals(o4) && o5.equals(o6)
    }

}
