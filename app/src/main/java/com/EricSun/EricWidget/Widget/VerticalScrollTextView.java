package com.EricSun.EricWidget.Widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

public class VerticalScrollTextView extends TextView {
	private String dataSource; // 数据
	private int delayMillis = 2500; // 2.5s延迟
	private Handler handler = new Handler();
	private Runnable runOperation;

	public VerticalScrollTextView(Context context) {
		this(context, null);
	}

	public VerticalScrollTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.runOperation = new Runnable() {

			@Override
			public void run() {

				int height = getHeight();
				int scrollY = getScrollY();
				int lineHeight = getLineHeight();
				int lineCount = getLineCount();// 总行数
				/**
				 * textView不可见内容的高度，可以理解为偏移位移
				 */
				int maxY = (getLineCount() * getLineHeight() + getPaddingTop() + getPaddingBottom())
						- getHeight();

				System.out.println("=maxY= " + maxY + "");
				System.out.println("=height= " + height + "");
				System.out.println("=lineCount= " + getLineCount() + "");

				double viewCount = Math.floor(height / lineHeight);// 可见区域最大显示多少行
				if (lineCount > viewCount) {// 总行数大于可见区域显示的行数时则滚动

					if (scrollY >= maxY) {
						scrollBy(0, -maxY);
					} else {
						scrollBy(0, lineHeight);
					}
					startScroll();
				}

			}
		};
	}

	/**
	 * 保持数据源
	 * 
	 * @param data
	 */
	public void setDataSource(String data) {
		stopScroll();
		if (data != null) {
			this.dataSource = data;
			setText(dataSource);
		}
		startScroll();
	}

	/**
	 * 开始滚动
	 */
	public void startScroll() {
		handler.postDelayed(runOperation, delayMillis);
	}

	/**
	 * 停止滚动
	 */
	public void stopScroll() {
		if (runOperation != null) {
			handler.removeCallbacks(runOperation);
		}
	}

}
