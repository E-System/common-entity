package com.es.lib.entity.model.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collection;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 30.08.16
 */
@Getter
@ToString
@RequiredArgsConstructor
public class PermissionTarget {

    private final String code;
    private final Collection<String> actions;
}
