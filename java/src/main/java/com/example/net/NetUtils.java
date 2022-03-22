package com.example.net;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {
    private static volatile NetUtils mNetUtils;
    private NetWorkStateReceiver mNetWorkStateReceiver;

    private NetUtils() {

    }

    public static NetUtils getInstance() {
        if (mNetUtils == null) {
            synchronized (NetUtils.class) {
                if (mNetUtils == null) {
                    mNetUtils = new NetUtils();
                }
            }
        }
        return mNetUtils;
    }

    // 判断网络是否连接
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    // 监听网络连接状态
    public void registerNetWorkState(Context context, INetworkStateChanges iNetworkStateChanges) {
        if (mNetWorkStateReceiver == null) {
            mNetWorkStateReceiver = new NetWorkStateReceiver(iNetworkStateChanges);
        }
        IntentFilter filter = new IntentFilter();

        // 适配Android8.0以上
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(mNetWorkStateReceiver, filter);
    }

    // 销毁网络监听
    public void destroy(Context context) {
        if (mNetWorkStateReceiver != null) {
            context.unregisterReceiver(mNetWorkStateReceiver);
            mNetWorkStateReceiver = null;
        }
    }


}
