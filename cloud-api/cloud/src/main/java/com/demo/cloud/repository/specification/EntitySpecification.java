package com.demo.cloud.repository.specification;

import com.demo.cloud.model.VirtualMachine;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public class EntitySpecification {
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

    protected static Specification<VirtualMachine> attrMax(String key, String value) {
        return attrMax(new String[]{key}, value);
    }

    protected static Specification<VirtualMachine> attrMax(String[] keys, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return (root, query, cb) -> cb.lessThanOrEqualTo(getPath(root, keys), value);
    }

    protected static Specification<VirtualMachine> attrMin(String key, String value) {
        return attrMin(new String[]{key}, value);
    }

    protected static Specification<VirtualMachine> attrMin(String[] keys, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return (root, query, cb) -> cb.greaterThanOrEqualTo(getPath(root, keys), value);
    }

    protected static <T> Path<String> getPath(Root<T> root, String... keys) {
        Path<String> path = root.get(keys[0]);
        for (int i = 1; i < keys.length; i++) {
            path = path.get(keys[i]);
        }
        return path;
    }
}
