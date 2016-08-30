package com.EricSun.EricWidget.Framework.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.EricSun.EricWidget.R;
import com.EricSun.EricWidget.Utils.SystemBarTintManager;
import com.lidroid.xutils.util.LogUtils;
import com.EricSun.EricWidget.Utils.DialogUtil;
import com.EricSun.EricWidget.Widget.CancelQueueToast;
import com.EricSun.EricWidget.Widget.CustomDialog;
import com.EricSun.EricWidget.Widget.CustomProgressDialog;
import com.EricSun.EricWidget.Widget.CustomToast;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpTaskHandler;


public abstract class BaseFragment extends Fragment implements OnClickListener, HttpCycleContext {

    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    public ViewGroup rootView;
    public ViewGroup contentView;//内容视图
    public ViewGroup actionBarView;//标题栏视图

    protected Context ct;
    protected CustomProgressDialog dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ct = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        createRootView(inflater);
        //获取主视图容器
        if (null != contentView) {
            contentView.addView(initView(inflater, container));
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }


    public View getRootView() {
        return rootView;
    }


    private void createRootView(LayoutInflater inflater) {
        rootView = (ViewGroup) inflater.inflate(R.layout.layout_main, null);
        actionBarView = (ViewGroup) rootView.findViewById(R.id.layout_title);

        contentView = (ViewGroup) rootView.findViewById(R.id.layout_content);

        rootView.setOnTouchListener(
                new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        InputMethodManager imm = (InputMethodManager) ct.getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (null != imm && null != ((Activity) ct).getCurrentFocus()) {
                            return imm.hideSoftInputFromWindow(((Activity) ct).getCurrentFocus()
                                    .getWindowToken(), 0);
                        }
                        return false;
                    }
                });
    }

    /**
     * set the title string
     *
     * @param title
     */
    public void setTitles(String title) {
        if (null != actionBarView) {
            TextView titleText = (TextView) actionBarView.findViewById(R.id.txt_title);
            titleText.setText(title);
        }
    }

    /**
     * set title textcolor
     *
     * @param color
     */
    public void setTitleTextColor(int color) {
        if (null != actionBarView) {
            TextView titleText = (TextView) actionBarView.findViewById(R.id.txt_title);
            titleText.setTextColor(color);
        }
    }

    public void setTitleBarColor(int color) {
        if (null != actionBarView) {
            actionBarView.setBackgroundColor(color);
        }
    }

    protected void closeKeyBoard() {
        View view = rootView;
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) ct.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null != imm && null != ((Activity) ct).getCurrentFocus()) {
                imm.hideSoftInputFromWindow(((Activity) ct).getCurrentFocus()
                        .getWindowToken(), 0);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HttpTaskHandler.getInstance().removeTask(HTTP_TASK_KEY);
    }


    protected abstract View initView(LayoutInflater inflater,
                                     ViewGroup container);

    public abstract void initData(Bundle savedInstanceState);

    protected abstract void processClick(View v);

    public void showToast(String msg) {
        showToast(msg, 0);
    }

    public void showToast(String msg, int time) {
        CustomToast customToast = new CustomToast(ct, msg, time);
        customToast.show();
    }

    //不重复显示的Toast
    public void myToast(String msg) {
        CancelQueueToast.showToast(ct, msg);
    }

    public void showProgressDialog(String content) {
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


    public void closeProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
        processClick(v);
    }

//    protected void loadData(HttpRequest.HttpMethod method, String url,
//                            RequestParams params, RequestCallBack<String> callback) {
//        HttpUtils http = new HttpUtils();
//        http.configCurrentHttpCacheExpiry(1000 * 10);
//        LogUtils.allowD = true;
//        if (params != null) {
//            if (params.getQueryStringParams() != null)
//                LogUtils.d(url + params.getQueryStringParams().toString());
//        } else {
//            params = new RequestParams();
//
//        }
//        params.addHeader("x-deviceid", app.getDeviceId());
//        params.addHeader("x-channel", app.getChannel());
//        if (!NetUtil.hasNetwork(ct)) {
//            // showToast("鍔犺浇澶辫触锛岃妫�煡缃戠粶锛�);
//            // callback.onFailure(new HttpException(), "鏃犵綉缁�);
//        } else {
//            http.send(method, url, params, callback);
//        }
//    }
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

}
