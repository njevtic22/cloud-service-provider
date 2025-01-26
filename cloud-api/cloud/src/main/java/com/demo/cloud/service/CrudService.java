package com.demo.cloud.service;

import java.util.List;

public interface CrudService<ID, T> {
    List<T> getAll();
}
