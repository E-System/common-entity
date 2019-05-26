/*
 * Copyright (c) E-System LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2019
 */

package com.es.lib.entity.fieldset.json

import com.es.lib.entity.fieldset.code.FieldTypeCode
import com.es.lib.entity.fieldset.json.field.JsonFieldMetadata
import com.es.lib.entity.fieldset.json.field.JsonFieldValue
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
        JsonFieldMetadata f = new JsonFieldMetadata(FieldTypeCode.TEXT, "1", "1", Arrays.asList(new JsonFieldValue("1", "1", "1"), new JsonFieldValue("1", "1", "1")))
        o3.put("1", f)
        o4.put("1", f)
        JsonFieldMetadata f1 = new JsonFieldMetadata(FieldTypeCode.TEXT, "2", "2", Arrays.asList(new JsonFieldValue("1", "1", "1"), new JsonFieldValue("1", "1", "1")))
        JsonFieldMetadata f2 = new JsonFieldMetadata(FieldTypeCode.TEXT, "2", "2", Arrays.asList(new JsonFieldValue("1", "1", "1"), new JsonFieldValue("1", "1", "1")))
        o5.put("2", f1)
        o6.put("2", f2)
        expect:
        o1 == o2 && o3 == o4 && o5 == o6
    }

}
