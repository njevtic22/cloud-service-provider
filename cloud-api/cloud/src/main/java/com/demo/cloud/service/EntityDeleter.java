package com.demo.cloud.service;

public interface EntityDeleter<ID> {
    void delete(ID id);
}
