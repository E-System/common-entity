package com.es.lib.entity.model.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * File store request data
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 17.03.18
 */
@Getter
@ToString
@RequiredArgsConstructor
public class StoreRequest {

    private final String id;
    private final boolean generateEmpty;
    private final Thumb thumb;
}
