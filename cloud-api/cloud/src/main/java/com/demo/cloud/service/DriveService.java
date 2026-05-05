package com.demo.cloud.service;

import com.demo.cloud.model.Drive;
import com.demo.cloud.model.DriveType;

import java.util.List;
import java.util.Map;

public interface DriveService extends
        EntityGetter<Long, Drive>,
        EntityUpdater<Long, Drive>,
        EntityDeleter<Long>
{
    Drive add(Drive newDrive, Long orgId);

    List<Long> getAllIds(Long orgId, boolean attached);

    long count(Map<String, String> filter);

    void detachAll(Long machineId);

    float calcProfit(Long machineId, DriveType type);
}
