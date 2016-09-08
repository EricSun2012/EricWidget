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

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.EricSun.EricWidget.R;


/**
 * 高仿iOS 7switchButton
 */
@SuppressLint("NewApi")
public class ShSwitchView extends View {

    private static final long commonDuration = 300L;
    private static final int intrinsicWidth = 0;
    private static final int intrinsicHeight = 0;
    private static final int backgroundColor = 0xFFCCCCCC;
    private static final int foregroundColor = 0xFFEFEFEF;
    private ObjectAnimator innerContentAnimator;
    private ObjectAnimator knobExpandAnimator;
    private ObjectAnimator knobMoveAnimator;
    private GestureDetector gestureDetector;
    private int width;
    private int height;
    private int centerX;
    private int centerY;
    private float cornerRadius;
    private int shadowSpace;
    private int outerStrokeWidth;
    private RectF knobBound;
    private float knobMaxExpandWidth;
    private float intrinsicKnobWidth;
    private float knobExpandRate;
    private Property<ShSwitchView, Float> knobExpandProperty = new Property<ShSwitchView, Float>(
            Float.class, "knobExpand") {
        @Override
        public void set(ShSwitchView sv, Float knobExpandRate) {
            sv.setKnobExpandRate(knobExpandRate);
        }

        @Override
        public Float get(ShSwitchView sv) {
            return sv.getKnobExpandRate();
        }
    };
    private float knobMoveRate;
    private boolean knobState;
    private boolean isOn;
    private boolean preIsOn;
    private RectF innerContentBound;
    private float innerContentRate = 1.0F;
    private float intrinsicInnerWidth;
    private float intrinsicInnerHeight;
    private Property<ShSwitchView, Float> innerContentProperty = new Property<ShSwitchView, Float>(
            Float.class, "innerBound") {
        @Override
        public void set(ShSwitchView sv, Float innerContentRate) {
            sv.setInnerContentRate(innerContentRate);
        }

        @Override
        public Float get(ShSwitchView sv) {
            return sv.getInnerContentRate();
        }
    };
    private int tintColor;
    private int colorStep = backgroundColor;
    private Property<ShSwitchView, Float> knobMoveProperty = new Property<ShSwitchView, Float>(
            Float.class, "knobMove") {
        @Override
        public void set(ShSwitchView sv, Float knobMoveRate) {
            sv.setKnobMoveRate(knobMoveRate);
        }

        @Override
        public Float get(ShSwitchView sv) {
            return sv.getKnobMoveRate();
        }
    };
    private Paint paint;
    private RectF ovalForPath;
    private Path roundRectPath;
    private OnSwitchStateChangeListener onSwitchStateChangeListener;
    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent event) {

            preIsOn = isOn;

            return true;
        }

        @Override
        public void onShowPress(MotionEvent event) {

            innerContentAnimator.setFloatValues(innerContentRate, 0.0F);
            innerContentAnimator.start();

            knobExpandAnimator.setFloatValues(knobExpandRate, 1.0F);
            knobExpandAnimator.start();
        }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {

            isOn = knobState;

            if (preIsOn == isOn) {
                isOn = !isOn;
                knobState = !knobState;
            }

            if (knobState) {

                knobMoveAnimator.setFloatValues(knobMoveRate, 1.0F);
                knobMoveAnimator.start();

                innerContentAnimator.setFloatValues(innerContentRate, 0.0F);
                innerContentAnimator.start();
            } else {

                knobMoveAnimator.setFloatValues(knobMoveRate, 0.0F);
                knobMoveAnimator.start();

                innerContentAnimator.setFloatValues(innerContentRate, 1.0F);
                innerContentAnimator.start();
            }

            knobExpandAnimator.setFloatValues(knobExpandRate, 0.0F);
            knobExpandAnimator.start();

            if (ShSwitchView.this.onSwitchStateChangeListener != null
                    && isOn != preIsOn) {
                ShSwitchView.this.onSwitchStateChangeListener
                        .onSwitchStateChange(isOn);
            }

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {

            if (e2.getX() > centerX) {
                if (!knobState) {
                    knobState = !knobState;

                    knobMoveAnimator.setFloatValues(knobMoveRate, 1.0F);
                    knobMoveAnimator.start();

                    innerContentAnimator.setFloatValues(innerContentRate, 0.0F);
                    innerContentAnimator.start();
                }
            } else {
                if (knobState) {
                    knobState = !knobState;

                    knobMoveAnimator.setFloatValues(knobMoveRate, 0.0F);
                    knobMoveAnimator.start();

                }
            }

            return true;
        }
    };

    public ShSwitchView(Context context) {
        this(context, null);
    }

    public ShSwitchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShSwitchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.ShSwitchView);

