package com.dekinci.myls.sorting;

import com.dekinci.myls.Utils;
import com.dekinci.myls.application.AttributeManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private AttributeManager cacheManager = AttributeManager.getManager();

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
        paths.forEach(Utils::delete);
    }

    @Test
    void test() {
        FileComparator comparator = createComparator(Order.straight(Attribute.FOLDER), Order.reversed(Attribute.SIZE), Order.straight(Attribute.NAME));
        print();
        paths.sort(comparator);
        print();

        for (int i = 0; i < total; i++) {
            BasicFileAttributes attributesI = cacheManager.get(paths.get(i));
            boolean iIsDir = attributesI.isDirectory();
            long iSize = attributesI.size();

            for (int j = 0; j < total; j++) {
                BasicFileAttributes attributesJ = cacheManager.get(paths.get(j));
                boolean jIsDir = attributesJ.isDirectory();
                long jSize = attributesJ.size();

                if (iIsDir && jIsDir)
                    assertTrue(compareIndexNames(i, j));
                else if (iIsDir)
                    assertTrue(i < j);
                else if (jIsDir)
                    assertTrue(i > j);
                else {
                    if (iSize == jSize)
                        assertTrue(compareIndexNames(i, j));
                    else if (iSize > jSize)
                        assertTrue(i < j);
                    else
                        assertTrue(i > j);
                }
            }
        }
    }

    private void print() {
        paths.forEach(System.out::println);
        System.out.println("********************************************");
    }

    private boolean compareIndexNames(int a, int b) {
        boolean straight = paths.get(a).getFileName().toString()
                .compareTo(paths.get(b).getFileName().toString()) >= 0;
        return b <= a == straight;
    }

    private FileComparator createComparator(Order... orders) {
        return new FileComparator(Arrays.asList(orders));
    }
}