package com.dekinci.myls.sorting;

import com.dekinci.myls.application.AttributeManager;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.dekinci.myls.sorting.Sorting.Order;

public class FileComparator implements Comparator<Path> {
    private ComplexComparator<Path> complexComparator;
    private AttributeManager cacheManager = AttributeManager.getManager();

    private Comparator<Path> dirComparator = Comparator.comparing(a -> !cacheManager.get(a).isDirectory());
    private Comparator<Path> nameComparator = Comparator.comparing(a -> a.getFileName().toString());
    private Comparator<Path> sizeComparator = Comparator.comparingLong(a -> cacheManager.get(a).size());
    private Comparator<Path> dateComparator = Comparator.comparing(a -> cacheManager.get(a).lastModifiedTime());

    public FileComparator(List<Order> orders) {
        var comparators = new ArrayList<Comparator<Path>>();
        for (Order order : orders)
            comparators.add(createComparator(order));
        complexComparator = new ComplexComparator<>(comparators);
    }

    private Comparator<Path> createComparator(Order order) {
        Comparator<Path> result = null;

        switch (order.getAttribute()) {
            case NAME:
                result = nameComparator;
                break;
            case SIZE:
                result = sizeComparator;
                break;
            case DATE:
                result = dateComparator;
                break;
            case FOLDER:
                result = dirComparator;
                break;
        }

        if (order.isReversed())
            result = result.reversed();
        return result;
    }

    @Override
    public int compare(Path a, Path b) {
        return complexComparator.compare(a, b);
    }
}
