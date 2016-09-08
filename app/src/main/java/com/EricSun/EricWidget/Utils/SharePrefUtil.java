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
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * SharePreferences工具类
 */
public class SharePrefUtil {
	private static String tag = SharePrefUtil.class.getSimpleName();
	private final static String SP_NAME = "config";
	private static SharedPreferences sp;

	public interface KEY {
		String ACCESSTOKEN = "accessToken";
		String FIRSTLAUNCH = "firstLaunch";
		String LASTUSERID = "lastUserId";
	}

	public interface HELP_PAGE_KEY {

		String HELP_PAGE_HOME = "help_page_home";// NEWS 甯姪鐣岄潰
		String HELP_PAGE_MY = "help_page_my";// SERVICE 甯姪鐣岄潰
		String HELP_PAGE_MORE = "help_page_more";// GOV 甯姪鐣岄潰
	}

	/*
	 * private static String getNewKey(Context context, String oldKey) { if
	 * (oldKey.equals(KEY.LAST_UID)) { return oldKey; } else if
	 * (oldKey.equals(KEY.FIRST_LAUNCH)) { return oldKey; } else if
	 * (oldKey.equals(KEY.CANUPDATE)) { return oldKey; } else if
	 * (oldKey.equals(KEY.DEVICE_TOKEN)) { return oldKey; } return oldKey + "_"
	 * + SharePrefUtil.getLong(context, KEY.LAST_UID, 0); }
	 */

	/**
	 * 淇濆瓨甯冨皵鍊�
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveBoolean(Context context, String key, boolean value) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		// key = getNewKey(context, key);
		sp.edit().putBoolean(key, value).commit();
	}

	/**
	 * 淇濆瓨瀛楃涓�
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveString(Context context, String key, String value) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		// key = getNewKey(context, key);
		sp.edit().putString(key, value).commit();

	}

	public static void clear(Context context) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		sp.edit().clear().commit();
	}

	/**
	 * 淇濆瓨long鍨�
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveLong(Context context, String key, long value) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		// key = getNewKey(context, key);
		sp.edit().putLong(key, value).commit();
	}

	/**
	 * 淇濆瓨int鍨�
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveInt(Context context, String key, int value) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		// key = getNewKey(context, key);
		sp.edit().putInt(key, value).commit();
	}

	/**
	 * 淇濆瓨float鍨�
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveFloat(Context context, String key, float value) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		// key = getNewKey(context, key);
		sp.edit().putFloat(key, value).commit();
	}

	/**
	 * 鑾峰彇瀛楃鍊�
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getString(Context context, String key, String defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		// key = getNewKey(context, key);
		return sp.getString(key, defValue);
	}

	/**
	 * 鑾峰彇int鍊�
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static int getInt(Context context, String key, int defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		// key = getNewKey(context, key);
		return sp.getInt(key, defValue);
	}

	/**
	 * 鑾峰彇long鍊�
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static long getLong(Context context, String key, long defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		// key = getNewKey(context, key);
		return sp.getLong(key, defValue);
	}

	/**
	 * 鑾峰彇float鍊�
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static float getFloat(Context context, String key, float defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		// key = getNewKey(context, key);
		return sp.getFloat(key, defValue);
	}

	/**
	 * 鑾峰彇甯冨皵鍊�
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static boolean getBoolean(Context context, String key,
									 boolean defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		// key = getNewKey(context, key);
		return sp.getBoolean(key, defValue);
	}

	/**
	 * 灏嗗璞¤繘琛宐ase64缂栫爜鍚庝繚瀛樺埌SharePref涓�
	 * 
	 * @param context
	 * @param key
	 * @param object
	 */
	public static void saveObj(Context context, String key, Object object) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		// key = getNewKey(context, key);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			// 灏嗗璞＄殑杞负base64鐮�
			String objBase64 = new String(Base64.encode(baos.toByteArray(),
					Base64.NO_WRAP));

			sp.edit().putString(key, objBase64).commit();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 灏哠harePref涓粡杩嘼ase64缂栫爜鐨勫璞¤鍙栧嚭鏉�
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static Object getObj(Context context, String key) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		// key = getNewKey(context, key);
		String objBase64 = sp.getString(key, null);
		if (TextUtils.isEmpty(objBase64))
			return null;

		// 瀵笲ase64鏍煎紡鐨勫瓧绗︿覆杩涜瑙ｇ爜
		byte[] base64Bytes = Base64
				.decode(objBase64.getBytes(), Base64.NO_WRAP);
		ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);

		ObjectInputStream ois;
		Object obj = null;
		try {
			ois = new ObjectInputStream(bais);
			obj = ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

}
