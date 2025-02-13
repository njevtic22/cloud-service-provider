package com.demo.cloud.mapper;

import com.demo.cloud.dto.category.AddCategoryDto;
import com.demo.cloud.dto.category.CategoryViewDto;
import com.demo.cloud.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryViewDto toViewDto(Category cat) {
        return new CategoryViewDto(
                cat.getId(),
                cat.getName(),
                cat.getCpu(),
                cat.getRam(),
                cat.getGpu()
        );
    }

    public Category toModel(AddCategoryDto dto) {
        return new Category(
                dto.getName(),
                dto.getCpu(),
                dto.getRam(),
                dto.getGpu(),
                false
        );
    }
}
