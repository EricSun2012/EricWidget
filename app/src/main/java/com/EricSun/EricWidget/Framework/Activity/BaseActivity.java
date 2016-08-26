package com.EricSun.EricWidget.Framework.Activity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.EricSun.EricWidget.R;
import com.EricSun.EricWidget.Utils.AppManager;
import com.EricSun.EricWidget.Utils.DialogUtil;
import com.EricSun.EricWidget.Widget.CancelQueueToast;
import com.EricSun.EricWidget.Widget.CustomDialog;
import com.EricSun.EricWidget.Widget.CustomProgressDialog;
import com.EricSun.EricWidget.Widget.CustomToast;

import java.util.List;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpTaskHandler;
import de.greenrobot.event.EventBus;

public abstract class BaseActivity extends FragmentActivity implements HttpCycleContext,
        OnClickListener {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();

    public ViewGroup rootView;//界面的根视图容器
    protected Context ct;
    protected CustomProgressDialog dialog;
    private View tipsView;//提示信息容器
    private boolean isActive;
    private boolean stopStatus;
    private int rootViewId;
    private boolean nojump;
    private boolean backHome;//是否可以跳转

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppManager.getAppManager().addActivity(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xA6808080);
        }

        ct = this;
        nojump = false;
        //获取主视图容器

        rootView = (ViewGroup) loadView();
        rootView.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        InputMethodManager imm = (InputMethodManager) ct.getSystemService(INPUT_METHOD_SERVICE);
                        if (null != imm && null != getCurrentFocus()) {
                            return imm.hideSoftInputFromWindow(getCurrentFocus()
                                    .getWindowToken(), 0);
                        }
                        return false;
                    }
                });
        setContentView(rootView);
        //根据数据填充已搭建的界面
        initData(savedInstanceState);
    }

    protected void dissmissTipsView(ViewGroup parentView) {
        if (null != tipsView) {
            parentView.removeView(tipsView);
        }
        parentView.invalidate();
    }

    protected void showTipsView(View tipsView, ViewGroup parentView) {

        this.tipsView = tipsView;
        if (null != parentView) {
            tipsView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            parentView.addView(tipsView);
        } else {
            rootView.addView(tipsView);
        }
    }


    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    @Override
    protected void onStart() {
        super.onStart();
        backHome = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        backHome = false;
//        if (!nojump && BaseApplication.getApp().getUser() != null &&
//                BaseApplication.getApp().getUser().getUid() > 0) {
//            if (stopStatus == true && isActive == false) {
//                boolean switcher = BaseApplication.getApp().getSetting().isGestrueSwitch();
//                if (switcher == true) {
//                    isActive = true;// app 浠庡悗鍙板敜閱掞紝杩涘叆鍓嶅彴
//                    stopStatus = true;
//                    Intent intent = new Intent(this, LockActivity.class);
//                    startActivity(intent);
//                }
//            }
//        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        backHome = true;

        super.onSaveInstanceState(outState);
    }

    //字体大小不跟随系统设置字体大小改变
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
//        config.setToDefaults();
        config.fontScale = 1.0f;
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpTaskHandler.getInstance().removeTask(HTTP_TASK_KEY);
        AppManager.getAppManager().finishActivity(this);
    }

	/*
     * 鍒ゆ柇鏄惁閿佸睆
	 */

    public void setNoJump(boolean flag) {
        nojump = flag;
    }

    @Override
    protected void onStop() {
        super.onStop();

//        if (!nojump && BaseApplication.getApp().getUser() != null &&
//                BaseApplication.getApp().getUser().getUid() > 0) {
//            if (!isAppOnForeground() ||
//                    isScreenLocked()) {
//                stopStatus = true;
//                isActive = false;// 璁板綍褰撳墠宸茬粡杩涘叆鍚庡彴
//            }
//        }

    }

    public boolean isScreenLocked() {

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();

        if (isScreenOn)
            return false;
        else
            return true;
    }

    /**
     * app是否在后台
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    protected void showToast(String msg) {
        showToast(msg, 0);
    }

    protected void showToast(String msg, int time) {
        CustomToast customToast = new CustomToast(ct, msg, time);
        customToast.show();
    }

    //不重复显示的Toast
    public void myToast(String msg) {
        CancelQueueToast.showToast(ct, msg);
    }

    protected void showProgressDialog(String content) {
        if (dialog == null && ct != null) {
            dialog = (CustomProgressDialog) DialogUtil.createProgressDialog(ct,
                    content);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface mDialog) {
                    dialog = null;
                }
            });
        }
        dialog.show();
    }

    protected void closeProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }


    protected abstract View loadView();

    public abstract void initData(Bundle savedInstanceState);

    protected abstract void processClick(View v);

    /**
     * 拨打电话dialog
     */
    private CustomDialog myDialog;

    public void showPhoneDialog(final String phoneNumber) {
        myDialog = new CustomDialog(ct);
        myDialog.setTitle(phoneNumber);
        // 确定按钮
        myDialog.setPositiveTitle("呼叫");
        myDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myDialog != null) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri
                            .parse("tel:" + phoneNumber));
                    if (ActivityCompat.checkSelfPermission(ct, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    ct.startActivity(intent);

                    myDialog.dismiss();
                    myDialog = null;
                }
            }
        });
        // 取消按钮
        myDialog.setOnNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myDialog != null) {
                    myDialog.dismiss();
                    myDialog = null;
                }
            }
        });
        myDialog.show();
    }

    /**
     * 保存fragment的source id
     *
     * @param rootViewID
     */
    public void setRootViewId(int rootViewID) {
        this.rootViewId = rootViewID;
    }

    private int getRootViewId() {
        if (rootViewId < 0) {
            throw new IllegalArgumentException("You should fill rootViewId before,now the rootViewId is less than Zero");
        }
        return rootViewId;
    }

    protected void closeKeyBoard() {
        View view = findViewById(rootViewId);
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) ct
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private Fragment mFragment;

    public void switchContent(Fragment from, Fragment to) {
        switchContent(from, to, false);
    }

    public void switchContent(Fragment from, Fragment to, boolean animal) {
        if (mFragment != to) {
            mFragment = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (animal) {
                transaction.setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(getRootViewId(), to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    public void switchContent(Fragment fragment, boolean animal) {
        if (mFragment != fragment) {
            mFragment = fragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (animal) {
                transaction.setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
            transaction.replace(getRootViewId(), fragment) // 替换Fragment，实现切换
                    .commit();
        }
    }



    /**
     * 替换fragment
     *
     * @param fragment
     * @param tag
     * @param isBack
     */
    public void replaceFragment(Fragment fragment, String tag, boolean isBack) {
        closeKeyBoard();
        if (backHome) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();


        if (true == isBack) {
            transaction.setCustomAnimations(R.anim.slide_in_left,
                    R.anim.slide_out_right, R.anim.slide_in_left,
                    R.anim.slide_out_right);
            transaction.addToBackStack(tag);
        } else {
            transaction.setCustomAnimations(R.anim.slide_in_left,
                    0, 0, 0);
        }
        transaction.replace(rootViewId, fragment, tag);
        transaction.commit();
    }

    /**
     * 添加fragment
     *
     * @param fragment
     * @param tag
     * @param isBack
     */
    public void addFragment(Fragment fragment, String tag, boolean isBack) {
        closeKeyBoard();
        if (backHome) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();


        // transaction.setCustomAnimations(R.anim.slide_in_left,
        // FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        if (true == isBack) {
//            transaction.setCustomAnimations(R.anim.slide_in_left,
//                    R.anim.slide_out_right, R.anim.slide_in_left,
//                    R.anim.slide_out_right);
            transaction.setCustomAnimations(R.anim.slide_in_left,
                    0, 0, R.anim.slide_out_right);
            transaction.addToBackStack(tag);
        } else {
            transaction.setCustomAnimations(R.anim.slide_in_left,
                    0, 0, 0);
        }
        transaction.add(rootViewId, fragment, tag);
        transaction.commit();
    }

    /**
     * 清空fragment栈
     */
    public void removeAllFragment() {
        closeKeyBoard();
        if (backHome) {
            return;
        }
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStackImmediate(null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * 移除fragment
     *
     * @param fragment
     */
    public void removeFragment(Fragment fragment) {
        closeKeyBoard();
        if (backHome) {
            return;
        }
        // FragmentTransaction transaction = getSupportFragmentManager()
        // .beginTransaction();
        getSupportFragmentManager().popBackStackImmediate(null, 0);
        // transaction.setCustomAnimations(R.anim.slide_in_left,
        // R.anim.slide_out_right, R.anim.slide_in_left,
        // R.anim.slide_out_right);
        // transaction.remove(fragment);
        // transaction.commit();
    }

    //小数点后只允许输入两位
    public void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "";
                }

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // TODO Auto-generated method stub
            }

        });

    }

    //注册
    protected void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    //取消注册
    protected void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    //发送事件
    protected void postEvent(Object event) {
        EventBus.getDefault().post(event);
    }

    //接收事件
    public void onEvent(Object event) {
    }
    /**
     * 点击除EditText的空白处键盘消失
     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (isShouldHideInput(v, ev)) {
//
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm != null) {
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//            }
//            return super.dispatchTouchEvent(ev);
//        }
//        // 必不可少，否则所有的组件都不会有TouchEvent了
//        if (getWindow().superDispatchTouchEvent(ev)) {
//            return true;
//        }
//        return onTouchEvent(ev);
//    }
//
//    public boolean isShouldHideInput(View v, MotionEvent event) {
//        if (v != null && (v instanceof EditText)) {
//            int[] leftTop = {0, 0};
//            //获取输入框当前的location位置
//            v.getLocationInWindow(leftTop);
//            int left = leftTop[0];
//            int top = leftTop[1];
//            int bottom = top + v.getHeight();
//            int right = left + v.getWidth();
//            if (event.getX() > left && event.getX() < right
//                    && event.getY() > top && event.getY() < bottom) {
//                // 点击的是输入框区域，保留点击EditText的事件
//                return false;
//            } else {
//                return true;
//            }
//        }
//        return false;
//    }

}

/*
    protected void loadData(HttpRequest.HttpMethod method, String url,
                            RequestParams params, RequestCallBack<String> callback) {

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0);

        LogUtils.allowD = true;
        if (params != null) {
            if (params.getQueryStringParams() != null)
                LogUtils.d(url + "?" + params.getQueryStringParams().toString());
        } else {
            params = new RequestParams();

        }
        params.addHeader("x-deviceid", app.getDeviceId());
        params.addHeader("x-channel", app.getChannel());

        if (!NetUtil.hasNetwork(ct)) {
//			showToast("鍔犺浇澶辫触锛岃妫�煡缃戠粶锛�);
        } else {
            LogUtils.d(url);
            http.send(method, url, params, callback);
        }
    }*/