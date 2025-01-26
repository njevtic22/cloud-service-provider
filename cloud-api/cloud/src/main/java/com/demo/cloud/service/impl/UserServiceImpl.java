package com.demo.cloud.service.impl;

import com.demo.cloud.model.User;
import com.demo.cloud.repository.UserRepository;
import com.demo.cloud.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }
}
