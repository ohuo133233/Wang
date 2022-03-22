
package com.example.netstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * 网络监听
 * 创建人：v_wangzhifeng01
 * 创建时间：2019-07-10
 */
public class NetStateChangeReceiver extends BroadcastReceiver {
    public static final String TAG = "NetStateChangeReceiver";
    private NetCallback mNetCallback;

    public NetStateChangeReceiver(NetCallback netCallback) {
        mNetCallback = netCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mNetCallback == null) {
            return;
        }
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            String wifiState = isWifiState(intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0));
            if (NetConstant.WIFI_OPEN.equals(wifiState)) {
                mNetCallback.wifi(wifiState);
                return;
            }
            if (NetConstant.WIFI_CLOSE.equals(wifiState)) {
                mNetCallback.noWifi(wifiState);
                return;
            }

        }
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            String netWorkState = isNetWorkState();
            if (NetConstant.NETWORK_2G.equals(netWorkState) || NetConstant.NETWORK_3G.equals(netWorkState)
                    || NetConstant.NETWORK_4G.equals(netWorkState)) {
                mNetCallback.mobile(netWorkState);
            } else {
                mNetCallback.noNet();
            }
        }

    }

    private String isWifiState(int wifiState) {
        Log.e(TAG, "wifiState:" + wifiState);
        switch (wifiState) {
            case WifiManager.WIFI_STATE_DISABLING:
                // WiFi状态禁用
                return NetConstant.WIFI_CLOSE;
            case WifiManager.WIFI_STATE_DISABLED:
                // WiFi状态已禁用
                return NetConstant.WIFI_CLOSE;
            case WifiManager.WIFI_STATE_ENABLING:
                //WiFi状态启用
                return NetConstant.WIFI_OPEN;
            case WifiManager.WIFI_STATE_ENABLED:
                // WiFi状态已启用
                return NetConstant.WIFI_OPEN;
            default:
                return NetConstant.NETWORK_UNKNOWN;
        }

    }

    public String isNetWorkState() {
        return NetManager.getInstance().getNetWorkClass();
    }
}
