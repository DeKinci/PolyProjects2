package com.dekinci.myls.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathContent {
    public static List<Path> get(Path path) {
        try (Stream<Path> filePathStream = Files.walk(path, 1)) {
            return filePathStream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
