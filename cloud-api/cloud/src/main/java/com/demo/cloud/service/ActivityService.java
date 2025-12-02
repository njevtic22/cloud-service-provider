package com.demo.cloud.service;

import com.demo.cloud.model.Activity;

public interface ActivityService extends EntityGetter<Long, Activity> {
    Activity add(Long machineId);

    Activity end(Long machineId);

    long calcProfit(Long machineId);
}
