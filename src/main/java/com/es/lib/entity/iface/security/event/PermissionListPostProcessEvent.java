package com.es.lib.entity.iface.security.event;

import com.es.lib.entity.iface.security.model.PermissionListBuilder;
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
public class PermissionListPostProcessEvent {

    private final PermissionListBuilder builder;
}
