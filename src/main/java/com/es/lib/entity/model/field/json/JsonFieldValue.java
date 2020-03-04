package com.es.lib.entity.model.field.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Field value metadata
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 30.05.16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonFieldValue implements Serializable {

    private String value;
    private String title;
    private String format;

    public JsonFieldValue(String value) {
        this(value, value, null);
    }

    public JsonFieldValue(String value, String title) {
        this(value, title, null);
    }
}
