package com.example.utils;

import android.util.Log;

import com.example.java.BuildConfig;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * LogUtils  日志工具类
 * 默认debug的会输出全部log，release只会输出e级log
 * 1.支持开关log，默认apk是debug打开日志，release关闭日志
 * 2.提供两种Log打印，一种不需要输入TAG值，有默认的TAG。另一种可以传入自定义TAG的值
 *
 *
 * TODO 1.完成获取Log日志文件
 *      2.打印详细信息
 *
 *
 *
 */

public class LogUtils {
    // TAG值
    private static String TAG = "TAG";
    // deBug开关
    private static boolean mIsDebug = BuildConfig.DEBUG;

    private static final InputStream is = null;

    /**
     * 没有测试过的方法
     * 不建议使用，没有完成
     */
    @Deprecated
    public static void getLogMessages(String type) {
        // 第一个是Logcat ，也就是我们想要获取的log日志
        // 第二个是 -s 也就是表示过滤的意思
        // 第三个就是 我们要过滤的类型 W表示warm ，我们也可以换成 D ：debug， I：info，E：error等等
        String[] running = new String[]{"logcat", "-s", "adb logcat *: E"};
        try {
            Process exec = Runtime.getRuntime().exec(running);
            final InputStream is = exec.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread() {
            @Override
            public void run() {
                FileOutputStream os = null;
                try {
                    //新建一个路径信息
                    os = new FileOutputStream("/sdcard/Log/Log.txt");
                    int len = 0;
                    byte[] buf = new byte[1024];
                    while (-1 != (len = is.read(buf))) {
                        os.write(buf, 0, len);
                        os.flush();
                    }
                } catch (Exception e) {
                    Log.d("writelog",
                            "read logcat process failed. message: "
                                    + e.getMessage());
                } finally {
                    if (null != os) {
                        try {
                            os.close();
                        } catch (IOException e) {
                           e.fillInStackTrace();
                        }
                    }
                }
            }
        }.start();
    }


    /**
     * @param isDebug 控制log开关，true打开Log，false关闭Log
     */
    public static void setDebug(boolean isDebug) {
        mIsDebug = isDebug;
    }

    /**
     * @param TAG 根据输入的TAG来改变TAG值
     */
    public static void setTAG(String TAG) {
        LogUtils.TAG = TAG;
    }

    /**
     * 返回当前的TAG值
     */
    public static String getTAG() {
        return TAG;
    }

    public static void v(String tag, String message) {
        if (mIsDebug) {
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (mIsDebug) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (mIsDebug) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (mIsDebug) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
            Log.e(tag, message);
    }

    public static void v(String message) {
        if (mIsDebug) {
            Log.v(TAG, message);
        }
    }

    public static void d(String message) {
        if (mIsDebug) {
            Log.d(TAG, message);
        }
    }

    public static void i(String message) {
        if (mIsDebug) {
            Log.i(TAG, message);
        }
    }

    public static void w(String message) {
        if (mIsDebug) {
            Log.w(TAG, message);
        }
    }

    public static void e(String message) {
            Log.e(TAG, message);
    }

}
