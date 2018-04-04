package com.dekinci.myls.cui;

public class Descriptions {
    public static final String PROGRAM_NAME = "ls is not low skill";

    public static final String path = "Specifies the path to the directory or file to show";

    public static final String isLong = "Turns output in long mode, " +
            "where rwx rights (in number format), " +
            "last modification date and size (in bytes) are also listed";

    public static final String isHumanable = "Turns output in comfortable mode " +
            "(KB, MB, GB file size, rwx rights, ellipses for long names).";

    public static final String sort = "Sorts files in specified order. Lowercase letter - descending, uppercase - ascending\n" +
            "\t  Available sort types: \n" +
            "\t\t  By name;\t N / n \n" +
            "\t\t  By size:\t S / s \n" +
            "\t\t  By last modification date: D / d \n" +
            "\t  You can specify different sort types in a row, first one will be applied first. \n" +
            "\t  Example: \"-s sND\" will give a list of files sorted from smaller size to bigger, " +
            "where files of equal sizes sorted by name alphabetically, and equal size and name " +
            "files (maybe some magical sort of them, but anyway) will be sorted by modification " +
            "date from newer to older";

    public static final String output = "Specifies output stream, console is default.";

    public static final String help = "Prints help manual";
}
