package com.es.lib.entity.event.security;

import com.es.lib.entity.model.security.PermissionListBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 30.08.16
 */
@Getter
@ToString
@RequiredArgsConstructor
public class PermissionListInitEvent {

    private final PermissionListBuilder builder;
}