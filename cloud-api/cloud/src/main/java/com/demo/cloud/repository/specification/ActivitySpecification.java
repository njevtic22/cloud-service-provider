package com.demo.cloud.repository.specification;

import com.demo.cloud.model.Activity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static com.demo.cloud.util.DateTimeUtil.toDateTime;

@Component
public class ActivitySpecification extends EntitySpecification2<Activity> {
    @Override
    public Specification<Activity> get(String key, String value) {
        final String[] machineKeys = {"machine", "id"};
        final String[] orgKeys = {"machine", "organization", "id"};

        return switch (key) {
            case "minTurnedOn" -> attrMin("turnedOn", toDateTime(value));
            case "maxTurnedOn" -> attrMax("turnedOn", toDateTime(value));

            case "minTurnedOff" -> attrMin("turnedOff", toDateTime(value));
            case "maxTurnedOff" -> attrMax("turnedOff", toDateTime(value));

            case "minProfit" -> attrMin("profit", value);
            case "maxProfit" -> attrMax("profit", value);

            case "ongoing" -> isOngoing("turnedOff", value);
            case "machineId" -> attrEqual(machineKeys, value);
            case "organizationId" -> attrEqual(orgKeys, value);

            case "archived" -> null;
            default -> throw new IllegalArgumentException("Invalid filter key " + key);
        };
    }

    private Specification<Activity> isOngoing(String key, String ongoingStr) {
        if (ongoingStr == null || ongoingStr.isBlank()) {
            return null;
        }

        boolean ongoing = Boolean.parseBoolean(ongoingStr);
        return ongoing ? attrNull(key) : attrNotNull(key);
    }
}
