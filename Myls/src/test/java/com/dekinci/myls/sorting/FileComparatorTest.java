package com.dekinci.myls.sorting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dekinci.myls.sorting.Sorting.Attribute;
import static com.dekinci.myls.sorting.Sorting.Order;
import static java.nio.file.StandardOpenOption.APPEND;
import static org.junit.jupiter.api.Assertions.*;

class FileComparatorTest {
    private List<Path> paths = new ArrayList<>();

    private final static int files = 8;
    private final static int dirs = 2;
    private final static int total = files + dirs;
    private final static String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Fusce tristique euismod neque, in venenatis velit tempus non. Donec hendrerit imperd" +
            "iet sem quis blandit. Morbi a enim vitae neque euismod tempor. Duis sollicitudin rut" +
            "rum consequat. Pellentesque habitant morbi tristique senectus et netus et malesuada " +
            "fames ac turpis egestas. Cras congue tortor et sem aliquet, sit amet porttitor magna" +
            " semper. Morbi volutpat efficitur ipsum ut consectetur. Donec imperdiet lectus est, " +
            "a auctor nisl porta a. Quisque ultricies eu nisi at sollicitudin.";

    @BeforeEach
    void setUp() throws IOException {
        for (int i = 0; i < files; i++) {
            Path path = Files.createTempFile("file_" + i + "_", null);
            for (int j = 0; j < i; j++)
                Files.write(path, text.getBytes(), APPEND);
            paths.add(path);
        }

        Files.write(paths.get(1), text.getBytes(), APPEND);

        for (int i = 0; i < dirs; i++)
            paths.add(Files.createTempDirectory("dir_" + i + "_"));
    }

    @AfterEach
    void tearDown() {
        paths.forEach(this::delete);
    }

    private void delete(Path p) {
        try {
            Files.delete(p);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Test
    void test() throws IOException {
        FileComparator comparator = createComparator(Order.reversed(Attribute.SIZE), Order.straight(Attribute.NAME));
        print();
        paths.sort(comparator);
        print();

        for (int i = 0; i < total; i++) {
            BasicFileAttributes attributesI = Files.readAttributes(paths.get(i), BasicFileAttributes.class);
            boolean iDir = attributesI.isDirectory();

            for (int j = 0; j < total; j++) {
                BasicFileAttributes attributesJ = Files.readAttributes(paths.get(j), BasicFileAttributes.class);
                boolean jDir = attributesJ.isDirectory();

                if (iDir && jDir)
                    assertTrue(j > i ? nameBigger(j, i) : nameBigger(i, j));
                else if (iDir)
                    assertTrue(i < j);
                else if (jDir)
                    assertTrue(i > j);
                else {
                    if (attributesI.size() == attributesJ.size())
                        assertTrue(j > i ? nameBigger(j, i) : nameBigger(i, j));
                    else if (attributesI.size() > attributesJ.size())
                        assertTrue(i < j);
                    else if (attributesI.size() < attributesJ.size())
                        assertTrue(i > j);
                }
            }
        }
    }

    private void print() {
        paths.forEach(System.out::println);
        System.out.println("********************************************");
    }

    private boolean nameBigger(int a, int b) {
        return paths.get(a).getFileName().toString().compareTo(paths.get(b).getFileName().toString()) >= 0;
    }

    private FileComparator createComparator(Order... orders) {
        return new FileComparator(Arrays.asList(orders));
    }
}