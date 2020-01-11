package com.es.lib.entity.iface.security.model;

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
public class Target {

    private final String code;
    private final Collection<String> actions;
}
