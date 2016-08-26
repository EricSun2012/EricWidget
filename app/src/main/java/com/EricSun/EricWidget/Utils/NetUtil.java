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
