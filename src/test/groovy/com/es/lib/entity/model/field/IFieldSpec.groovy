package com.es.lib.entity.model.field

import com.es.lib.entity.model.field.code.FieldType
import com.es.lib.entity.model.field.code.IFieldAttrs
import spock.lang.Specification

class IFieldSpec extends Specification {

    def "Check parent of"() {
        when:
        def f1 = new Field(FieldType.SELECTOR_SINGLE, 'CODE1', 'NAME1', [:])
        def f2 = new Field(FieldType.SELECTOR_SINGLE, 'CODE2', 'NAME2', [(IFieldAttrs.PARENT): 'CODE1'])
        then:
        f1.isParentOf(f2)
        !f2.isParentOf(f1)
    }

    class Field implements IField {

        FieldType type
        String code
        String name
        Map<String, String> attributes

        Field(FieldType type, String code, String name, Map<String, String> attributes) {
            this.type = type
            this.code = code
            this.name = name
            this.attributes = attributes
        }
    }
}
