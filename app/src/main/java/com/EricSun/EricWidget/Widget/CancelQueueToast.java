package com.EricSun.EricWidget.Widget;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.EricSun.EricWidget.R;

/**
 * Created by Administrator on 2015/10/7.
 */

/**
 * 立即显示的toast
 */
public class CancelQueueToast {
    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
            mToast = null;
        }
    };

    public static void showToast(Context mContext, String text) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_toast, null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(text);
        mHandler.removeCallbacks(r);
        if (mToast == null) {
            mToast = new Toast(mContext);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            mToast.setView(view);
        }
        mHandler.postDelayed(r, 1000);
        mToast.show();
    }
}
