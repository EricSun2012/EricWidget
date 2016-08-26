package com.EricSun.EricWidget.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.EricSun.EricWidget.R;

/**
 * Created by Eric on 16/1/20.
 */
public class CustomProgressBar extends FrameLayout {

    public ProgressBar mProgressBar;
    public TextView mTextView;
    public int deltaDist = 10;

    private int height;
    private int width;

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void setProgress(int progress) {
        if (null != mProgressBar) {
            mProgressBar.setProgress(progress);

            MarginLayoutParams lp = (MarginLayoutParams) mProgressBar.getLayoutParams();
            int mWidth = width - lp.leftMargin - lp.rightMargin;
//            float xPoint = mProgressBar.getX();
            mWidth = mWidth * progress / mProgressBar.getMax();

            if (null != mTextView) {
//                mTextView.setX(mWidth + xPoint);
                mTextView.setX(mWidth - deltaDist);
                mTextView.setText(progress + "%");
            }
            postInvalidate();
        }
    }

    /**
     * @param context
     * @param attrs
     */
    private void initView(Context context, AttributeSet attrs) {

        View view = LinearLayout.inflate(context, R.layout.custom_progressbar, null);
        mProgressBar = (ProgressBar) view.findViewById(R.id.ProgressBar);
        mTextView = (TextView) view.findViewById(R.id.Tv_tips);
        addView(view);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.height = h;
        this.width = w;


        MarginLayoutParams lp = (MarginLayoutParams) mProgressBar.getLayoutParams();
        int mWidth = width - lp.leftMargin - lp.rightMargin;
//        float xPoint = mProgressBar.getX();
        mWidth = mWidth * mProgressBar.getProgress() / mProgressBar.getMax();

        if (null != mTextView) {
            mTextView.setX(mWidth - deltaDist);
        }
    }
}
