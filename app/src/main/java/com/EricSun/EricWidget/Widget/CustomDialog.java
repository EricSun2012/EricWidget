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

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.EricSun.EricWidget.R;


public class CustomDialog extends Dialog {
    public LinearLayout customLayout;
    private EditText editText;
    private Button positiveButton, negativeButton;
    private TextView titleView, contentView;
    private boolean _lock = false;

    public CustomDialog(Context context) {
        super(context, R.style.CustomDialog);
        setCustomDialog(R.layout.custom_dialog);
    }

    public CustomDialog(Context context, int ResId) {
        super(context, R.style.CustomDialog);
        setCustomDialog(ResId);
    }

    private void setCustomDialog(int ResId) {

        View mView = LayoutInflater.from(getContext()).inflate(ResId, null);
        titleView = (TextView) mView.findViewById(R.id.Tv_Title);
        contentView = (TextView) mView.findViewById(R.id.Tv_content);
        editText = (EditText) mView.findViewById(R.id.Edit_input);
        customLayout = (LinearLayout) mView
                .findViewById(R.id.Linear_customView);
        positiveButton = (Button) mView.findViewById(R.id.Bt_ok);
        negativeButton = (Button) mView.findViewById(R.id.Bt_cancle);
        super.setContentView(mView);
        this.setCanceledOnTouchOutside(false);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    public void setCustomDialog(String title,
                                String content,
                                String buttonTitle[],
                                View.OnClickListener buttonListener[]) {
        titleView.setText(title);
        if (null != content && content.length() > 0) {
            contentView.setVisibility(View.VISIBLE);
            contentView.setText(content);
        }
        if (null != buttonTitle) {
            if (buttonTitle.length >= 2) {
                negativeButton.setText(buttonTitle[1]);
            }
            positiveButton.setText(buttonTitle[0]);
            if (buttonTitle.length == 1) {
                setSingleButton(true);
            }
        }
        if (null != buttonListener) {
            if (buttonListener.length >= 2) {
                negativeButton.setOnClickListener(buttonListener[1]);
            }
            positiveButton.setOnClickListener(buttonListener[0]);
        }
    }


    /**
     * 锁定dialog
     *
     * @param lock
     */
    public void setLockDialog(boolean lock) {
        _lock = lock;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!_lock) {
            return super.onKeyDown(keyCode, event);
        }
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public String getContent() {
        return contentView.getText().toString();
    }


    /**
     * 显示单一positiveButton
     *
     * @param isSingle
     */
    public void setSingleButton(boolean isSingle) {
        findViewById(R.id.View_vertical).setVisibility(isSingle ? View.GONE : View.VISIBLE);
        negativeButton.setVisibility(isSingle ? View.GONE : View.VISIBLE);
    }


    public void setContent(String contentString) {
        contentView.setVisibility(View.VISIBLE);
        this.contentView.setText(contentString);
    }

    public TextView getTitleView() {
        return titleView;
    }

    public void setTitleView(TextView title) {
        this.titleView = title;
    }

    public String getTitle() {
        return titleView.getText().toString();
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public TextView getContentView() {
        return contentView;
    }

    public void setContentView(TextView content) {
        this.contentView = content;
    }

    /**
     * 获取自定义view
     *
     * @return
     */
    public View getCustomView() {
        return customLayout;
    }

    /**
     * 添加自定义view
     *
     * @param childView
     */
    public void setCustomtView(View childView) {
        if (customLayout != null) {
            customLayout.removeAllViews();
            customLayout.addView(childView);
        }
    }

    public View getEditText() {
        return editText;
    }

    public String getPositiveTitle() {
        return positiveButton.getText().toString();
    }

    public void setPositiveTitle(String title) {
        this.positiveButton.setText(title);
    }

    public Button getPositiveButton() {
        return this.positiveButton;
    }

    public void setPositiveButton(Button positiveButton) {
        this.positiveButton = positiveButton;
    }

    public String getNegativeTitle() {
        return negativeButton.getText().toString();
    }

    public void setNegativeTitle(String title) {
        this.negativeButton.setText(title);
    }

    public Button getNegativeButton() {
        return this.negativeButton;
    }

    public void setNegativeButton(Button negativeButton) {
        this.negativeButton = negativeButton;
    }


    /**
     * 确定键监听器
     *
     * @param listener
     */
    public void setOnPositiveListener(View.OnClickListener listener) {
        positiveButton.setOnClickListener(listener);
    }

    /**
     * 取消键监听器
     *
     * @param listener
     */
    public void setOnNegativeListener(View.OnClickListener listener) {
        negativeButton.setOnClickListener(listener);
    }
}
