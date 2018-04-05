package com.dekinci.myls;

import com.dekinci.myls.sorting.FileComparator;
import com.dekinci.myls.sorting.Sorting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.dekinci.myls.sorting.Sorting.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.*;

class MylsTest {
    private static final int DIR_AMOUNT = 5;
    private static final int FILES_AMOUNT = 15;

    private Path tempDir;
    private Path testdir;
    private PrintStream out = mock(PrintStream.class);

    private List<Path> files = new ArrayList<>();
    private List<Path> dirs = new ArrayList<>();
    private List<Path> junkFiles = new ArrayList<>();

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("testtempdir");
        testdir = Utils.createDir(tempDir, "testdir");

        Utils.forEach(FILES_AMOUNT, (i) -> files.add(Utils.createFile(testdir, "file_" + i)));
        Utils.forEach(DIR_AMOUNT, (i) -> dirs.add(Utils.createDir(testdir, "dir_" + i)));
        dirs.forEach((p) -> junkFiles.add(Utils.createFile(p, "other_file")));

        System.setOut(out);
    }

    @AfterEach
    void tearDown() {
        junkFiles.forEach(Utils::delete);
        dirs.forEach(Utils::delete);
        files.forEach(Utils::delete);
        Utils.delete(testdir);
        Utils.delete(tempDir);
    }

    @Test
    void ls() {
        Myls myls = new Myls(testdir);
        Comparator<Path> comparator = new FileComparator(List.of(Order.straight(Attribute.FOLDER), Order.straight(Attribute.NAME)));

        myls.ls(comparator, false, false);

        verify(out).println(contains("total " + (DIR_AMOUNT + FILES_AMOUNT)));
        verify(out, times(DIR_AMOUNT)).println(contains("dir_"));
        verify(out, times(FILES_AMOUNT)).println(contains("file_"));
    }
}