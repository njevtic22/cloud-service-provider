package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.core.error.exceptions.UniquePropertyException;
import com.demo.cloud.model.Category;
import com.demo.cloud.repository.CategoryRepository;
import com.demo.cloud.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

import static com.demo.cloud.repository.specification.CategorySpecification.getSpec;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category add(Category newCategory) {
        validateName(newCategory.getName());
        // Should cpu, ram, gpu be checked here if they are already checked in Dto?

        return repository.save(newCategory);
    }

    @Override
    public Page<Category> getAll(Pageable pageable, Map<String, String> filter) {
        return repository.findAll(getSpec(filter, false), pageable);
    }

    @Override
    public Category getById(Long id) {
        return repository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Category", id));
    }

    @Override
    public Category update(Long id, Category changes) {
        Objects.requireNonNull(changes, "Category changes must not be null.");

        Category existing = getById(id);
        if (!existing.getName().equals(changes.getName())) {
            validateName(changes.getName());
        }

        Category updated = new Category(
                existing.getId(),
                changes.getName(),
                changes.getCpu(),
                changes.getRam(),
                changes.getGpu(),
                existing.isArchived()
        );
        return repository.save(updated);
    }

    private void validateName(String name) {
        if (repository.existsByName(name)) {
            throw new UniquePropertyException("Name '" + name + "' is already taken");
        }
    }
}
