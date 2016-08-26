package com.EricSun.EricWidget.UI;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.EricSun.EricWidget.R;


/**
 * Created by Eric on 16/8/25.
 */
public class LoadingView extends RelativeLayout {


    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutParams params = new LayoutParams(150,150);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(params);


        addView(imageView);
        Drawable drawable = getResources().getDrawable(R.drawable.progressbar_animation);
        imageView.setImageDrawable(drawable);


        AnimationDrawable anim = (AnimationDrawable) drawable;
        anim.start();
    }

}
