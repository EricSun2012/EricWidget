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
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import java.util.List;

/**
 * 上下滚动显示文字
 */
public class AutoTextView extends TextSwitcher implements
        ViewSwitcher.ViewFactory {

    public AutoRollTextSelectItem delegate;
    private Context mContext;
    //mInUp,mOutUp分别构成向下翻页的进出动画
    private Rotate3dAnimation mInUp;
    private Rotate3dAnimation mOutUp;
    //mInDown,mOutDown分别构成向下翻页的进出动画
    private Rotate3dAnimation mInDown;
    private Rotate3dAnimation mOutDown;
    private NoticeChangeTask noticeChangeTask;
    private List<String> newInfoList;
    private int currentIndex = 0;
    private boolean canRoll = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (canRoll) {
                currentIndex++;
                String showString = newInfoList.get(currentIndex);
                next();
                setText(showString);
                if (null != delegate) {
                    delegate.autoRollTextSelect(currentIndex);
                }
                startRoll();
                if (currentIndex >= newInfoList.size() - 1) {
                    //如果currentIndex=0，在执行if(canRoll)的时候会++，
                    // 这样的结果是除了第一次currentIndex=0时显示了第一条数据，
                    // 以后再也不会显示第一条数据,所以currentIndex=-1
                    currentIndex = -1;
                }
            }
        }
    };

    public AutoTextView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mContext = context;
        noticeChangeTask = new NoticeChangeTask();
        init();
    }

    public void setNewInfoList(List<String> newInfoList) {
        this.newInfoList = newInfoList;
        setText(newInfoList.get(0));
    }

    public void startRoll() {
        if (newInfoList.size() > 1) {
            canRoll = true;
            handler.postDelayed(noticeChangeTask, 4000);//控制切换时间间隔
        }
    }

    public void stopRoll() {
        if (noticeChangeTask != null) {
            handler.removeCallbacks(noticeChangeTask);
        }
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    private void init() {
        // TODO Auto-generated method stub
        setFactory(this);
//		mInUp = createAnim(-90, 0 , true, true);
//		mOutUp = createAnim(0, 90, false, true);
//		mInDown = createAnim(90, 0 , true , false);
//		mOutDown = createAnim(0, -90, false, false);
        mInUp = createAnim(0, 0, true, true);
        mOutUp = createAnim(0, 0, false, true);
        mInDown = createAnim(0, 0, true, false);
        mOutDown = createAnim(0, 0, false, false);
        //TextSwitcher主要用于文件切换，比如 从文字A 切换到 文字 B，
        //setInAnimation()后，A将执行inAnimation，
        //setOutAnimation()后，B将执行OutAnimation
        setInAnimation(mInUp);
        setOutAnimation(mOutUp);
    }

    private Rotate3dAnimation createAnim(float start, float end, boolean turnIn, boolean turnUp) {
        final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, turnIn, turnUp);
        rotation.setDuration(500);
        rotation.setFillAfter(false);
        rotation.setInterpolator(new AccelerateInterpolator());
        return rotation;
    }

    //这里返回的TextView，就是我们看到的View
    @Override
    public View makeView() {
        // TODO Auto-generated method stub
        TextView t = new TextView(mContext);
        t.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        t.setTextSize(14);
        t.setMaxLines(1);
        t.setSingleLine();
        t.setTextColor(0xA9A9A9);
        return t;
    }

    //定义动作，向下滚动翻页
    public void previous() {
        if (getInAnimation() != mInDown) {
            setInAnimation(mInDown);
        }
        if (getOutAnimation() != mOutDown) {
            setOutAnimation(mOutDown);
        }
    }

    //定义动作，向上滚动翻页
    public void next() {
        if (getInAnimation() != mInUp) {
            setInAnimation(mInUp);
        }
        if (getOutAnimation() != mOutUp) {
            setOutAnimation(mOutUp);
        }
    }

    public interface AutoRollTextSelectItem {
        public void autoRollTextSelect(int itemPosition);
    }

    public class NoticeChangeTask implements Runnable {
        @Override
        public void run() {
            handler.obtainMessage().sendToTarget();
        }
    }

    class Rotate3dAnimation extends Animation {
        private final float mFromDegrees;
        private final float mToDegrees;
        private final boolean mTurnIn;
        private final boolean mTurnUp;
        private float mCenterX;
        private float mCenterY;
        private Camera mCamera;

        public Rotate3dAnimation(float fromDegrees, float toDegrees, boolean turnIn, boolean turnUp) {
            mFromDegrees = fromDegrees;
            mToDegrees = toDegrees;
            mTurnIn = turnIn;
            mTurnUp = turnUp;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
//			mCenterY = getHeight() / 2;
            mCenterY = getHeight();
            mCenterX = getWidth() / 2;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float fromDegrees = mFromDegrees;
            float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;
            final int derection = mTurnUp ? 1 : -1;

            final Matrix matrix = t.getMatrix();

            camera.save();
            if (mTurnIn) {
                camera.translate(0.0f, derection * mCenterY * (interpolatedTime - 1.0f), 0.0f);
            } else {
                camera.translate(0.0f, derection * mCenterY * (interpolatedTime), 0.0f);
            }
            camera.rotateX(degrees);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }
}