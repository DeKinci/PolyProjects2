package com.dekinci.myls.cui.view;

import com.dekinci.myls.application.AttributeManager;

import java.nio.file.Path;
import java.util.List;

public class PathListPrinter {
    public static void print(List<Path> list, InfoBuilder builder, boolean detailed) {
        var manager = AttributeManager.getManager();
        System.out.println("total " + list.size());
        for (Path path : list) {
            var stringBuilder = new StringBuilder();

            if (detailed) {
                var attributes = manager.get(path);
                stringBuilder.append(builder.permissions(manager.getPosix(path))).append(" ")
                        .append(!attributes.isDirectory() ? builder.size(attributes) : "folder").append(" ")
                        .append(builder.date(attributes)).append(" ");
            }

            stringBuilder.append(builder.name(path));
            System.out.println(stringBuilder.toString());
        }
    }
}
