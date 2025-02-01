package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.model.Role;
import com.demo.cloud.repository.RoleRepository;
import com.demo.cloud.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Role> getAll(Pageable pageable, Map<String, String> filter) {
        String name = filter.get("name");
        if (name != null) {
            return repository.findAllByName(pageable, name);
        }
        return repository.findAll(pageable);
    }

    @Override
    public Role getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role", id));
    }

    @Override
    public Role getByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Role", "name", name));
    }
}
