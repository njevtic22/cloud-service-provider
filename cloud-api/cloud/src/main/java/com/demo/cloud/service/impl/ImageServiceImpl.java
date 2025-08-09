package com.demo.cloud.service.impl;

import com.demo.cloud.service.ImageService;
import com.demo.cloud.util.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ImageServiceImpl implements ImageService {
    private final String imgRoot;

    public ImageServiceImpl(@Value("${root.images}") String imgRoot) {
        this.imgRoot = imgRoot;
    }

    @Override
    public String upload(byte[] content, String name, String type) throws IOException {
        File image = new File(imgRoot + File.separator + "organization" + File.separator + name + "." + type);
        try (FileOutputStream out = new FileOutputStream(image)) {
            out.write(content);
            out.flush();
        }
        return image.getAbsolutePath();
    }

    @Override
    public Pair<byte[], String> read(String path) throws IOException {
        if (path == null) {
            return null;
        }

        String[] split = path.split("\\.");
        // check if prefix image/ is needed - yes it is needed for displaying base64 encoded image
        String type = split[split.length - 1];

        byte[] content;
        try (FileInputStream in = new FileInputStream(path)) {
            content = in.readAllBytes();
        }
        return new Pair<>(content, type);
    }

    @Override
    public void delete(String path) throws IOException {
        File toDelete = new File(path);
        Files.delete(toDelete.toPath());
//        boolean deleted = toDelete.delete();
//        if (!deleted) {
//            throw new RuntimeException("File not deleted");
//        }
    }
}
