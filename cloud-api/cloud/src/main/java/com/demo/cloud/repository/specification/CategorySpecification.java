package com.demo.cloud.repository.specification;

import com.demo.cloud.model.Category;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;
import java.util.function.BiFunction;

public class CategorySpecification extends EntitySpecification {
    public static Specification<Category> getSpec(Map<String, String> filter, boolean archived) {
        return getSpec(withArchived(filter, archived));
    }

    public static Specification<Category> getSpec(Map<String, String> filter) {
        BiFunction<String, String, Specification<Category>> specParser = (key, value) -> {
            return switch (key) {
                case "name" -> attrLike(key, value);

                case "minCpu" -> attrMin("cpu", value);
                case "minRam" -> attrMin("ram", value);
                case "minGpu" -> attrMin("gpu", value);

                case "maxCpu" -> attrMax("cpu", value);
                case "maxRam" -> attrMax("ram", value);
                case "maxGpu" -> attrMax("gpu", value);

                case "archived" -> attrEqual(key, Boolean.valueOf(value));
                default -> throw new IllegalArgumentException("Invalid filter key " + key);
            };
        };

        return getSpec(filter, specParser);
    }
}
