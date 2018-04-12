package com.dekinci.myls.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class PathContent {
    public static List<Path> get(Path path) {
        try (var filePathStream = Files.walk(path, 1)) {
            var manager = AttributeManager.getManager();
            return filePathStream.filter((p) -> !(manager.get(p).isDirectory() && p.equals(path)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
