package com.demo.cloud.repository.specification;

import org.springframework.data.jpa.domain.Specification;

public class ClassSpecification {
    public static <T> Specification<T> attrLike(String attr, String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return null;
        }

        return (root, query, cb) -> cb.like(cb.upper(root.get(attr)), "%" + keyword.toUpperCase() + "%");
    }
}
