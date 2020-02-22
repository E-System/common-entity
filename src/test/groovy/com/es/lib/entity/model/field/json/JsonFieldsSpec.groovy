/*
 * Copyright (c) E-System LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2019
 */

package com.es.lib.entity.model.field.json

import com.es.lib.entity.model.field.code.FieldType
import spock.lang.Specification

class JsonFieldsSpec extends Specification {

    def "Equals"() {
        setup:
        JsonFields o1 = new JsonFields()
        JsonFields o2 = new JsonFields()
        JsonFields o3 = new JsonFields()
        JsonFields o4 = new JsonFields()
        JsonFields o5 = new JsonFields()
        JsonFields o6 = new JsonFields()
        JsonField f = new JsonField(FieldType.TEXT, "1", "1", Arrays.asList(new JsonFieldValue("1", "1", "1"), new JsonFieldValue("1", "1", "1")))
        o3.put("1", f)
        o4.put("1", f)
        JsonField f1 = new JsonField(FieldType.TEXT, "2", "2", Arrays.asList(new JsonFieldValue("1", "1", "1"), new JsonFieldValue("1", "1", "1")))
        JsonField f2 = new JsonField(FieldType.TEXT, "2", "2", Arrays.asList(new JsonFieldValue("1", "1", "1"), new JsonFieldValue("1", "1", "1")))
        o5.put("2", f1)
        o6.put("2", f2)
        expect:
        o1 == o2 && o3 == o4 && o5 == o6
    }

}
