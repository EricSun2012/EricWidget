/**
 * @ProjectName: yijinrong
 * @FileName: Tools.java
 * @PackageName: com.yl.util
 * @Date: 2014骞�0鏈�1鏃ヤ笅鍗�:20:54
 * @Copyright (c) 2014, JieTang@gtriches.com All Rights Reserved.
 * @version V1.0  
 */

package com.EricSun.EricWidget.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * @ClassName: Tools
 * @Function: TODO ADD FUNCTION
 * @Reason: TODO ADD REASON
 * @Date: 2014骞�0鏈�1鏃�涓嬪崍7:20:54
 * @author Administrator
 * @version V1.0
 * @since JDK 1.8
 */
public class Tools {

	/**
	 * 鑾峰緱褰撳墠app鐗堟湰鍙�
	 * 
	 * @Title: getAppVersionName
	 * @Description: TODO
	 * @param @param context
	 * @param @return
	 * @return String
	 * @throws
	 * @author Administrator
	 * @since JDK 1.8
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "1.0.0";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

	/**
	 * 鑾峰彇鐗堟湰搴忓垪鍙�
	 * 
	 * @param context
	 * @return int
	 */
	public static int getAppVersion(Context context) {
		int version = 0;
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			version = pi.versionCode;
			if (version <= 0) {
				return 1;
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return version;
	}

	/**
	 * String杞琲nt
	 * 
	 * @Title: convertInteger
	 * @Description: TODO
	 * @param @param strs
	 * @param @return
	 * @return int
	 * @throws
	 * @author Administrator
	 * @since JDK 1.8
	 */
	public static int convertInteger(String strs) {
		return Integer.parseInt(strs);
	}

	/**
	 * int杞琒tring
	 * 
	 * @Title: convertString
	 * @Description: TODO
	 * @param @param ints
	 * @param @return
	 * @return String
	 * @throws
	 * @author Administrator
	 * @since JDK 1.8
	 */
	public static String convertString(int ints) {
		return Integer.toString(ints);
	}
}
