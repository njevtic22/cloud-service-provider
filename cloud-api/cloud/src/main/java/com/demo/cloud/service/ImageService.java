package com.demo.cloud.service;

import com.demo.cloud.util.Pair;

import java.io.IOException;

public interface ImageService {
    String upload(byte[] content, String name, String type) throws IOException;

    Pair<byte[], String> read(String path) throws IOException;

    void delete(String path) throws IOException;
}
