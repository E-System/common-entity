/*
 * Copyright 2020 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.es.lib.entity.model.field.json

import com.es.lib.entity.model.field.code.FieldType
import com.es.lib.entity.model.field.json.value.StringFieldValue
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
        JsonField f = new JsonField(FieldType.TEXT, "1", "1", Arrays.asList(new StringFieldValue("1", "1", "1"), new StringFieldValue("1", "1", "1")))
        o3.put("1", f)
        o4.put("1", f)
        JsonField f1 = new JsonField(FieldType.TEXT, "2", "2", Arrays.asList(new StringFieldValue("1", "1", "1"), new StringFieldValue("1", "1", "1")))
        JsonField f2 = new JsonField(FieldType.TEXT, "2", "2", Arrays.asList(new StringFieldValue("1", "1", "1"), new StringFieldValue("1", "1", "1")))
        o5.put("2", f1)
        o6.put("2", f2)
        expect:
        o1 == o2 && o3 == o4 && o5 == o6
    }

}
