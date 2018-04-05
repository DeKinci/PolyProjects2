package com.dekinci.myls.cui;

import com.dekinci.myls.sorting.Sorting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ArgsTest {
    private String path;
    private String outPath;

    private Path arg;
    private Path out;

    @BeforeEach
    void prepare() throws IOException {
        arg = Files.createTempFile("in", null).toAbsolutePath();
        path = arg.toString();

        out = Files.createTempFile("out", null).toAbsolutePath();
        outPath = out.toString();
    }

    @AfterEach
    void clean() throws IOException {
        Files.delete(arg);
        Files.delete(out);
    }

    @Test
    void testOk() {
        String argStr[] = {path, "-l", "-o", outPath};
        Args args = Args.parse(argStr);

        assertEquals(path, args.getPath().toString());
        assertTrue(args.isDetailed());
        assertFalse(args.isHumanable());
        assertEquals(Sorting.Order.straight(Sorting.Attribute.NAME), args.getSortingOrder().get(0));

        args.getOutput().close();
    }

    @Test
    void testSortingOk() {
        Args args = Args.parse(path, "-s", "n");
        Sorting.Order order = Sorting.Order.reversed(Sorting.Attribute.NAME);
        Sorting.Order argsOrder = args.getSortingOrder().get(0);
        assertEquals(order, argsOrder);
    }

    @Test
    void testSortingBad() {
        assertThrows(IllegalArgumentException.class, () -> Args.parse(path, "-s", "nN"));
        assertThrows(IllegalArgumentException.class, () -> Args.parse(path, "-s", ""));
        assertThrows(IllegalArgumentException.class, () -> Args.parse(path, "-s", "kek"));
    }

    @Test
    void testUnfilledFlag() {
        String argStr[] = {path, "-l", "-o"};
        assertThrows(IllegalArgumentException.class, () -> Args.parse(argStr));
    }

    @Test
    void testNonexistentFlag() {
        String argStr[] = {path, "-noSuchFlag"};
        assertThrows(IllegalArgumentException.class, () -> Args.parse(argStr));
    }

    @Test
    void testEmptyArgs() {
        assertThrows(IllegalArgumentException.class, Args::parse);
    }
}