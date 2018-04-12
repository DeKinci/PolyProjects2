package com.dekinci.myls.cui.view;

import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class HumanBuilder implements InfoBuilder {
    private static final String[] units = new String[]{"B ", "KB", "MB", "GB", "TB"};

    @Override
    public String permissions(PosixFileAttributes attributes) {
        if (attributes == null)
            return "unsupported_OS";

        var permissions = new HashSet<Permission>();
        attributes.permissions().forEach((p) -> permissions.add(Permission.fromPosix(p)));

        var builder = new StringBuilder();
        builder.append(attributes.isDirectory() ? "d" : "-");

        for (Permission permission : Permission.values())
            builder.append(permissions.contains(permission) ? permission.getLetter() : "-");

        return builder.toString();
    }

    @Override
    public String size(BasicFileAttributes attributes) {
        int digitGroups = (int) (Math.log10(attributes.size()) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(attributes.size() / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    @Override
    public String date(BasicFileAttributes attributes) {
        var dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        var date = new Date(attributes.lastModifiedTime().toMillis());
        return dateFormat.format(date);
    }

    @Override
    public String name(Path path) {
        return StringUtils.abbreviate(path.getFileName().toString(), 40);
    }
}
