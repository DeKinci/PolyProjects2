package com.dekinci.myls.cui.view;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;

public interface InfoBuilder {
    String permissions(PosixFileAttributes attributes);
    String size(BasicFileAttributes attributes);
    String date(BasicFileAttributes attributes);
    String name(Path path);
}
