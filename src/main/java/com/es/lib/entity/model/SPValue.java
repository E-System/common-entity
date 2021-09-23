/*
 * Copyright 2018 E-System LLC
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
package com.es.lib.entity.model;

import com.es.lib.common.Jsons;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Value type transformer
 *
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 05.03.18
 */
@ToString
@Getter
@Setter
public class SPValue {

    private static final ObjectMapper OBJECT_MAPPER = Jsons.mapper();

    private ValueTypeCode type;
    private String value;
    private String string;
    private String price;
    private Number numeric;
    private Boolean bool;
    private String json;
    private Collection<KeyValue> property;

    public SPValue(ValueTypeCode type, String value) throws ParseException {
        this.type = type;
        this.value = value;
        Object parsedValue = parse(value, false);
        switch (type) {
            case STRING:
                string = (String) parsedValue;
                break;
            case PRICE:
                price = (String) parsedValue;
                break;
            case NUMERIC:
                numeric = (Number) parsedValue;
                break;
            case JSON:
                json = (String) parsedValue;
                break;
            case PROPERTY:
                property = (Collection<KeyValue>) parsedValue;
                break;
            default:
                bool = (Boolean) parsedValue;
                break;
        }
    }

    public static Object parse(TypedValue value) throws ParseException {
        return new SPValue(
            value.getType(),
            value.getValue()
        ).getObject();
    }

    public Object getObject() throws ParseException {
        return parse(this.value, true);
    }

    private Object parse(final String value, boolean jsonParse) throws ParseException {
        Object result;
        switch (type) {
            case STRING:
                result = parseString(value);
                break;
            case PRICE:
                result = parsePrice(value);
                break;
            case NUMERIC:
                result = parseNumber(value);
                break;
            case JSON:
                result = parseJson(value, jsonParse);
                break;
            case PROPERTY:
                result = parseProperty(value);
                break;
            case BOOLEAN:
                result = parseBoolean(value);
                break;
            default:
                result = null;
        }
        return result;
    }

    public String getValue() {
        switch (type) {
            case STRING:
                this.value = formatString(string);
                break;
            case PRICE:
                this.value = formatPrice(price);
                break;
            case NUMERIC:
                this.value = formatNumber(numeric);
                break;
            case JSON:
                this.value = formatJson(json);
                break;
            case PROPERTY:
                this.value = formatProperty(property);
                break;
            case BOOLEAN:
                this.value = formatBoolean(bool);
                break;
        }
        return this.value;
    }

    private String parseString(String value) {
        return value;
    }

    private String formatString(String value) {
        return value;
    }

    private String parsePrice(String value) {
        return value;
    }

    private String formatPrice(String value) {
        return value;
    }

    private Boolean parseBoolean(String value) {
        return Boolean.parseBoolean(value);
    }

    private String formatBoolean(Boolean value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    private Collection<KeyValue> parseProperty(String value) {
        if (value == null) {
            return new ArrayList<>();
        }
        String[] rows = value.trim().split("\n");
        List<KeyValue> result = new ArrayList<>(rows.length);
        for (String row : rows) {
            String[] parts = row.trim().split("=");
            if (parts.length == 1) {
                result.add(new KeyValue(parts[0], ""));
            }
            if (parts.length == 2) {
                result.add(new KeyValue(parts[0], parts[1]));
            }
        }
        return result;
    }

    private String formatProperty(Collection<KeyValue> value) {
        if (value == null || value.size() == 0) {
            return null;
        }
        return value.stream().map(KeyValue::getPropertyRow).collect(Collectors.joining("\n"));
    }

    private Object parseJson(String value, boolean jsonParse) {
        if (value == null) {
            return null;
        }
        if (!jsonParse) {
            return value;
        }
        try {
            return OBJECT_MAPPER.readValue(value, Object.class);
        } catch (IOException e) {
            return null;
        }
    }

    private String formatJson(String json) {
        if (json == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(OBJECT_MAPPER.readValue(json, Object.class));
        } catch (IOException e) {
            return null;
        }
    }

    private Number parseNumber(String value) throws ParseException {
        if (value == null) {
            return null;
        }
        return createNumberFormat().parse(value);
    }

    private String formatNumber(Number value) {
        if (value == null) {
            return null;
        }
        return createNumberFormat().format(value);
    }

    private static DecimalFormat createNumberFormat() {
        DecimalFormat result = new DecimalFormat();
        result.setGroupingUsed(false);
        result.setMinimumFractionDigits(0);
        result.setMaximumFractionDigits(3);
        DecimalFormatSymbols dfs = result.getDecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        result.setDecimalFormatSymbols(dfs);
        return result;
    }

    public Long getPriceAsLong() {
        if (price == null) {
            return null;
        }
        return Long.parseLong(price);
    }
}
