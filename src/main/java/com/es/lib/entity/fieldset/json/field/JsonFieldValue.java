package com.es.lib.entity.fieldset.json.field;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Objects;

/**
 * Field value
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 30.05.16
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonFieldValue implements Serializable {

    private String value;
    private String title;
    private String format;

    public JsonFieldValue() {}

    public JsonFieldValue(String value) {
        this.value = value;
        this.title = value;
    }

    public JsonFieldValue(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public JsonFieldValue(String value, String title, String format) {
        this.value = value;
        this.title = title;
        this.format = format;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormat() { return format; }

    public void setFormat(String format) { this.format = format; }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() == this.getClass()) {
            JsonFieldValue v = (JsonFieldValue) obj;
            return Objects.equals(value, v.value) && Objects.equals(title, v.title);
        }
        return false;
    }

    @Override
    public String toString() {
        return "JsonFieldValue{" +
               "value='" + value + '\'' +
               ", title='" + title + '\'' +
               ", format='" + format + '\'' +
               '}';
    }
}
