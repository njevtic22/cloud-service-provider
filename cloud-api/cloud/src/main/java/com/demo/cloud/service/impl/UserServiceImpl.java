package com.demo.cloud.service.impl;

import com.demo.cloud.model.User;
import com.demo.cloud.repository.UserRepository;
import com.demo.cloud.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.demo.cloud.repository.specification.UserSpecification.getSpec;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAll(Map<String, String> filter) {
        return repository.findAll(getSpec(filter));
    }
}
