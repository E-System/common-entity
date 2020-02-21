/*
 * Copyright (c) E-System LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2019
 */

package com.es.lib.entity.fieldset.json

import com.es.lib.entity.fieldset.code.FieldTypeCode
import com.es.lib.entity.fieldset.json.field.JsonField
import com.es.lib.entity.fieldset.json.field.JsonFieldValue
import spock.lang.Specification

class JsonFieldSetSpec extends Specification {

    def "Equals"() {
        setup:
        JsonFieldSet o1 = new JsonFieldSet()
        JsonFieldSet o2 = new JsonFieldSet()
        JsonFieldSet o3 = new JsonFieldSet()
        JsonFieldSet o4 = new JsonFieldSet()
        JsonFieldSet o5 = new JsonFieldSet()
        JsonFieldSet o6 = new JsonFieldSet()
        JsonField f = new JsonField(FieldTypeCode.TEXT, "1", "1", Arrays.asList(new JsonFieldValue("1", "1", "1"), new JsonFieldValue("1", "1", "1")))
        o3.put("1", f)
        o4.put("1", f)
        JsonField f1 = new JsonField(FieldTypeCode.TEXT, "2", "2", Arrays.asList(new JsonFieldValue("1", "1", "1"), new JsonFieldValue("1", "1", "1")))
        JsonField f2 = new JsonField(FieldTypeCode.TEXT, "2", "2", Arrays.asList(new JsonFieldValue("1", "1", "1"), new JsonFieldValue("1", "1", "1")))
        o5.put("2", f1)
        o6.put("2", f2)
        expect:
        o1 == o2 && o3 == o4 && o5 == o6
    }

}
