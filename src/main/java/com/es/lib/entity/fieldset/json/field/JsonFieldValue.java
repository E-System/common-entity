package com.es.lib.entity.fieldset.json.field;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Field value
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 30.05.16
 */
@Getter
@Setter
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
        this(value, title);
        this.format = format;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonFieldValue that = (JsonFieldValue) o;
        return Objects.equals(value, that.value) &&
               Objects.equals(title, that.title) &&
               Objects.equals(format, that.format);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, title, format);
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
