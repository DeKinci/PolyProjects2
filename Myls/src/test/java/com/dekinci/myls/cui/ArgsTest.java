package com.dekinci.myls.cui;

import com.dekinci.myls.sorting.Sorting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ArgsTest {
    private String path;
    private String outPath;

    private Path arg;
    private Path out;

    private PrintStream stream = mock(PrintStream.class);

    @BeforeEach
    void prepare() throws IOException {
        arg = Files.createTempFile("in", null).toAbsolutePath();
        path = arg.toString();

        out = Files.createTempFile("out", null).toAbsolutePath();
        outPath = out.toString();

        System.setOut(stream);
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
        verify(stream, times(3)).println(contains("Usage:"));
    }

    @Test
    void testUnfilledFlag() {
        String argStr[] = {path, "-l", "-o"};
        assertThrows(IllegalArgumentException.class, () -> Args.parse(argStr));
        verify(stream).println(contains("Usage:"));
    }

    @Test
    void testNonexistentFlag() {
        String argStr[] = {path, "-noSuchFlag"};
        assertThrows(IllegalArgumentException.class, () -> Args.parse(argStr));
        verify(stream).println(contains("Usage:"));
    }

    @Test
    void testEmptyArgs() {
        assertThrows(IllegalArgumentException.class, Args::parse);
        verify(stream).println(contains("Usage:"));
    }
}