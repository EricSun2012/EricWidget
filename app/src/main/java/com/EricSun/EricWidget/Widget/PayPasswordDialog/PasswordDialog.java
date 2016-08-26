package com.EricSun.EricWidget.Widget.PayPasswordDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.EricSun.EricWidget.R;


public class PasswordDialog extends Dialog implements OnClickListener {

    private GridPasswordView gridpassword;
    private String passwordStr = "";
    private int layoutRes;
    private Context context;
    private ImageView imv_cancle;
    private InputDialogListener mDialogListener;
    private TextView tv_payMoney;//支付金额textview
    private String payMoney;//支付金额String
    private TextView tv_availableMoney;//可用余额textview
    private String availableMoney;//可用余额String
    public DialogInputCompleteDelegate delegate;


    public interface InputDialogListener {
        void onOK(String text);
    }

    public void setListener(InputDialogListener inputDialogListener) {
        this.mDialogListener = inputDialogListener;
    }

    public PasswordDialog(Context context) {
        super(context, R.style.mystyle);
        this.context = context;
    }

    public PasswordDialog(Context context, int resLayout,
                          String payMoney, String availableMoney) {
        super(context, R.style.mystyle);
        this.context = context;
        this.layoutRes = resLayout;
        this.payMoney = payMoney;
        this.availableMoney = availableMoney;
    }

    public PasswordDialog(Context context, int theme, int resLayout,
                          String payMoney, String availableMoney) {
        super(context, theme);
        this.context = context;
        this.layoutRes = resLayout;
        this.payMoney = payMoney;
        this.availableMoney = availableMoney;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.setContentView(layoutRes);
        setCanceledOnTouchOutside(false);
        gridpassword = (GridPasswordView) findViewById(R.id.password);
        gridpassword.setOnPasswordChangedListener(passlistener);
        imv_cancle = (ImageView) findViewById(R.id.imv_cancle);
        tv_payMoney = (TextView) findViewById(R.id.tv_payMoney);
        tv_payMoney.setText(payMoney);
        tv_availableMoney = (TextView) findViewById(R.id.tv_availableMoney);
        tv_availableMoney.setText(availableMoney);
        // 取消按钮点击事件
        imv_cancle.setOnClickListener(this);
        gridpassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.imv_cancle) {
            closeKeyBoard();
            dismiss();
        } else if (viewId == R.id.password) {
            showKeyBoard();
        }
    }

    /**
     * 开启软键盘
     */
    private void showKeyBoard() {
        InputMethodManager inputMgr = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * 关闭软键盘
     */
    private void closeKeyBoard() {
        InputMethodManager inputMgr = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
    }

    /**
     * 监听输入的密码
     */
    GridPasswordView.OnPasswordChangedListener passlistener = new GridPasswordView.OnPasswordChangedListener() {

        // 密码
        @Override
        public void onMaxLength(String psw) {
            // 获取密码
            passwordStr = psw;
        }

        // 密码长度
        @Override
        public void onChanged(String psw) {
            if (null != delegate) {
                delegate.inputCompleteWithString(psw);
            }
        }
    };


    public interface DialogInputCompleteDelegate {
        public void inputCompleteWithString(String value);
    }
}