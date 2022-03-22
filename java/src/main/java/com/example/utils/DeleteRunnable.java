package com.example.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;

public class DeleteRunnable implements Runnable {
    private String mRegEx;
    private String dirPath;
    private static final String TAG = DeleteRunnable.class.getSimpleName();

    /**
     * Constructor
     *
     * @param dirPath  要删除文件所在的目录路径
     * @param mRegEx   规则
     */
    public DeleteRunnable(String dirPath,  String mRegEx) {
        this.mRegEx = mRegEx;
        this.dirPath = dirPath;
    }

    @Override
    public void run() {
        deleteFilesWithPrefix();
    }

    /**
     * 枚举并删除所有符合条件(前缀)的文件
     */
    private void deleteFilesWithPrefix() {
        if (!TextUtils.isEmpty(dirPath)) {
            File adDir = new File(dirPath);
            if (adDir.exists() && adDir.isDirectory()) {
                if (!TextUtils.isEmpty(mRegEx)) {
                    DeleteFileFilter filter = new DeleteFileFilter( mRegEx);
                    // 2.匹配是否是需要删除的文件
                    File[] fileList = adDir.listFiles(filter);
                    if (fileList != null && fileList.length > 0) {
                        for (File file : fileList) {
                            if (file.isFile() && file.exists()) {
                                boolean delete = file.delete();
                                Log.i(TAG, "删除符合条件" + (delete ? "成功~" : "失败！"));
                            }
                        }
                    }
                }
            }

        }
    }
}
