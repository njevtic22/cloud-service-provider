package com.demo.cloud.filterParams;

import java.beans.ConstructorProperties;

public class ActivityFilter extends FilterParams {
    @ConstructorProperties({"min-turned-on", "max-turned-on", "min-turned-off", "max-turned-off", "min-profit", "max-profit", "machine-id", "organization-id"})
    public ActivityFilter(
            Long minTurnedOn,
            Long maxTurnedOn,
            Long minTurnedOff,
            Long maxTurnedOff,
            Float minProfit,
            Float maxProfit,
            Long machineId,
            Long organizationId
    ) {
        putIfNotNull(minTurnedOn, maxTurnedOn, minTurnedOff, maxTurnedOff, minProfit, maxProfit, machineId, organizationId);
    }
}
