package com.dekinci.myls.cui;

class Descriptions {
    static final String PROGRAM_NAME = "ls is not low skill";

    static final String PATH = "Specifies the path to the directory or file to show";

    static final String IS_LONG = "Turns output in long mode, " +
            "where rwx rights (in number format), " +
            "last modification date and size (in bytes) are also listed";

    static final String IS_HUMANABLE = "Turns output in comfortable mode " +
            "(KB, MB, GB file size, rwx rights, ellipses for long names).";

    static final String SORT = "Sorts files in specified order. Lowercase letter - descending, uppercase - ascending\n" +
            "\t  Available sort types: \n" +
            "\t\t  By folder: F / f \n" +
            "\t\t  By name:\t N / n \n" +
            "\t\t  By size:\t S / s \n" +
            "\t\t  By last mod date: D / d \n" +
            "\t  You can specify different sort types in a row, first one will be applied first. \n" +
            "\t  Example: \"-s sND\" will give a list of files sorted from smaller size to bigger, " +
            "where files of equal sizes sorted by name alphabetically, and equal size and name " +
            "files (maybe some magical sort of them, but anyway) will be sorted by modification " +
            "date from newer to older";

    static final String OUTPUT = "Specifies output stream, console is default.";

    static final String HELP = "Prints help manual";
}
