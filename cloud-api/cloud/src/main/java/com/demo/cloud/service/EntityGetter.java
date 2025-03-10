package com.demo.cloud.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface EntityGetter<ID, T> {
    Page<T> getAll(Pageable pageable, Map<String, String> filter);

    T getById(ID id);
}
