package com.dekinci.myls.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class AttributeManager {
    private static final AtomicReference<AttributeManager> instance = new AtomicReference<>();

    public static AttributeManager getManager() {
        if (instance.get() == null)
            synchronized (AttributeManager.class) {
                if (instance.get() == null)
                    instance.set(new AttributeManager());
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

    public PosixFileAttributes getPosix(Path path) {
        try {
            PosixFileAttributes result = Files.readAttributes(path, PosixFileAttributes.class);
            cacheMap.put(path, result);
            return result;
        } catch (UnsupportedOperationException e) {
            return null;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot access file");
        }
    }
}
