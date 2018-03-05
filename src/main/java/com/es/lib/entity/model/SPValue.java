/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.entity.model;


import com.es.lib.entity.result.KeyValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Преобразователь системного параметра
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public class SPValue {

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
        return new Gson().fromJson(value, Object.class);
    }

    private String formatJson(String json) {
        if (json == null) {
            return null;
        }
        return new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(json));
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

    public ValueTypeCode getType() {
        return type;
    }

    public void setType(ValueTypeCode type) {
        this.type = type;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Number getNumeric() {
        return numeric;
    }

    public void setNumeric(Number numeric) {
        this.numeric = numeric;
    }

    public Boolean getBool() {
        return bool;
    }

    public void setBool(Boolean bool) {
        this.bool = bool;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Collection<KeyValue> getProperty() {
        return property;
    }

    public void setProperty(Collection<KeyValue> property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "SPValue{" +
               "type=" + type +
               ", value='" + value + '\'' +
               ", string='" + string + '\'' +
               ", price='" + price + '\'' +
               ", numeric=" + numeric +
               ", bool=" + bool +
               ", json='" + json + '\'' +
               ", property=" + property +
               '}';
    }
}
