
package com.example.netstate;

/**
 * 网络状态发生改变回调
 * 创建人：v_wangzhifeng01
 * 创建时间：2019-07-10
 */
public  interface NetCallback {

    void wifi(String wifiState);

    void noWifi(String wifiState);

    void mobile(String netWorkState);

    void noNet();
}
