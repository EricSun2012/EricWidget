package com.EricSun.EricWidget.Widget;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
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

    /**
     * 显示单一positiveButton
     *
     * @param isSingle
     */
    public void setSingleButton(boolean isSingle) {
        findViewById(R.id.View_vertical).setVisibility(isSingle ? View.GONE : View.VISIBLE);
        negativeButton.setVisibility(isSingle ? View.GONE : View.VISIBLE);
    }

    public String getContent() {
        return contentView.getText().toString();
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
