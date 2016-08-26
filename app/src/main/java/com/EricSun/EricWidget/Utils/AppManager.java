package com.EricSun.EricWidget.Utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

public class AppManager {

	private static Stack<Activity> activityStack;
	private static AppManager instance;

	private AppManager() {
	}

	/**
	 * 鍗曚竴瀹炰緥
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	/**
	 * 娣诲姞Activity鍒板爢锟�
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	public Activity indexOfActivity(int index) {
		if (index < 0 || index >= activityStack.size()) {
			return null;
		}
		Activity activity = activityStack.elementAt(index);
		return activity;
	}

	/**
	 * 鑾峰彇褰撳墠Activity锛堝爢鏍堜腑锟�锟斤拷锟�锟斤拷鍘嬪叆鐨勶級
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 缁撴潫褰撳墠Activity锛堝爢鏍堜腑锟�锟斤拷锟�锟斤拷鍘嬪叆鐨勶級
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 缁撴潫鎸囧畾鐨凙ctivity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 缁撴潫鎸囧畾绫诲悕鐨凙ctivity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 杩斿洖褰撳墠activity鐨勭储寮曞彿
	 */
	public int currentActivityIndex() {
		return activityStack.size() - 1;
	}

	/**
	 * 鍥炲埌绱㈠紩浣嶇疆鐨凙ctivity
	 */
	public void finishActivity(int index) {
		if (index < 0 || index > activityStack.size() - 2)
			return;
		for (int i = activityStack.size() - 1; i >= index; i--) {
			if (null != activityStack.get(i)) {
//				if (activityStack.get(i).getClass() == MainActivity.class) {
//					continue;
//				}
				activityStack.get(i).finish();
			}
		}
	}

	/**
	 * 缁撴潫锟�锟斤拷Activity
	 */
	public void finishAllActivity() {
		for (int i = activityStack.size() - 1; i >= 0; i--) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
	}

	/**
	 * 锟�锟斤拷搴旂敤绋嬪簭
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
		}
	}
}