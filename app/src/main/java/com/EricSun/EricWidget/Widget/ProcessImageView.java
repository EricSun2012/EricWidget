package com.EricSun.EricWidget.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Eric on 16/6/14.
 */
public class ProcessImageView extends ImageView {

    private Paint mPaint;// 画笔
    int width = 0;
    int height = 0;
    Context context = null;
    int progress = 100;

    public ProcessImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ProcessImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProcessImageView(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.FILL);


        mPaint.setColor(Color.parseColor("#70000000"));// 半透明
        canvas.drawRect(0, 0, getWidth(), getHeight() - getHeight() * progress
                / 100, mPaint);

        mPaint.setColor(Color.parseColor("#00000000"));// 全透明
        canvas.drawRect(0, getHeight() - getHeight() * progress / 100,
                getWidth(), getHeight(), mPaint);

//        mPaint.setTextSize(30);
//        mPaint.setColor(Color.parseColor("#FFFFFF"));
//        mPaint.setStrokeWidth(2);
//        Rect rect = new Rect();
//        mPaint.getTextBounds(progress + "%", 0, (progress + "%").length(), rect);// 确定文字的宽度
//        canvas.drawText(progress + "%", getWidth() / 2 - rect.width() / 2,
//                getHeight() / 2, mPaint);

    }

    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }
}