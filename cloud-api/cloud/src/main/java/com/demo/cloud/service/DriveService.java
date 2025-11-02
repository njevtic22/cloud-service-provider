package com.demo.cloud.service;

import com.demo.cloud.model.Drive;

public interface DriveService extends
        EntityGetter<Long, Drive>,
        EntityUpdater<Long, Drive>,
        EntityDeleter<Long>
{
    Drive add(Drive newDrive, Long orgId);

    long count();
}
