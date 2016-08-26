package com.EricSun.EricWidget.Utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * @fileName FileUtils.java
 * @package com.immomo.momo.android.util
 * @description 鏂囦欢宸ュ叿绫�
 * @author 浠讳笢鍗�
 * @email 86930007@qq.com
 * @version 1.0
 */
public class FileUtils {
	/**
	 * 鍒ゆ柇SD鏄惁鍙互
	 * 
	 * @return
	 */
	public static boolean isSdcardExist() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * 鍒涘缓鏍圭洰褰�
	 * 
	 * @param path
	 *            鐩綍璺緞
	 */
	public static void createDirFile(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	/**
	 * 鍒涘缓鏂囦欢
	 * 
	 * @param path
	 *            鏂囦欢璺緞
	 * @return 鍒涘缓鐨勬枃浠�
	 */
	public static File createNewFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				return null;
			}
		}
		return file;
	}

	/**
	 * 鍒犻櫎鏂囦欢澶�
	 * 
	 * @param folderPath
	 *            鏂囦欢澶圭殑璺緞
	 */
	public static void delFolder(String folderPath) {
		delAllFile(folderPath);
		String filePath = folderPath;
		filePath = filePath.toString();
		File myFilePath = new File(filePath);
		myFilePath.delete();
	}

	/**
	 * 鍒犻櫎鏂囦欢
	 * 
	 * @param path
	 *            鏂囦欢鐨勮矾寰�
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
			}
		}
	}

	/**
	 * bitmap转inputstream
	 * 
	 * @param bm
	 * @param compraseLevel
	 * @return
	 */
	public static InputStream Bitmap2InputStream(Bitmap bm, int compraseLevel) {
		if (null == bm){
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, compraseLevel, baos);
		InputStream sbs = new ByteArrayInputStream(baos.toByteArray());
		return sbs;
	}

	/**
	 * 鑾峰彇鏂囦欢鐨刄ri
	 * 
	 * @param path
	 *            鏂囦欢鐨勮矾寰�
	 * @return
	 */
	public static Uri getUriFromFile(String path) {
		File file = new File(path);
		return Uri.fromFile(file);
	}

	/**
	 * 鎹㈢畻鏂囦欢澶у皬
	 * 
	 * @param size
	 * @return
	 */
	public static String formatFileSize(long size) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "鏈煡澶у皬";
		if (size < 1024) {
			fileSizeString = df.format((double) size) + "B";
		} else if (size < 1048576) {
			fileSizeString = df.format((double) size / 1024) + "K";
		} else if (size < 1073741824) {
			fileSizeString = df.format((double) size / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) size / 1073741824) + "G";
		}
		return fileSizeString;
	}
}
