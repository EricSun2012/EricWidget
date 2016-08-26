package com.EricSun.EricWidget.Utils;


import android.app.Dialog;
import android.content.Context;

import com.EricSun.EricWidget.Widget.CustomProgressDialog;

/**
 * 瀵硅瘽妗嗗垱寤哄伐鍏风被
 * 
 */
public class DialogUtil {


	/**
	 * 鍒涘缓杩涘害瀵硅瘽妗�
	 * @param context
	 * @param content
	 * @return
	 */
	public static Dialog createProgressDialog(Context context, String content){
		return new CustomProgressDialog(context, content);
	}
	
}
