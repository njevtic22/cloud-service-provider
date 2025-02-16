package com.demo.cloud.repository.specification;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.BiFunction;

public class EntitySpecification {
    public static <T> Specification<T> getSpec(Map<String, String> filter, BiFunction<String, String, Specification<T>> specParser) {
        ArrayList<Specification<T>> specs = new ArrayList<>(filter.size());

        for (Map.Entry<String, String> entry : filter.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Specification<T> spec = specParser.apply(key, value);

            if (spec != null) {
                specs.add(spec);
            }
        }

        return specs
                .stream()
                .reduce(Specification::and)
                .orElse(null);
    }

    public static Map<String, String> withArchived(Map<String, String> filter, boolean archived) {
        filter.put("archived", String.valueOf(archived));
        return filter;
    }

    public static <T> Specification<T> attrLike(String key, String value) {
        return attrLike(new String[]{key}, value);
    }

    public static <T> Specification<T> attrLike(String[] keys, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return (root, query, cb) -> cb.like(cb.upper(getPath(root, keys)), "%" + value.toUpperCase() + "%");
    }

    public static <T> Specification<T> attrEqual(String key, Object value) {
        return attrEqual(new String[]{key}, value);
    }

    public static <T> Specification<T> attrEqual(String[] keys, Object value) {
        if (value == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(getPath(root, keys), value);
    }

    public static <T> Specification<T> attrMax(String key, String value) {
        return attrMax(new String[]{key}, value);
    }

    public static <T> Specification<T> attrMax(String[] keys, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return (root, query, cb) -> cb.lessThanOrEqualTo(getPath(root, keys), value);
    }

    public static <T> Specification<T> attrMin(String key, String value) {
        return attrMin(new String[]{key}, value);
    }

    public static <T> Specification<T> attrMin(String[] keys, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return (root, query, cb) -> cb.greaterThanOrEqualTo(getPath(root, keys), value);
    }

    public static <T> Specification<T> dateMin(String key, LocalDate date) {
        return dateMin(new String[]{key}, date);
    }

    public static <T> Specification<T> dateMin(String[] keys, LocalDate date) {
        if (date == null) {
            return null;
        }

        return (root, query, cb) -> cb.greaterThanOrEqualTo(getPath(root, keys), date);
    }

    public static <T> Specification<T> dateMax(String key, LocalDate date) {
        return dateMax(new String[]{key}, date);
    }

    public static <T> Specification<T> dateMax(String[] keys, LocalDate date) {
        if (date == null) {
            return null;
        }

        return (root, query, cb) -> cb.lessThanOrEqualTo(getPath(root, keys), date);
    }

    protected static <T, R> Path<R> getPath(Root<T> root, String... keys) {
        Path<R> path = root.get(keys[0]);
        for (int i = 1; i < keys.length; i++) {
            path = path.get(keys[i]);
        }
        return path;
    }
}
