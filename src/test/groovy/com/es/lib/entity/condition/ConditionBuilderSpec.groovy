/*
 * Copyright 2016 E-System LLC
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

package com.es.lib.entity.condition

import spock.lang.Specification

/**
 * Author: diman 
 * Date: 31.05.14
 */
class ConditionBuilderSpec extends Specification {

    def "Expect exp1 after construction"() {
        expect:
        " exp1" == cb
        where:
        cb = new Conditions().add().first("exp1").end().statement;
    }

    def "Expect (and exp1 and exp2)"() {
        expect:
        " and exp1 and exp2" == cb
        where:
        cb = new Conditions().add().first("and exp1").add().first("and exp2").end().statement;
    }
}
