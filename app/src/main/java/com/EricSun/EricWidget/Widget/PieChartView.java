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
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.View;

//import android.view.animation.AccelerateDecelerateInterpolator;
//import android.view.animation.Animation;
//import android.view.animation.DecelerateInterpolator;
//import android.view.animation.Transformation;


/**
 * 大饼图．
 *
 * @author zhou
 */
public class PieChartView extends View {

    /**
     * 绘制区域
     */
    RectF oval = new RectF();

    RectF ovalPie = new RectF();

    private Paint bgPaint;

    private int strokeWidth = 7;

    /**
     * 颜色是key,百分比是value　考虑到百分比可能相同,而相同颜色没有太大意义
     */
    private SparseIntArray mPipAndColor;

    private Paint piePaint;

    private Paint progressPaint;

    boolean inProgress;

    boolean isAnimation;

    /**
     * 动画
     */
    Runnable invalidateCall = new Runnable() {

        @Override
        public void run() {
            mProgressStartAngle += 5;
            invalidate();
        }
    };

    Runnable invalidatePie = new Runnable() {
        @Override
        public void run() {
            mProgressStartAngle += 3;
            if (mProgressStartAngle <= 120) {
                invalidate();
            }
        }
    };


    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        strokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, strokeWidth, getResources()
                .getDisplayMetrics());
        // complexToDimensionPixelOffset(10, getResources().getDisplayMetrics());
        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
//        bgPaint.setStyle(Paint.Style.STROKE);
//        bgPaint.setStrokeWidth(strokeWidth);
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(0xFFC0C0C0);
        piePaint = new Paint();
        piePaint.setAntiAlias(true);
        piePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        if (isInEditMode()) {
            forPreview();
        }


    }

    public PieChartView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    private void forPreview() {
        SparseIntArray pipAndColor = new SparseIntArray(5);
        pipAndColor.put(Color.YELLOW, 70);
        pipAndColor.put(Color.GREEN, 33);
        pipAndColor.put(Color.RED, 21);
        pipAndColor.put(Color.MAGENTA, 29);
        setData(pipAndColor);
    }

//    private void startAnimation() {
//        PieAnimation animation = new PieAnimation(this);
//        animation.setDuration(300);
//        animation.setInterpolator(new DecelerateInterpolator());
//        this.startAnimation(animation);
//    }

    public void init(int[] color) {
        mPipAndColor = new SparseIntArray(4);
        mPipAndColor.put(color[0], 0);
        mPipAndColor.put(color[1], 0);
        mPipAndColor.put(color[2], 0);
        mPipAndColor.put(color[3], 0);
    }

    /**
     * 显示加载进度
     */
    public void beginLoad() {
        mProgressStartAngle = 0;
        inProgress = true;
        invalidate();
    }

    public void setData(SparseIntArray pipAndColor, boolean anim) {
        inProgress = false;
        isAnimation = anim;
        removeCallbacks(invalidateCall);
        removeCallbacks(invalidatePie);

        if (anim) {
            this.mPipAndColor = pipAndColor;
            postDelayed(invalidatePie, 50);
//            startAnimation();
        } else {
            setData(pipAndColor);
        }
    }

    public void setData(SparseIntArray pipAndColor) {
        this.mPipAndColor = pipAndColor;
        invalidate();
    }

    public SparseIntArray getData() {
        return this.mPipAndColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        if (mPipAndColor != null) {
            if (inProgress) {
                drawProgressPie(canvas);
            } else {
                drawPie(canvas);
            }
        }
    }

    private void drawBg(Canvas canvas) {
        // canvas.drawOval(oval, bgPaint);
//        canvas.drawCircle(oval.centerX(), oval.centerY(), (oval.width() + strokeWidth) / 2, bgPaint);
        canvas.drawCircle(oval.centerX(), oval.centerY(), oval.width() / 2, bgPaint);
    }

    private int mProgressStartAngle = 0;
    private int mProgressSweepAngle = 0;

    /**
     * 旋转的进度圈
     *
     * @param canvas
     */
    private void drawProgressPie(Canvas canvas) {
        int padding = 40 / mPipAndColor.size();
        if (mProgressStartAngle < 360) {
            mProgressSweepAngle = (1 + mProgressStartAngle) / mPipAndColor.size();
        } else {
            mProgressSweepAngle = (360) / mPipAndColor.size() - padding;
        }
        int startAngle = mProgressStartAngle;
        for (int i = 0; i < mPipAndColor.size(); i++) {
            int color = mPipAndColor.keyAt(i);
            progressPaint.setColor(color);
            canvas.drawArc(ovalPie, startAngle, mProgressSweepAngle, false, progressPaint);
            startAngle += mProgressSweepAngle + padding;
        }
        postDelayed(invalidateCall, 50);
    }

    private void drawPie(Canvas canvas) {
        int startAngle = 90;
        for (int i = 0; i < mPipAndColor.size(); i++) {
            int color = mPipAndColor.keyAt(i);
//            int percent = (int) (mPipAndColor.valueAt(i) + 0.5);

            int percent = (int) (mPipAndColor.valueAt(i));
            if (isAnimation) {
                if (percent > 3) {
                    percent = percent * mProgressStartAngle / 120;
                }
            }

            piePaint.setColor(color);
            canvas.drawArc(oval, startAngle, percent, true, piePaint);
            startAngle += percent;
        }
        if (isAnimation) {
            postDelayed(invalidatePie, 30);
        }
    }


    /**
     * 依据宽度定直径
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int left, right, top, bottom;
        if ((w > 0) && (h > 0) && ((w != oldw) || (h != oldh))) {
            left = getPaddingLeft() + strokeWidth;
            right = getPaddingRight() + strokeWidth;
            top = getPaddingTop() + strokeWidth;
            bottom = getPaddingBottom() + strokeWidth;
            oval.set(left, top, w - right, w - bottom);
            ovalPie.set(left + strokeWidth, top + strokeWidth, w - right - strokeWidth, w - bottom - strokeWidth);
//            Logger.dd("w:%d,h:%d", w, h); strokeWidth
        }
    }

//    public class PieAnimation extends Animation {
//
//        SparseIntArray endPipAndColor;
//        SparseIntArray animPipAndColor;
//        private PieChartView mPieChartView;
//
//        public PieAnimation(PieChartView pieChartView) {
//            super();
//            endPipAndColor = pieChartView.getData().clone();
//            animPipAndColor = pieChartView.getData().clone();
//            mPieChartView = pieChartView;
//        }
//
//        @Override
//        public void initialize(int width, int height, int parentWidth, int parentHeight) {
//            super.initialize(width, height, parentWidth, parentHeight);
//        }
//
//        @Override
//        public boolean willChangeBounds() {
//            return false;
//        }
//
//        @Override
//        protected void applyTransformation(float interpolatedTime, Transformation t) {
//            super.applyTransformation(interpolatedTime, t);
//            if (interpolatedTime < 1) {
//                for (int i = 0; i < endPipAndColor.size(); i++) {
//                    animPipAndColor.put(endPipAndColor.keyAt(i), (int) (endPipAndColor.valueAt(i) * interpolatedTime));
//                }
//                mPieChartView.setData(animPipAndColor);
//            }
//        }
//
//    }

}