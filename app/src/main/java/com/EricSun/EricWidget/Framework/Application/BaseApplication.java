/*
 * MIT License
 *
 * Copyright (c) 2016 Eric
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.EricSun.EricWidget.Framework.Application;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.EricSun.EricWidget.Framework.Network.NetWorkState;
import com.EricSun.EricWidget.Utils.NetUtil;
import com.zhy.m.permission.MPermissions;

import java.util.Observable;
import java.util.Observer;


public class BaseApplication extends Application {
    // 全局变量

    private String source = "mobile";
    private String system;
    private int width;
    private int height;
    private int code;
    private String version;
    private String deviceId;
    private String channel;

    private ConnectionChangeReceiver myReceiver;
    public NetWorkState mState;
    private Observable mObservable = new Observable();

    @Override
    public void onCreate() {
        super.onCreate();

        mState = NetUtil.hasNetwork(getApplicationContext()) ? NetWorkState.NetWorkStateWiFi : NetWorkState.NetWorkStateNone;
//        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageManager pm = getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = pm.getPackageInfo(getPackageName(), 0);
            version = packInfo.versionName;
            code = packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }


        ApplicationInfo info;
        try {
            info = pm.getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA);
            channel = "Android";
            if (null != info && null != info.metaData) {
                channel += "&" + info.metaData.getString("JPUSH_CHANNEL");
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        system = android.os.Build.MODEL + ","
                + android.os.Build.VERSION.SDK_INT + ","
                + android.os.Build.VERSION.RELEASE;

        DisplayMetrics dm = getResources().getDisplayMetrics();
        width = (int) (dm.widthPixels * dm.density + 0.5f); // 屏幕宽（px，如：480px）
        height = (int) (dm.heightPixels * dm.density + 0.5f); // 屏幕高（px，如：800px）

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver = new ConnectionChangeReceiver();
        registerReceiver(myReceiver, filter);
    }


    public class ConnectionChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = null;

            mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (null == mobNetInfo && null == wifiNetInfo)
                return;

            if ((mobNetInfo != null && !mobNetInfo.isConnected()) && (wifiNetInfo != null && !wifiNetInfo.isConnected())) {
                //改变背景或者 处理网络的全局变量  无网络情况
                mState = NetWorkState.NetWorkStateNone;
            } else if (mobNetInfo != null && mobNetInfo.isConnected()) {
                //改变背景或者 处理网络的全局变量  有网络情况
                mState = NetWorkState.NetWorkStateWWAN;
            } else if (wifiNetInfo != null && wifiNetInfo.isConnected()) {
                mState = NetWorkState.NetWorkStateWiFi;
            }
        }
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    /**
     * Add observer
     *
     * @param observer
     */
    public void addObserver(Observer observer) {
        mObservable.addObserver(observer);
    }


    /**
     * Delete observer
     *
     * @param observer
     */

    public void removeObserver(Observer observer) {
        mObservable.deleteObserver(observer);
    }

    public void removeAllOberver() {
        mObservable.deleteObservers();
    }

}
