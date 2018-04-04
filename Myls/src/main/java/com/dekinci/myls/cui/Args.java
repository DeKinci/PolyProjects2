package com.dekinci.myls.cui;

import com.beust.jcommander.*;
import com.beust.jcommander.converters.PathConverter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.dekinci.myls.sorting.Sorting.*;

/**
 * Present arguments for the program.
 * Encapsulates all parsing library elements.
 */
public class Args {
    public static Args parse(String... str) {
        Args args = new Args();

        try {
            JCommander.newBuilder()
                    .addObject(args)
                    .build()
                    .parse(str);

            validateArgs(args);
        } catch (ParameterException e) {
            handleParameterException(e);
        }

        return args;
    }

    private static void handleParameterException(ParameterException e) {
        JCommander commander = e.getJCommander();
        if (commander == null)
            commander = JCommander.newBuilder().addObject(new Args()).build();
        commander.setProgramName(Descriptions.PROGRAM_NAME);
        commander.usage();
        throw new IllegalArgumentException(e.getMessage());
    }

    private static void validateArgs(Args args) {
        //Anti-costyil'
        if (args.path.size() > 1)
            die("Wrong arguments");

        if (Files.notExists(args.getPath()))
            die("File is not found!");

        if (args.getSortingOrder().isEmpty())
            die("Sorting order is not specified");

        if (args.needHelp())
            die("Help request");
    }

    private static void die(String lastWords) {
        throw new ParameterException(lastWords);
    }

    private Args() {
    }

    //List of paths is a costyil' to fix JCommander's main parameter converter bug
    @Parameter(description = Descriptions.path, converter = PathConverter.class, required = true)
    private List<Path> path;

    @Parameter(names = {"--long", "-l"}, description = Descriptions.isLong)
    private boolean isLong = false;

    @Parameter(names = {"-h", "--human-readable"}, description = Descriptions.isHumanable)
    private boolean isHumanable = false;

    @Parameter(names = {"-s", "--sort"}, description = Descriptions.sort, listConverter = SortingTypesConverter.class)
    private List<Order> sorts = Collections.singletonList(Order.straight(Attribute.NAME));

    @Parameter(names = {"-o", "--output"}, description = Descriptions.output, converter = OutputStreamConverter.class)
    private PrintStream output = System.out;

    @Parameter(names = "--help", description = Descriptions.help, help = true)
    private boolean help = false;

    private static class OutputStreamConverter implements IStringConverter<PrintStream> {
        @Override
        public PrintStream convert(String s) {
            Path path = Paths.get(s);
            try {
                return new PrintStream(new FileOutputStream(path.toFile()));
            } catch (FileNotFoundException e) {
                System.err.println("Can not access output file!");
                return System.out;
            }
        }
    }

    private static class SortingTypesConverter implements IStringConverter<List<Order>> {
        List<Order> result = new ArrayList<>(3);

        @Override
        public List<Order> convert(String s) {
            char[] sorters = s.trim().toCharArray();

            for (char sort : sorters)
                switch (sort) {
                    case 'N':
                        addIfNotPresent(Order.straight(Attribute.NAME));
                        break;
                    case 'n':
                        addIfNotPresent(Order.reversed(Attribute.NAME));
                        break;

                    case 'S':
                        addIfNotPresent(Order.straight(Attribute.SIZE));
                        break;
                    case 's':
                        addIfNotPresent(Order.reversed(Attribute.SIZE));
                        break;

                    case 'D':
                        addIfNotPresent(Order.straight(Attribute.DATE));
                        break;
                    case 'd':
                        addIfNotPresent(Order.reversed(Attribute.DATE));
                        break;
                    default:
                        die("Wrong sorting attribute");
                }

            return result;
        }

        private void addIfNotPresent(Order order) {
            if (result.contains(order) || result.contains(Order.opposite(order)))
                die("Sorting attributes duplicate");
            result.add(order);
        }
    }

    public Path getPath() {
        return path.get(0);
    }

    public boolean isLong() {
        return isLong;
    }

    public boolean isHumanable() {
        return isHumanable;
    }

    public List<Order> getSortingOrder() {
        return sorts;
    }

    public PrintStream getOutput() {
        return output;
    }

    private boolean needHelp() {
        return help;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Args)) return false;
        Args args = (Args) o;
        return isLong == args.isLong &&
                isHumanable == args.isHumanable &&
                help == args.help &&
                Objects.equals(path, args.path) &&
                Objects.equals(sorts, args.sorts) &&
                Objects.equals(output, args.output);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, isLong, isHumanable, sorts, output, help);
    }
}
