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
