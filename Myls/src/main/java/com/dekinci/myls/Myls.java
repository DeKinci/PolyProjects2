package com.dekinci.myls;

import com.dekinci.myls.application.PathContent;
import com.dekinci.myls.cui.view.InfoBuilder;
import com.dekinci.myls.cui.view.PathListPrinter;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

class Myls {
    private List<Path> content;

    Myls(Path path) {
        content = PathContent.get(path);
    }

    void ls(Comparator<Path> comparator, InfoBuilder infoBuilder, boolean detailed) {
        content.sort(comparator);
        PathListPrinter.print(content, infoBuilder, detailed);
    }
}