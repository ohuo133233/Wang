package com.example.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

public class SDCardUtils {

    private static File[] mFiles;
    private static final String TAG = SDCardUtils.class.getSimpleName();

    /**
     * 返回外部储存的读写状态
     *
     * @return true：可用；false：不可用
     */

    public static boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 返回所有外部储存的地址
     *
     * @return mFiles 外部储存的
     */
    public static File[] getExternalFilesDirs(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mFiles = context.getExternalFilesDirs(Environment.MEDIA_MOUNTED);
            for (File file : mFiles) {
                LogUtils.d(TAG, "外部储存地:" + file.getAbsolutePath());
            }
        }
        return mFiles;
    }

}