//		tintColor = ta.getColor(R.styleable.ShSwitchView_tintColor, 0xFF9CE949);
        tintColor = ta.getColor(R.styleable.ShSwitchView_tintColor, 0xFF9CE949);

        int defaultOuterStrokeWidth = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1.5F, context.getResources()
                        .getDisplayMetrics());
        int defaultShadowSpace = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources()
                        .getDisplayMetrics());

        outerStrokeWidth = ta.getDimensionPixelOffset(
                R.styleable.ShSwitchView_outerStrokeWidth,
                defaultOuterStrokeWidth);
        shadowSpace = ta.getDimensionPixelOffset(
                R.styleable.ShSwitchView_shadowSpace, defaultShadowSpace);

        ta.recycle();

        knobBound = new RectF();
        innerContentBound = new RectF();
        ovalForPath = new RectF();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        roundRectPath = new Path();

        gestureDetector = new GestureDetector(context, gestureListener);
        gestureDetector.setIsLongpressEnabled(false);

        if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        innerContentAnimator = ObjectAnimator.ofFloat(ShSwitchView.this,
                innerContentProperty, innerContentRate, 1.0F);
        innerContentAnimator.setDuration(commonDuration);
        innerContentAnimator.setInterpolator(new DecelerateInterpolator());

        knobExpandAnimator = ObjectAnimator.ofFloat(ShSwitchView.this,
                knobExpandProperty, knobExpandRate, 1.0F);
        knobExpandAnimator.setDuration(commonDuration);
        knobExpandAnimator.setInterpolator(new DecelerateInterpolator());

        knobMoveAnimator = ObjectAnimator.ofFloat(ShSwitchView.this,
                knobMoveProperty, knobMoveRate, 1.0F);
        knobMoveAnimator.setDuration(commonDuration);
        knobMoveAnimator.setInterpolator(new DecelerateInterpolator());
    }

    public OnSwitchStateChangeListener getOnSwitchStateChangeListener() {
        return this.onSwitchStateChangeListener;
    }

    public void setOnSwitchStateChangeListener(
            OnSwitchStateChangeListener onSwitchStateChangeListener) {
        this.onSwitchStateChangeListener = onSwitchStateChangeListener;
    }

    float getInnerContentRate() {
        return this.innerContentRate;
    }

    void setInnerContentRate(float rate) {
        this.innerContentRate = rate;

        float w = intrinsicInnerWidth / 2.0F * rate;
        float h = intrinsicInnerHeight / 2.0F * rate;

        this.innerContentBound.left = centerX - w;
        this.innerContentBound.top = centerY - h;
        this.innerContentBound.right = centerX + w;
        this.innerContentBound.bottom = centerY + h;

        invalidate();
    }

    float getKnobExpandRate() {
        return this.knobExpandRate;
    }

    void setKnobExpandRate(float rate) {
        this.knobExpandRate = rate;

        float w = intrinsicKnobWidth
                + (knobMaxExpandWidth - intrinsicKnobWidth) * rate;

        boolean left = knobBound.left + knobBound.width() / 2 > centerX;

        if (left) {
            knobBound.left = knobBound.right - w;
        } else {
            knobBound.right = knobBound.left + w;
        }

        invalidate();
    }

    float getKnobMoveRate() {
        return knobMoveRate;
    }

    void setKnobMoveRate(float rate) {
        this.knobMoveRate = rate;

        float kw = knobBound.width();
        float w = (width - kw - ((shadowSpace + outerStrokeWidth) * 2))
                * rate;

        this.colorStep = RGBColorTransform(rate, backgroundColor, tintColor);

        knobBound.left = shadowSpace + outerStrokeWidth + w;
        knobBound.right = knobBound.left + kw;

        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        // make sure widget remain in a good appearance
        if ((float) height / (float) width < 0.33333F) {
            height = (int) (width * 0.33333F);

            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
                    MeasureSpec.getMode(widthMeasureSpec));
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                    MeasureSpec.getMode(heightMeasureSpec));

            super.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        }

        centerX = width / 2;
        centerY = height / 2;

        cornerRadius = centerY - shadowSpace;

        innerContentBound.left = outerStrokeWidth + shadowSpace;
        innerContentBound.top = outerStrokeWidth + shadowSpace;
        innerContentBound.right = width - outerStrokeWidth - shadowSpace;
        innerContentBound.bottom = height - outerStrokeWidth - shadowSpace;

        intrinsicInnerWidth = innerContentBound.width();
        intrinsicInnerHeight = innerContentBound.height();

        knobBound.left = outerStrokeWidth + shadowSpace;
        knobBound.top = outerStrokeWidth + shadowSpace;
        knobBound.right = height - outerStrokeWidth - shadowSpace;
        knobBound.bottom = height - outerStrokeWidth - shadowSpace;

        intrinsicKnobWidth = knobBound.height();
        knobMaxExpandWidth = width * 0.7F;
        if (knobMaxExpandWidth > knobBound.width() * 1.25F) {
            knobMaxExpandWidth = knobBound.width() * 1.25F;
        }
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void setOn(boolean on) {
        setOn(on, false);
    }

    public void setOnWithNoneCallback(boolean on, boolean animated) {


        if (this.isOn == on)
            return;

        this.isOn = on;
        knobState = this.isOn;

        if (!animated) {
            knobMoveAnimator.setDuration(1L);
            innerContentAnimator.setDuration(1L);
            knobExpandAnimator.setDuration(1L);
        }

        if (knobState) {

            knobMoveAnimator.setFloatValues(knobMoveRate, 1.0F);
            knobMoveAnimator.start();

            innerContentAnimator.setFloatValues(innerContentRate, 0.0F);
            innerContentAnimator.start();
        } else {

            knobMoveAnimator.setFloatValues(knobMoveRate, 0.0F);
            knobMoveAnimator.start();

            innerContentAnimator.setFloatValues(innerContentRate, 1.0F);
            innerContentAnimator.start();
        }

        knobExpandAnimator.setFloatValues(knobExpandRate, 0.0F);
        knobExpandAnimator.start();

        if (!animated) {
            knobMoveAnimator.setDuration(commonDuration);
            innerContentAnimator.setDuration(commonDuration);
            knobExpandAnimator.setDuration(commonDuration);
        }

    }

    public void setOn(boolean on, boolean animated) {
        if (this.isOn == on)
            return;
        setOnWithNoneCallback(on, animated);

        if (ShSwitchView.this.onSwitchStateChangeListener != null
                && isOn != preIsOn) {
            ShSwitchView.this.onSwitchStateChangeListener
                    .onSwitchStateChange(isOn);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.d("action", "outside");
                if (!knobState) {
                    innerContentAnimator = ObjectAnimator.ofFloat(
                            ShSwitchView.this, innerContentProperty,
                            innerContentRate, 1.0F);
                    innerContentAnimator.setDuration(300L);
                    innerContentAnimator
                            .setInterpolator(new DecelerateInterpolator());

                    innerContentAnimator.start();
                }

                knobExpandAnimator = ObjectAnimator.ofFloat(ShSwitchView.this,
                        knobExpandProperty, knobExpandRate, 0.0F);
                knobExpandAnimator.setDuration(300L);
                knobExpandAnimator.setInterpolator(new DecelerateInterpolator());

                knobExpandAnimator.start();

                isOn = knobState;

                if (ShSwitchView.this.onSwitchStateChangeListener != null
                        && isOn != preIsOn) {
                    ShSwitchView.this.onSwitchStateChangeListener
                            .onSwitchStateChange(isOn);
                }

                break;
        }

        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // background
        paint.setColor(colorStep);
        paint.setStyle(Paint.Style.FILL);

        drawRoundRect(shadowSpace, shadowSpace, width - shadowSpace, height
                - shadowSpace, cornerRadius, canvas, paint);

        // innerContent
        paint.setColor(foregroundColor);
        drawRoundRect(innerContentBound, innerContentBound.height() / 2,
                canvas, paint);

        // knob
        paint.setShadowLayer(shadowSpace / 2, 0, shadowSpace / 2, 0x44000000);
        drawRoundRect(knobBound, cornerRadius - outerStrokeWidth, canvas, paint);
        paint.setShadowLayer(0, 0, 0, 0);

        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

        drawRoundRect(knobBound, cornerRadius - outerStrokeWidth, canvas, paint);

    }

    private void drawRoundRect(RectF bound, float radius, Canvas canvas,
                               Paint paint) {
        drawRoundRect(bound.left, bound.top, bound.right, bound.bottom, radius,
                canvas, paint);
    }

    private void drawRoundRect(float left, float top, float right,
                               float bottom, float radius, Canvas canvas, Paint paint) {
        roundRectPath.reset();

        float radiusSize = radius * 2;

        roundRectPath.moveTo(left, top + radius);

        ovalForPath.set(left, top, left + radiusSize, top + radiusSize);
        roundRectPath.arcTo(ovalForPath, 180, 90, false);
        roundRectPath.lineTo(right - radius, top);
        ovalForPath.set(right - radiusSize, top, right, top + radiusSize);
        roundRectPath.arcTo(ovalForPath, 270, 90, false);
        roundRectPath.lineTo(right, bottom - radius);
        ovalForPath.set(right - radiusSize, bottom - radiusSize, right, bottom);
        roundRectPath.arcTo(ovalForPath, 0, 90, false);
        roundRectPath.lineTo(left + radius, bottom);
        ovalForPath.set(left, bottom - radiusSize, left + radiusSize, bottom);
        roundRectPath.arcTo(ovalForPath, 90, 90, false);
        roundRectPath.close();

        canvas.drawPath(roundRectPath, paint);
    }

    // seperate RGB channels and calculate new value for each channel
    // ignore alpha channel
    private int RGBColorTransform(float progress, int fromColor, int toColor) {
        int or = (fromColor >> 16) & 0xFF;
        int og = (fromColor >> 8) & 0xFF;
        int ob = fromColor & 0xFF;

        int nr = (toColor >> 16) & 0xFF;
        int ng = (toColor >> 8) & 0xFF;
        int nb = toColor & 0xFF;

        int rGap = (int) ((nr - or) * progress);
        int gGap = (int) ((ng - og) * progress);
        int bGap = (int) ((nb - ob) * progress);

        return 0xFF000000 | ((or + rGap) << 16) | ((og + gGap) << 8)
                | (ob + bGap);

    }

    public interface OnSwitchStateChangeListener {
        public void onSwitchStateChange(boolean isOn);
    }
}
