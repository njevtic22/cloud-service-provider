package com.demo.cloud.repository.specification;

import com.demo.cloud.core.error.exceptions.FilterKeyException;
import com.demo.cloud.model.Category;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CategorySpecification extends EntitySpecification<Category> {
    @Override
    public Specification<Category> get(String key, String value) {
        return switch (key) {
            case "name" -> attrLike(key, value);

            case "minCpu", "minRam", "minGpu" -> attrMin(key.substring(3).toLowerCase(), value);
            case "maxCpu", "maxRam", "maxGpu" -> attrMax(key.substring(3).toLowerCase(), value);

            case "archived" -> attrEqual(key, Boolean.valueOf(value));
            default -> throw new FilterKeyException(key);
        };
    }
}
