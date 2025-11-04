package com.demo.cloud.repository.specification;

import com.demo.cloud.core.error.exceptions.FilterKeyException;
import com.demo.cloud.model.Activity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static com.demo.cloud.util.DateTimeUtil.toDateTime;

@Component
public class ActivitySpecification extends EntitySpecification<Activity> {
    private final String[] machineKeys = {"machine", "id"};
    private final String[] orgKeys = {"machine", "organization", "id"};
    private final String[] turnedOnKey = {"turnedOn"};
    private final String[] turnedOffKey = {"turnedOff"};

    @Override
    public Specification<Activity> get(String key, String value) {
        return switch (key) {
            case "minTurnedOn", "minTurnedOff" -> attrMin(getKeys(key), toDateTime(value));
            case "maxTurnedOn", "maxTurnedOff" -> attrMax(getKeys(key), toDateTime(value));

            case "minProfit" -> attrMin("profit", value);
            case "maxProfit" -> attrMax("profit", value);

            case "ongoing" -> isOngoing("turnedOff", value);
            case "machineId", "organizationId" -> attrEqual(getKeys(key), value);

            case "archived" -> null;
            default -> throw new FilterKeyException(key);
        };
    }

    private Specification<Activity> isOngoing(String key, String ongoingStr) {
        if (ongoingStr == null || ongoingStr.isBlank()) {
            return null;
        }

        boolean ongoing = Boolean.parseBoolean(ongoingStr);
        return ongoing ? attrNull(key) : attrNotNull(key);
    }

    private String[] getKeys(String key) {
        return switch (key) {
            case "minTurnedOn", "maxTurnedOn" -> turnedOnKey;
            case "minTurnedOff", "maxTurnedOff" -> turnedOffKey;
            case "machineId" -> machineKeys;
            case "organizationId" -> orgKeys;
            default -> throw new FilterKeyException(key);
        };
    }
}
