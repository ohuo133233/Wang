package com.example.utils;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class TimerUtils {

    private static TimerUtils sTimerUtils = null;
    private Handler mHandler = new Handler();

    private TimerUtils() {
    }

    public static TimerUtils getInstance() {
        if (sTimerUtils == null) {
            synchronized (TimerUtils.class) {
                if (sTimerUtils == null) {
                    sTimerUtils = new TimerUtils();
                }
            }
        }
        return sTimerUtils;
    }

    /**
     * 倒计时
     * 在time执行一次runnable
     *
     * @param time     毫秒
     * @param runnable 回调
     */
    public void countDown(long time, Runnable runnable) {
        mHandler.postDelayed(runnable, time);
    }

    /**
     * 定时器
     * 每隔@time毫秒执行一次runnable
     *
     * @param time     毫秒
     * @param runnable 回调
     */
    public void timer(long time, final Runnable runnable) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        };

        timer.schedule(task, time, time);

    }


}
