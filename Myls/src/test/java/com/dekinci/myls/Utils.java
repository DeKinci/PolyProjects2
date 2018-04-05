package com.dekinci.myls;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Consumer;

public class Utils {
    public static void delete(Path p) {
        try {
            Files.delete(p);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public static void forEach(int n, Consumer<? super Integer> action) {
        Objects.requireNonNull(action);
        for (int i = 0; i < n; i++)
            action.accept(i);
    }

    public static Path createFile(Path path) {
        try {
            Files.createFile(path);
            return path;
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public static Path createFile(Path path, String name) {
        return createFile(subPath(path, name));
    }

    public static Path createDir(Path path) {
        try {
            Files.createDirectory(path);
            return path;
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public static Path createDir(Path path, String name) {
        return createDir(subPath(path, name));
    }

    public static Path subPath(Path path, String sub) {
        return Paths.get(path.toString(), sub);
    }
}
