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

package com.EricSun.EricWidget.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * 自定义的View，实现ListView A~Z快速索引效果
 * 
 * @author Folyd
 * 
 */
public class SlideBar extends View {
	private Paint paint = new Paint();
	private OnTouchLetterChangeListenner listenner;
	// 是否画出背景
	private boolean showBg = false;
	// 选中的项
	private int choose = -1;
	// 准备好的A~Z的字母数组
	public List<String> letters;
	

	/*
	 * public static String[] letters = { "#", "A", "B", "C", "D", "E", "F",
	 * "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
	 * "U", "V", "W", "X", "Y", "Z" };
	 */
	// 构造方法
	public SlideBar(Context context) {
		super(context);
	}

	public SlideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取宽和高
		int width = getWidth();
		int height = getHeight();

		if (showBg) {
			// 画出背景
			canvas.drawColor(Color.parseColor("#55000000"));
		}
		if (letters == null)
			return;
		// 每个字母的高度
		int singleHeight = height / letters.size();

		// 画字母
		for (int i = 0; i < letters.size(); i++) {
			paint.setColor(Color.BLACK);
			// 设置字体格式
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(20f);
			// 如果这一项被选中，则换一种颜色画
			if (i == choose) {
				paint.setColor(Color.parseColor("#F88701"));
				paint.setFakeBoldText(true);
			}
			// 要画的字母的x,y坐标
			float posX = width / 2 - paint.measureText(letters.get(i)) / 2;
			float posY = i * singleHeight + singleHeight;
			// 画出字母
			canvas.drawText(letters.get(i), posX, posY, paint);
			// 重新设置画笔
			paint.reset();
		}
	}

	/**
	 * 处理SlideBar的状态
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final float y = event.getY();
		// 算出点击的字母的索引
		final int index = (int) (y / getHeight() * letters.size());
		// 保存上次点击的字母的索引到oldChoose
		final int oldChoose = choose;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			showBg = true;
			if (oldChoose != index && listenner != null && index > 0
					&& index < letters.size()) {
				choose = index;
				listenner.onTouchLetterChange(showBg, letters.get(index));
				invalidate();
			}
			break;

		case MotionEvent.ACTION_MOVE:
			if (oldChoose != index && listenner != null && index > 0
					&& index < letters.size()) {
				choose = index;
				listenner.onTouchLetterChange(showBg, letters.get(index));
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			showBg = false;
			choose = -1;
			if (listenner != null) {
				if (index <= 0) {
					listenner.onTouchLetterChange(showBg, letters.get(0));
				} else if (index > 0 && index < letters.size()) {
					listenner.onTouchLetterChange(showBg, letters.get(index));
				} else if (index >= letters.size()) {
					listenner.onTouchLetterChange(showBg,
							letters.get(letters.size() - 1));
				}
			}
			invalidate();
			break;
		}
		return true;
	}

	/**
	 * 回调方法，注册监听器
	 * 
	 * @param listenner
	 */
	public void setOnTouchLetterChangeListenner(
			OnTouchLetterChangeListenner listenner) {
		this.listenner = listenner;
	}

	/**
	 * SlideBar 的监听器接口
	 * 
	 * @author Folyd
	 * 
	 */
	public interface OnTouchLetterChangeListenner {

		void onTouchLetterChange(boolean isTouched, String s);
	}

}
