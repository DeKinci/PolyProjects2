package com.dekinci.myls.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.Map;
import java.util.WeakHashMap;

public class AttributeManager {
    private static AttributeManager instance;

    public static AttributeManager getManager() {
        if (instance == null)
            instance = new AttributeManager();

        return instance;
    }

    private Map<Path, BasicFileAttributes> cacheMap = new WeakHashMap<>();

    public BasicFileAttributes get(Path path) {
        var result = cacheMap.get(path);
        if (result == null) try {
            result = Files.readAttributes(path, BasicFileAttributes.class);
            cacheMap.put(path, result);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot access file");
        }
        return result;
    }

    public PosixFileAttributes getPosix(Path path) {
        try {
            var result = Files.readAttributes(path, PosixFileAttributes.class);
            cacheMap.put(path, result);
            return result;
        } catch (UnsupportedOperationException e) {
            return null;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot access file");
        }
    }
}
