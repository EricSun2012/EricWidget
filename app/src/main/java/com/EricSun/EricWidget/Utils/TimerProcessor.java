package com.EricSun.EricWidget.Utils;

import android.widget.RelativeLayout;

public abstract class TimerProcessor {
	private int serialNumber;
	public RelativeLayout view;
	public abstract void process();
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
}