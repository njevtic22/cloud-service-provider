package com.demo.cloud.service;

import java.util.List;
import java.util.Map;

public interface CrudService<ID, T> {
    List<T> getAll(Map<String, String> filter);
}
