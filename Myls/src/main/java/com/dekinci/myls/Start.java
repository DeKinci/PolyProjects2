package com.dekinci.myls;

import com.dekinci.myls.cui.Args;
import com.dekinci.myls.cui.view.HumanBuilder;
import com.dekinci.myls.cui.view.RoboBuilder;
import com.dekinci.myls.sorting.FileComparator;

public class Start {
    public static void main(String[] strArgs) {
        try {
            var args = Args.parse(strArgs);
            System.setOut(args.getOutput());
            var myls = new Myls(args.getPath());
            myls.ls(new FileComparator(args.getSortingOrder()),
                    args.isHumanable() ? new HumanBuilder() : new RoboBuilder(),
                    args.isDetailed());
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
