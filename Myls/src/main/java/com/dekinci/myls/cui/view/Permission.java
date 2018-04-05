package com.dekinci.myls.cui.view;

import java.nio.file.attribute.PosixFilePermission;

public enum Permission {
    OWNER_READ("r", 1),
    OWNER_WRITE("w", 2),
    OWNER_EXECUTE("x", 4),

    GROUP_READ("r", 1),
    GROUP_WRITE("w", 2),
    GROUP_EXECUTE("x", 4),

    OTHERS_READ("r", 1),
    OTHERS_WRITE("w", 2),
    OTHERS_EXECUTE("x", 4);

    public static Permission fromPosix(PosixFilePermission posixFilePermission) {
        switch (posixFilePermission) {
            case OWNER_READ:
                return OWNER_READ;
            case OWNER_WRITE:
                return OWNER_WRITE;
            case OWNER_EXECUTE:
                return OWNER_EXECUTE;

            case GROUP_READ:
                return GROUP_READ;
            case GROUP_WRITE:
                return GROUP_WRITE;
            case GROUP_EXECUTE:
                return GROUP_EXECUTE;

            case OTHERS_READ:
                return OTHERS_READ;
            case OTHERS_WRITE:
                return OTHERS_WRITE;
            case OTHERS_EXECUTE:
                return OTHERS_EXECUTE;

            default:
                throw new RuntimeException("Unexpected permission enum");
        }
    }

    private String letter;
    private int mask;

    Permission(String letter, int mask) {
        this.letter = letter;
        this.mask = mask;
    }

    public String getLetter() {
        return letter;
    }

    public int getMask() {
        return mask;
    }
}
