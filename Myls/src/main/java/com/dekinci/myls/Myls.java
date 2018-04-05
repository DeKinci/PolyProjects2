package com.dekinci.myls;

import com.dekinci.myls.application.PathContent;
import com.dekinci.myls.cui.view.HumanBuilder;
import com.dekinci.myls.cui.view.PathListPrinter;
import com.dekinci.myls.cui.view.RoboBuilder;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class Myls {
    private List<Path> content;

    Myls(Path path) {
        content = PathContent.get(path);
    }

    public void ls(Comparator<Path> comparator, boolean humable, boolean detailed) {
        content.sort(comparator);
        PathListPrinter.print(content, humable ? new HumanBuilder() : new RoboBuilder(), detailed);
    }
}