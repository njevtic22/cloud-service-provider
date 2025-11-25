package com.demo.cloud.controller;

import com.demo.cloud.core.PaginatedResponse;
import com.demo.cloud.dto.category.AddCategoryDto;
import com.demo.cloud.dto.category.CategoryViewDto;
import com.demo.cloud.dto.category.UpdateCategoryDto;
import com.demo.cloud.filterParams.CategoryFilter;
import com.demo.cloud.mapper.CategoryMapper;
import com.demo.cloud.model.Category;
import com.demo.cloud.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService service;
    private final CategoryMapper mapper;

    public CategoryController(CategoryService service, CategoryMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> add(@Valid @RequestBody AddCategoryDto newDto, UriComponentsBuilder uriBuilder) {
        Category newCat = mapper.toModel(newDto);
        Category added = service.add(newCat);

        uriBuilder.path("api/categories/{id}");
        URI uri = uriBuilder.buildAndExpand(added.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<PaginatedResponse<CategoryViewDto>> getAll(Pageable pageable, CategoryFilter filter) {
        Page<Category> cats = service.getAll(pageable, filter.getParams());
        return ResponseEntity.ok(mapper.toDto(cats));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<CategoryViewDto> getById(@PathVariable Long id) {
        Category found = service.getById(id);
        CategoryViewDto foundDto = mapper.toDto(found);
        return ResponseEntity.ok(foundDto);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<CategoryViewDto> update(@PathVariable Long id, @Valid @RequestBody UpdateCategoryDto changesDto) {
        Category changes = mapper.toModel(changesDto);
        Category updated = service.update(id, changes);
        CategoryViewDto updatedDto = mapper.toDto(updated);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
