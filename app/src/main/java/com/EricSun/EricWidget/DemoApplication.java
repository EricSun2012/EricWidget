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

package com.EricSun.EricWidget;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.lidroid.xutils.util.LogUtils;
import com.EricSun.EricWidget.Framework.Application.BaseApplication;
import com.EricSun.EricWidget.Framework.Network.MySSLSocketFactory;


import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;

import javax.net.ssl.SSLSocketFactory;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;
import cn.finalteam.okhttpfinal.https.SkirtHttpsHostnameVerifier;
import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Eric on 16/8/25.
 */
public class DemoApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();


        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        builder.setFollowSslRedirects(true);
        builder.setRetryOnConnectionFailure(true);
        builder.setTimeout(22000);
        builder.setSSLSocketFactory(createSocketFactory());
        builder.setHostnameVerifier(new SkirtHttpsHostnameVerifier());
        builder.setNetworkInterceptors(createNetworkInterceptor());
        Headers.Builder headerBuilder = new Headers.Builder();
        createCommonHeader(headerBuilder);
        builder.setCommenHeaders(headerBuilder.build());
        Cache mCache = new Cache(getApplicationContext().getCacheDir(), 20 * 1024 * 1024);
        builder.setCache(mCache);

        OkHttpFinal.getInstance().init(builder.build());

    }

    public String addChannelId() {
        ApplicationInfo appInfo = null;
        try {
            appInfo = getApplicationContext().getPackageManager()
                    .getApplicationInfo(getApplicationContext().getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (null != appInfo && null != appInfo.metaData) {
            return "" + appInfo.metaData.get("UMENG_CHANNEL");
        }
        return "";
    }


    private void createCommonHeader(Headers.Builder headerBuilder) {
        headerBuilder.add("platform", "android");
        headerBuilder.add("appVersion", BuildConfig.VERSION_NAME);
        headerBuilder.add("Accept", "*/*");
        headerBuilder.add("Content-Type", "application/json;charset=UTF-8");
        headerBuilder.add("x-channelId", addChannelId());
    }

    private ArrayList<Interceptor> createNetworkInterceptor() {
        ArrayList<Interceptor> interceptorList = new ArrayList<>();
        interceptorList.add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                if (null != response) {
                    int errorCode = response.code();
                    if (errorCode >= 200 && errorCode < 300) {//OK
                        //add your code or nothing
                    } else if (errorCode == 401) {//unauthenticated
                        LogUtils.d("RestClient send UNAUTHEN");
                        Intent intent = new Intent(Configure.UNAUTHEN);
                        getApplicationContext().sendBroadcast(intent);

                    } else if (errorCode >= 400 && errorCode < 500) {//CLIENT error

                    } else if (errorCode >= 500 && errorCode < 600) {//server error

                    } else {//Unexpected response 未知错误
                    }
                }
                return response;
            }
        });

        return interceptorList;
    }


    private SSLSocketFactory createSocketFactory() {
        SSLSocketFactory sf = null;
        try {
            MySSLSocketFactory sslFy = new MySSLSocketFactory(null);
            sf = sslFy.sslContext.getSocketFactory();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }

        return sf;
    }
}
