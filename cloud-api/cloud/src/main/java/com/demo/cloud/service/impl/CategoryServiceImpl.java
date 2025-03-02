package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.core.error.exceptions.MultipleAffectedRowsException;
import com.demo.cloud.core.error.exceptions.UniquePropertyException;
import com.demo.cloud.model.Category;
import com.demo.cloud.repository.CategoryRepository;
import com.demo.cloud.repository.specification.EntitySpecification2;
import com.demo.cloud.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final EntitySpecification2<Category> spec;

    public CategoryServiceImpl(CategoryRepository repository, EntitySpecification2<Category> spec) {
        this.repository = repository;
        this.spec = spec;
    }

    @Override
    public Category add(Category newCategory) {
        validateName(newCategory.getName());
        // Should cpu, ram, gpu be checked here if they are already checked in Dto?

        return repository.save(newCategory);
    }

    @Override
    public Page<Category> getAll(Pageable pageable, Map<String, String> filter) {
        return repository.findAll(spec.get(filter), pageable);
    }

    @Override
    public Category getById(Long id) {
        return repository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Category", id));
    }

    @Override
    public Category update(Long id, Category changes) {
        Objects.requireNonNull(changes, "Category changes must not be null.");

        // TODO: prevent update if machine is active

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

    @Override
    @Transactional
    public void delete(Long id) {
        Objects.requireNonNull(id, "Id must not be null.");

        if (!repository.existsByIdAndArchivedFalse(id)) {
            throw new EntityNotFoundException("Category", id);
        }

        int rowsAffected = repository.archiveById(id);
        if (rowsAffected != 1) {
            throw new MultipleAffectedRowsException("Categories", "delete (by id)");
        }
    }

    private void validateName(String name) {
        if (repository.existsByName(name)) {
            throw new UniquePropertyException("Name '" + name + "' is already taken");
        }
    }
}
