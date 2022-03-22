
package com.example.netstate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 网络管理者
 * 需要在Activty中初始化，在结束时需要销毁
 * 创建人：v_wangzhifeng01
 * 创建时间：2019-07-10
 */
public final class NetManager {
    private static final String TAG = "NetManager";
    private static NetManager mNetManager;
    private static NetStateChangeReceiver netStateChangeReceiver;
    private static Context mContext;

    // 单例模式
    private NetManager() {

    }

    public static NetManager getInstance() {
        if (mNetManager == null) {
            synchronized (NetManager.class) {
                if (mNetManager == null) {
                    mNetManager = new NetManager();
                }
            }
        }
        return mNetManager;
    }

    public void init(Context context) {
        mContext = context;
    }

    /**
     * 实时监听网络状态，需要销毁
     */

    public void monitorNet(NetCallback netCallback) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        netStateChangeReceiver = new NetStateChangeReceiver(netCallback);
        mContext.registerReceiver(netStateChangeReceiver, filter);
    }

    public void onDestroy() {
        mContext.unregisterReceiver(netStateChangeReceiver);
        mContext = null;
    }

    /**
     * 判断网络状态
     */
    public String checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        // 传输数据
        if (activeInfo == null || !activeInfo.isConnected()) {
            Log.e(TAG, "无网络");
            return NetConstant.NETWORK_NO;
        }
        Log.e(TAG, activeInfo.getType() + "");
        if (activeInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            Log.e(TAG, "WIFI网络");
            return NetConstant.WIFI;
        }
        if (activeInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            Log.e(TAG, "移动网络");
            return getNetWorkClass();
        }
        return NetConstant.NETWORK_UNKNOWN;
    }

    /**
     * 判断网络是2G、3G、4G
     * 测试2G网络是，会返回网络，此时网络可能连接不良
     * 3G、4G没有问题
     */
    public String getNetWorkClass() {
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
                Log.e(TAG, NetConstant.NETWORK_2G);
                return NetConstant.NETWORK_2G;
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                Log.e(TAG, NetConstant.NETWORK_3G);
                return NetConstant.NETWORK_3G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                Log.e(TAG, NetConstant.NETWORK_4G);
                return NetConstant.NETWORK_4G;
            default:
                Log.e(TAG, NetConstant.NETWORK_UNKNOWN);
                return NetConstant.NETWORK_UNKNOWN;
        }
    }

    public boolean ping() {
        String result = null;
        try {
            // ping 的地址，可以换成任何一种可靠的外网
            String ip = "www.baidu.com";
            // ping网址3次
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            Log.d("------ping-----", "result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            Log.d("----result---", "result = " + result);
        }
        return false;
    }

}
