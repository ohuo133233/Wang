package com.example.net;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import com.example.utils.LogUtils;


/**
 * Created by Carson_Ho on 16/10/31.
 */
public class NetWorkStateReceiver extends BroadcastReceiver {

    private INetworkStateChanges mINetworkStateChanges;

    public NetWorkStateReceiver(INetworkStateChanges iNetworkStateChanges) {
        mINetworkStateChanges = iNetworkStateChanges;
    }

    public void onReceive(Context context, Intent intent) {
        LogUtils.d("网络状态发生变化");
        ConnectivityManager connMgr;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (!wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                LogUtils.d("wifi 和 移动数据 均未连接");
                mINetworkStateChanges.onNetDisconnect();
            } else {
                LogUtils.d("wifi 或 移动数据 已连接");
                mINetworkStateChanges.onNetConnect();
            }
        } else {
            connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Network[] networks = connMgr.getAllNetworks();
            boolean hasConnected = false;
            for (Network network : networks) {
                NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
                if (networkInfo.isConnected()) {
                    LogUtils.d("wifi 或 移动数据 已连接");
                    hasConnected = true;
                    mINetworkStateChanges.onNetConnect();
                }
            }
            if (!hasConnected) {
                LogUtils.d("wifi 和 移动数据 均未连接");
            }
        }
    }
}



