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
