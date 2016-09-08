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

package com.EricSun.EricWidget.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {

	/**
	 * 判断有无网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context) {
		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo workinfo = con.getActiveNetworkInfo();
		if (workinfo == null || !workinfo.isAvailable()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断当前是否有可用的网络以及网络类型
	 * 
	 * @param context
	 * @return 0：无网络 1：WIFI 2：CMWAP 3：CMNET
	 */
	public static int isNetworkAvailable(Context context) {
		int res = 0;// 默认无网络
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (null != connectivity) {

			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info == null) {
				return res;
			}

			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					NetworkInfo netWorkInfo = info[i];
					if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
						res = 1;
						break;
					} else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
						String extraInfo = netWorkInfo.getExtraInfo();
						if ("cmwap".equalsIgnoreCase(extraInfo)
								|| "cmwap:gsm".equalsIgnoreCase(extraInfo)) {
							res = 2;
							break;
						} else {
							res = 3;
							break;
						}
					}
				}
			}

		}
		return res;
	}

}
