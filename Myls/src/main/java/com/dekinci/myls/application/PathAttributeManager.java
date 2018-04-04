package com.dekinci.myls.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class PathAttributeManager {
    private static final AtomicReference<PathAttributeManager> instance = new AtomicReference<>();

    public static PathAttributeManager getCache() {
        if (instance.get() == null)
            synchronized (PathAttributeManager.class) {
                if (instance.get() == null)
                    instance.set(new PathAttributeManager());
            }

        return instance.get();
    }

    private Map<Path, BasicFileAttributes> cacheMap = new WeakHashMap<>();

    public BasicFileAttributes get(Path path) {
        BasicFileAttributes result = cacheMap.get(path);
        if (result == null) try {
            result = Files.readAttributes(path, BasicFileAttributes.class);
            cacheMap.put(path, result);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot access file");
        }
        return result;
    }
}
