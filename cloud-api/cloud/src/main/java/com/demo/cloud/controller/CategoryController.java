package com.demo.cloud.controller;

import com.demo.cloud.dto.category.CategoryViewDto;
import com.demo.cloud.mapper.CategoryMapper;
import com.demo.cloud.model.Category;
import com.demo.cloud.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService service;
    private final CategoryMapper mapper;

    public CategoryController(CategoryService service, CategoryMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<CategoryViewDto> getById(@PathVariable Long id) {
        Category found = service.getById(id);
        CategoryViewDto foundDto = mapper.toViewDto(found);
        return ResponseEntity.ok(foundDto);
    }
}
