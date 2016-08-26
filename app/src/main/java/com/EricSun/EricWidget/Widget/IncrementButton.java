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
