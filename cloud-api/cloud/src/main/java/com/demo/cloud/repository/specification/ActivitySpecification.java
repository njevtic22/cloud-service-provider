package com.demo.cloud.repository.specification;

import com.demo.cloud.model.Activity;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import java.util.function.BiFunction;

public class ActivitySpecification extends EntitySpecification {
    public static Specification<Activity> getSpec(Map<String, String> filter) {
        final String[] machineKeys = {"machine", "id"};
        final String[] orgKeys = {"machine", "organization", "id"};

        BiFunction<String, String, Specification<Activity>> specParser = (key, value) -> {
            return switch (key) {
                case "minTurnedOn" -> dateMin("turnedOn", parse(value));
                case "maxTurnedOn" -> dateMax("turnedOn", parse(value));

                case "minTurnedOff" -> dateMin("turnedOff", parse(value));
                case "maxTurnedOff" -> dateMax("turnedOff", parse(value));

                case "minProfit" -> attrMin("profit", value);
                case "maxProfit" -> attrMax("profit", value);

                case "machineId" -> attrEqual(machineKeys, value);
                case "organizationId" -> attrEqual(orgKeys, value);
                default -> throw new IllegalArgumentException("Invalid filter key " + key);
            };
        };

        return getSpec(filter, specParser);
    }

    private static LocalDate parse(String value) {
        return Instant.ofEpochMilli(Long.parseLong(value)).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
