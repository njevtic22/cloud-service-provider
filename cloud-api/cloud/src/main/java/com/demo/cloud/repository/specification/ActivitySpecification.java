package com.demo.cloud.repository.specification;

import com.demo.cloud.model.Activity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.BiFunction;

import static com.demo.cloud.util.DateTimeUtil.toDate;
import static com.demo.cloud.util.DateTimeUtil.toDateTime;

public class ActivitySpecification extends EntitySpecification {
    public static Specification<Activity> getSpec(Map<String, String> filter) {
        final String[] machineKeys = {"machine", "id"};
        final String[] orgKeys = {"machine", "organization", "id"};

        BiFunction<String, String, Specification<Activity>> specParser = (key, value) -> {
            return switch (key) {
                case "minTurnedOn" -> attrMin("turnedOn", parseDateTime(value));
                case "maxTurnedOn" -> attrMax("turnedOn", parseDateTime(value));

                case "minTurnedOff" -> attrMin("turnedOff", parseDateTime(value));
                case "maxTurnedOff" -> attrMax("turnedOff", parseDateTime(value));

                case "minProfit" -> attrMin("profit", value);
                case "maxProfit" -> attrMax("profit", value);

                case "ongoing" -> isOngoing("turnedOff", value);
                case "machineId" -> attrEqual(machineKeys, value);
                case "organizationId" -> attrEqual(orgKeys, value);
                default -> throw new IllegalArgumentException("Invalid filter key " + key);
            };
        };

        return getSpec(filter, specParser);
    }

    private static Specification<Activity> isOngoing(String key, String ongoingStr) {
        if (ongoingStr == null || ongoingStr.isBlank()) {
            return null;
        }

        boolean ongoing = Boolean.parseBoolean(ongoingStr);
        return ongoing ? attrNull(key) : attrNotNull(key);
    }

    private static LocalDate parseDate(String value) {
        return toDate(value);
    }

    private static LocalDateTime parseDateTime(String value) {
        return toDateTime(value);
    }
}
