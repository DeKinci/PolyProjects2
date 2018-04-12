package com.dekinci.myls.cui.view;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.HashSet;

public class RoboBuilder implements InfoBuilder {
    @Override
    public String permissions(PosixFileAttributes attributes) {
        if (attributes == null)
            return "unsupported_OS";

        var permissions = new HashSet<Permission>();
        attributes.permissions().forEach((p) -> permissions.add(Permission.fromPosix(p)));

        var builder = new StringBuilder();
        builder.append(attributes.isDirectory() ? "d" : "-");

        int owner = 0, group = 0, other = 0;
        var values = Permission.values();

        for (int i = 0; i < 3; i++)
            owner += permissions.contains(values[i]) ? values[i].getMask() : 0;
        for (int i = 3; i < 6; i++)
            group += permissions.contains(values[i]) ? values[i].getMask() : 0;
        for (int i = 6; i < 9; i++)
            other += permissions.contains(values[i]) ? values[i].getMask() : 0;

        builder.append(owner != 0 ? owner : "-");
        builder.append(group != 0 ? group : "-");
        builder.append(other != 0 ? other : "-");

        return builder.toString();
    }

    @Override
    public String size(BasicFileAttributes attributes) {
        return attributes.size() + "";
    }

    @Override
    public String date(BasicFileAttributes attributes) {
        return attributes.lastModifiedTime().toString();
    }

    @Override
    public String name(Path path) {
        return path.getFileName().toString();
    }
}
