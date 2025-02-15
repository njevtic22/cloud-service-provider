package com.demo.cloud.service;

public interface EntityUpdater<ID, T> {
    T update(ID id, T changes);
}
