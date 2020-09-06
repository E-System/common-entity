/*
 * Copyright 2020 E-System LLC
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
package com.es.lib.entity.model.field.json;

import com.es.lib.entity.model.field.json.value.AttrsFieldValue;
import com.es.lib.entity.model.field.json.value.BooleanFieldValue;
import com.es.lib.entity.model.field.json.value.NumberFieldValue;
import com.es.lib.entity.model.field.json.value.StringFieldValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true, property = "type", defaultImpl = StringFieldValue.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = StringFieldValue.class, name = StringFieldValue.CODE),
    @JsonSubTypes.Type(value = NumberFieldValue.class, name = NumberFieldValue.CODE),
    @JsonSubTypes.Type(value = BooleanFieldValue.class, name = BooleanFieldValue.CODE),
    @JsonSubTypes.Type(value = AttrsFieldValue.class, name = AttrsFieldValue.CODE),
})
public abstract class JsonFieldValue<T> implements Serializable {

    private String type;
    private T value;
    private String title;
    private String format;

    public JsonFieldValue(String type) {
        this(type, null, null, null);
    }

    public JsonFieldValue(String type, T value) {
        this(type, value, String.valueOf(value), null);
    }

    public JsonFieldValue(String type, T value, String title) {
        this(type, value, title, null);
    }

    @JsonIgnore
    public String stringValue() { return value == null ? null : String.valueOf(value); }

}
