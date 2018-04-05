package com.dekinci.myls;

import com.dekinci.myls.cui.Args;
import com.dekinci.myls.sorting.FileComparator;

public class Start {
    public static void main(String[] strArgs) {
        //Thread.setDefaultUncaughtExceptionHandler((t, e) -> System.err.println(e.getMessage()));
        Args args = Args.parse(strArgs);
        Myls myls = new Myls(args.getPath());
        myls.ls(new FileComparator(args.getSortingOrder()), args.isHumanable(), args.isDetailed());
    }
}
