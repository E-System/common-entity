package com.es.lib.entity.model.localization;

import com.es.lib.entity.type.JsonbType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 20.10.2017
 */
public class JsonLocalization extends HashMap<String, HashMap<String, String>> {

    public static class UserType extends JsonbType {

        @Override
        public Class returnedClass() {
            return JsonLocalization.class;
        }
    }

    public JsonLocalization() { }

    public JsonLocalization(int initialCapacity) {
        super(initialCapacity);
    }

    public JsonLocalization(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public JsonLocalization(Map<? extends String, ? extends HashMap<String, String>> m) {
        super(m);
    }

    public JsonLocalization(Collection<String> fields) {
        this(fields, null);
    }

    public JsonLocalization(Collection<String> fields, Collection<String> locales) {
        putIfAbsent(fields, locales);
    }

    public void putIfAbsent(Collection<String> fields) {
        putIfAbsent(fields, null);
    }

    public void putIfAbsent(Collection<String> fields, Collection<String> locales) {
        if (fields == null) {
            return;
        }
        for (String field : fields) {
            HashMap<String, String> locs = putIfAbsent(field, new HashMap<>());
            if (locales == null || locales.isEmpty()) {
                continue;
            }
            for (String locale : locales) {
                locs.putIfAbsent(locale, null);
            }
        }
    }

    public String getValue(String code, String locale) {
        Map<String, String> valueByLocale = get(code);
        if (valueByLocale == null || StringUtils.isBlank(locale)) {
            return null;
        }
        return valueByLocale.get(locale);
    }

    public String getValue(String code, String locale, Supplier<String> defaultValueSupplier) {
        String value = getValue(code, locale);
        if (value == null) {
            return defaultValueSupplier.get();
        }
        return value;
    }

    public void setValue(String code, String locale, String value) {
        computeIfAbsent(code, s -> new HashMap<>()).put(locale, value);
    }

    public boolean isAnyAvailable(String code) {
        HashMap<String, String> locales = get(code);
        if (locales == null || locales.isEmpty()) {
            return false;
        }
        for (String value : locales.values()) {
            if (StringUtils.isNotBlank(value)) {
                return true;
            }
        }
        return false;
    }

    public void cleanEmpty() {
        Collection<String> keyForDelete = new ArrayList<>();
        Map<String, Collection<String>> localesForDelete = new HashMap<>();
        for (Entry<String, HashMap<String, String>> entry : entrySet()) {
            if (entry.getValue() == null || entry.getValue().isEmpty()) {
                keyForDelete.add(entry.getKey());
            } else {
                int size = entry.getValue().size();
                Collection<String> toDelete = new ArrayList<>();
                for (Entry<String, String> localeEntry : entry.getValue().entrySet()) {
                    if (localeEntry.getValue() == null) {
                        toDelete.add(localeEntry.getKey());
                    }
                }
                if (toDelete.size() == size) {
                    keyForDelete.add(entry.getKey());
                } else {
                    localesForDelete.put(entry.getKey(), toDelete);
                }
            }
        }
        for (String key : keyForDelete) {
            this.remove(key);
        }
        for (Entry<String, Collection<String>> entry : localesForDelete.entrySet()) {
            HashMap<String, String> locs = this.get(entry.getKey());
            if (locs != null) {
                for (String value : entry.getValue()) {
                    locs.remove(value);
                }
            }
        }
    }
}
