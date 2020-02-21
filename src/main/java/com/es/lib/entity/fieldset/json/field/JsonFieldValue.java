package com.es.lib.entity.fieldset.json.field;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

/**
 * Field value
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
        this.value = value;
        this.title = value;
    }

    public JsonFieldValue(String value, String title) {
        this.value = value;
        this.title = title;
    }
}
