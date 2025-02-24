package com.demo.cloud.service;

import com.demo.cloud.model.Drive;

public interface DriveService extends EntityGetter<Long, Drive> {
    Drive add(Drive newDrive, Long orgId);
}
