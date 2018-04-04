package com.dekinci.myls.sorting;

import java.util.Comparator;
import java.util.List;

public class ComplexComparator<T> implements Comparator<T> {
    private List<Comparator<T>> comparators;

    public ComplexComparator(List<Comparator<T>> comparators) {
        this.comparators = comparators;
    }

    @Override
    public int compare(T a, T b) {
        if (a.equals(b))
            return 0;

        for (Comparator<T> comparator : comparators) {
            int result = comparator.compare(a, b);
            if (result != 0)
                return result;
        }

        return 0;
    }
}
