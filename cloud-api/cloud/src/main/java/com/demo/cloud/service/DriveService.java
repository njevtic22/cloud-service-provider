package com.demo.cloud.service;

import com.demo.cloud.model.Drive;

import java.util.Map;

public interface DriveService extends
        EntityGetter<Long, Drive>,
        EntityUpdater<Long, Drive>,
        EntityDeleter<Long>
{
    Drive add(Drive newDrive, Long orgId);

    long count(Map<String, String> filter);
}
