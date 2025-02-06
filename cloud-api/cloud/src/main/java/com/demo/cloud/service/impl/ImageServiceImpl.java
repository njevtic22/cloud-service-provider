package com.demo.cloud.service.impl;

import com.demo.cloud.service.ImageService;
import com.demo.cloud.util.Pair;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public Pair<byte[], String> read(String path) throws IOException {
        String[] split = path.split("\\.");
        String type = split[split.length - 1];

        byte[] content;
        try (FileInputStream in = new FileInputStream(path)) {
            content = in.readAllBytes();
        }
        return new Pair<>(content, type);
    }
}
