package com.example.utils;


import java.io.File;
import java.io.FilenameFilter;

public class DeleteFileFilter implements FilenameFilter {
    // 前缀或后缀规则
    private String mRegEx;

    public DeleteFileFilter(  String regEx) {
        this.mRegEx = regEx;
    }

    @Override
    public boolean accept(File file, String s) {
        return  s.endsWith(mRegEx);
    }

}
