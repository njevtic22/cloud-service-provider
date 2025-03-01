package com.demo.cloud.repository.specification;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Map;

public abstract class EntitySpecification2<T> {
    public Specification<T> get(Map<String, String> filter) {
        filter.putIfAbsent("archived", "false");

        ArrayList<Specification<T>> specs = new ArrayList<>(filter.size());

        for (Map.Entry<String, String> entry : filter.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Specification<T> spec = get(key, value);

            if (spec != null) {
                specs.add(spec);
            }
        }

        return specs
                .stream()
                .reduce(Specification::and)
                .orElse(null);
    }

    public Specification<T> get(Map<String, String> filter, boolean archived) {
        filter.put("archived", String.valueOf(archived));
        return get(filter);
    }

    public abstract Specification<T> get(String key, String value) throws IllegalArgumentException;

    public Specification<T> attrLike(String key, String value) {
        return attrLike(new String[]{key}, value);
    }

    public Specification<T> attrLike(String[] keys, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return (root, query, cb) -> cb.like(cb.upper(getPath(root, keys)), "%" + value.toUpperCase() + "%");
    }

    public Specification<T> attrEqual(String key, Object value) {
        return attrEqual(new String[]{key}, value);
    }

    public Specification<T> attrEqual(String[] keys, Object value) {
        if (value == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(getPath(root, keys), value);
    }

    protected <R> Path<R> getPath(Root<T> root, String... keys) {
        Path<R> path = root.get(keys[0]);
        for (int i = 1; i < keys.length; i++) {
            path = path.get(keys[i]);
        }
        return path;
    }
}
