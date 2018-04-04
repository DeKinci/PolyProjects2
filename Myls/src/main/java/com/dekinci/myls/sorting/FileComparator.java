package com.dekinci.myls.sorting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static com.dekinci.myls.sorting.Sorting.*;

public class FileComparator implements Comparator<Path> {
    private Map<Path, BasicFileAttributes> cache = new WeakHashMap<>();
    private List<Comparator<Path>> comparators = new ArrayList<>();

    public FileComparator(List<Order> orders) {
        for (Order order : orders)
            comparators.add(createComparator(order));
    }

    private Comparator<Path> createComparator(Order order) {
        Comparator<Path> result = null;

        switch (order.getAttribute()) {
            case NAME:
                result = new NameComparator();
                break;
            case SIZE:
                result = new SizeComparator();
                break;
            case DATE:
                result = new DateComparator();
                break;
        }

        if (order.isReversed())
            result = result.reversed();
        return result;
    }

    @Override
    public int compare(Path a, Path b) {
        if (a.equals(b))
            return 0;

        if (getAtAnyCost(a).isDirectory() && !getAtAnyCost(b).isDirectory())
            return -1;
        else if (getAtAnyCost(b).isDirectory() && !getAtAnyCost(a).isDirectory())
            return 1;

        for (Comparator<Path> comparator : comparators) {
            int result = comparator.compare(a, b);
            if (result != 0)
                return result;
        }

        return 0;
    }

    private class NameComparator implements Comparator<Path> {
        @Override
        public int compare(Path o1, Path o2) {
            return o1.getFileName().toString().compareTo(o2.getFileName().toString());
        }
    }

    private class SizeComparator implements Comparator<Path> {
        @Override
        public int compare(Path o1, Path o2) {
            return Long.compare(getAtAnyCost(o1).size(), getAtAnyCost(o2).size());
        }
    }

    private class DateComparator implements Comparator<Path> {
        @Override
        public int compare(Path o1, Path o2) {
            return getAtAnyCost(o1).lastModifiedTime().compareTo(getAtAnyCost(o2).lastModifiedTime());
        }
    }

    //any means necessary
    private BasicFileAttributes getAtAnyCost(Path path) {
        BasicFileAttributes result = cache.get(path);
        if (result == null) try {
            result = Files.readAttributes(path, BasicFileAttributes.class);
            cache.put(path, result);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot access file");
        }
        return result;
    }
}
