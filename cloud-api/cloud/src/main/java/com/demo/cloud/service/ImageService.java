package com.demo.cloud.service;

import com.demo.cloud.util.Pair;

import java.io.IOException;

public interface ImageService {
    Pair<byte[], String> read(String path) throws IOException;
}
