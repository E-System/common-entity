package com.es.lib.entity;

import java.util.Map;

public interface IAttributeOwner {

    Map<String, String> getAttributes();

    void setAttributes(Map<String, String> attributes);
}
