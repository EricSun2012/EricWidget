package com.EricSun.EricWidget.Widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.EricSun.EricWidget.R;

/**
 * @name自定义圆形进度条对话框
 */
public class CustomProgressDialog extends ProgressDialog {

    private String content;
    private TextView progress_dialog_content;

    private CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        setCanceledOnTouchOutside(false);
    }

    public CustomProgressDialog(Context context, String content) {
        this(context, R.style.AppTheme_Transparent);
        this.content = content;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        progress_dialog_content.setText(content);
    }

    public void setContent(String str) {
        progress_dialog_content.setText(str);
    }

    private void initView() {
        setContentView(R.layout.custom_progress_dialog);
        ImageView progressBar = (ImageView) findViewById(R.id.Imgv_progressBar);
        AnimationDrawable anim = (AnimationDrawable) progressBar.getBackground();
        anim.start();
        progress_dialog_content = (TextView) findViewById(R.id.progress_dialog_content);
    }

}
