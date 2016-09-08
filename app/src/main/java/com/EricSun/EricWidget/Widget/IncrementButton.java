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
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import java.lang.ref.WeakReference;

/**
 * Created by Eric on 15/10/21.
 */
public class IncrementButton extends Button {

    final private int milDelay = 125;
    private OnLongClickListener mClickListener;
    private boolean pressed = false;
    private WeakReference<Button> mButton;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null != mClickListener) {
                mClickListener.onLongClick(mButton.get());
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pressed == true) {
                            mHandler.obtainMessage(0).sendToTarget();
                        }
                    }
                }, milDelay);
            }
        }
    };

    public IncrementButton(Context context) {
        this(context, null);
    }

    public IncrementButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IncrementButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mButton = new WeakReference<Button>(this);
    }

    public OnLongClickListener getOnLongClickListener() {
        return mClickListener;
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        mClickListener = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                pressed = true;
                if (null != mHandler) {
                    event.getEventTime();
                    mHandler.obtainMessage(0).sendToTarget();
                }
            }
            break;
            case MotionEvent.ACTION_OUTSIDE: {
                pressed = false;
                if (null != mHandler) {
                    mHandler.removeMessages(0);
                }
            }
            break;
            case MotionEvent.ACTION_UP: {
                pressed = false;
                if (null != mHandler) {
                    mHandler.removeMessages(0);
                }
            }
            break;
        }
        return pressed;
    }
}
