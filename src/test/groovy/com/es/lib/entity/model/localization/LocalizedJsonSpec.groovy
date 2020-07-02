package com.es.lib.entity.model.localization

import spock.lang.Specification

import java.util.function.Supplier

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 23.10.2017
 */
class LocalizedJsonSpec extends Specification {

    def "GetValue"() {
        when:
        def loc = new JsonLocalization()
        loc['title'] = ['ru_RU': 'V_RU', 'en_US': 'V_EN']
        then:
        loc.getValue('title', null) == null
        loc.getValue('title', 'ru_RU') == 'V_RU'
    }

    def "GetValue with supplier"() {
        when:
        def loc = new JsonLocalization()
        loc['title'] = ['ru_RU': 'V_RU', 'en_US': 'V_EN']
        then:
        loc.getValue('title', null, new Supplier<String>() {
            @Override
            String get() {
                return 'V_SUPPLIER'
            }
        }) == 'V_SUPPLIER'
        loc.getValue('title', 'ru_RU') == 'V_RU'
    }

    def "SetValue"() {
        when:
        def loc = new JsonLocalization()
        loc['title'] = ['ru_RU': 'V_RU', 'en_US': 'V_EN']
        loc.setValue('title', 'ru_RU', 'V_RU_NEW')
        loc.setValue('title1', 'ru_RU', 'V_RU_NEW')
        then:
        loc.getValue('title', 'ru_RU') == 'V_RU_NEW'
        loc.getValue('title1', 'ru_RU') == 'V_RU_NEW'
        loc.getValue('title', 'en_US') == 'V_EN'

    }

    def "CleanEmpty"() {
        when:
        def loc = new JsonLocalization()
        loc['title'] = ['ru_RU': 'V_RU', 'en_US': 'V_EN']
        loc['title1'] = [:]
        loc['title2'] = ['ru_RU': null]
        loc.cleanEmpty()
        then:
        loc.getValue('title', null) == null
        loc.getValue('title', 'ru_RU') == 'V_RU'
        loc.getValue('title', 'en_US') == 'V_EN'
        !loc.containsKey('title1')
        !loc.containsKey('title2')
    }

    def "Put if absent"() {
        when:
        def loc = new JsonLocalization()
        loc['title'] = ['ru_RU': 'V_RU', 'en_US': 'V_EN']
        loc.putIfAbsent(['title', 'content'])
        then:
        loc.getValue('title', 'ru_RU') == 'V_RU'
        loc.getValue('title', 'en_US') == 'V_EN'
        loc.getValue('content', 'en_US') == null
    }

    def "Put if absent with initial empty"() {
        when:
        def loc = new JsonLocalization()
        loc.putIfAbsent(['title', 'content'])
        then:
        loc.containsKey('title')
        loc.containsKey('content')
        loc.getValue('title', 'ru_RU') == null
        loc.getValue('title', 'en_US') == null
        loc.getValue('content', 'en_US') == null
    }

    def "IsAnyAvailable not available"() {
        when:
        def loc = new JsonLocalization()
        loc.putIfAbsent(['title', 'content'])
        then:
        !loc.isAnyAvailable('title')
    }

    def "IsAnyAvailable available"() {
        when:
        def loc = new JsonLocalization()
        loc['title'] = ['ru_RU': 'V_RU', 'en_US': null]
        then:
        loc.isAnyAvailable('title')
    }
}
