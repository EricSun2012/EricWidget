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
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;

public class CenterRadioButton extends RadioButton {
	private Context context;

	private Drawable mButtonDrawable;
	private int mButtonResource;

	public CenterRadioButton(Context context) {
		super(context);
		this.context = context;
	}

	public CenterRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	public void setButtonDrawable(int resid) {
		if (resid != 0 && resid == mButtonResource) {
			return;
		}

		mButtonResource = resid;

		Drawable d = null;
		if (mButtonResource != 0) {
			d = getResources().getDrawable(mButtonResource);
		}
		setButtonDrawable(d);
	}

	@Override
	public void setButtonDrawable(Drawable d) {
		if (d != null) {
			if (mButtonDrawable != null) {
				mButtonDrawable.setCallback(null);
				unscheduleDrawable(mButtonDrawable);
			}
			d.setCallback(this);
			d.setState(getDrawableState());
			d.setVisible(getVisibility() == VISIBLE, false);
			mButtonDrawable = d;
			mButtonDrawable.setState(null);
			setMinHeight(mButtonDrawable.getIntrinsicHeight());
		}

		refreshDrawableState();
	}

	// 核心代码部分
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		final Drawable buttonDrawable = mButtonDrawable;
		if (buttonDrawable != null) {
			final int verticalGravity = getGravity()
					& Gravity.VERTICAL_GRAVITY_MASK;
			final int height = buttonDrawable.getIntrinsicHeight();
			final int width = buttonDrawable.getIntrinsicWidth();
			int y = 0;
			int x = 0;
			x = (getWidth() - width) / 2;
			
			switch (verticalGravity) {
			case Gravity.BOTTOM:
				y = getHeight() - height;
				break;
			case Gravity.CENTER_VERTICAL:
				y = (getHeight() - height) / 2;
				break;
			case Gravity.CENTER:
				y = (getHeight() - height) / 2;
				
			}
			
			

			buttonDrawable.setBounds(x, y, x + width, y + height);
			buttonDrawable.draw(canvas);
		}
	}
}