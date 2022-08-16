package com.es.lib.entity.event.file;

import com.es.lib.entity.iface.file.IFileStore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class RollbackFileStoreEvent {

    private final IFileStore fileStore;
}
