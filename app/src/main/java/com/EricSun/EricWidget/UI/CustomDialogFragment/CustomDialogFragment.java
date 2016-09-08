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

package com.EricSun.EricWidget.UI.CustomDialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.EricSun.EricWidget.Framework.Fragment.BaseFragment;
import com.EricSun.EricWidget.R;
import com.EricSun.EricWidget.Widget.CustomDialog;

/**
 * Created by Eric on 16/9/1.
 */
public class CustomDialogFragment extends BaseFragment {


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        setTitles("自定义Dialog");
        setTitleTextColor(getResources().getColor(R.color.color_white));
        setTitleBarColor(getResources().getColor(R.color.color_mediumTurquoise));
        View view = inflater.inflate(R.layout.fragment_customdialog, null);
        view.findViewById(R.id.bt_dialog).setOnClickListener(this);
        view.findViewById(R.id.bt_tips).setOnClickListener(this);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    private void showCustomDialog() {
        final CustomDialog dialog = new CustomDialog(ct);
        dialog.setCustomDialog("标题", "这个dialog", new String[]{"点我啊", "取消"}, new View.OnClickListener[]{new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("点到我了");
                dialog.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("cancel");
                dialog.dismiss();
            }
        }});
        dialog.show();
    }

    private void showSingleDialog() {
        final CustomDialog mDialog = new CustomDialog(ct);
        mDialog.setCustomDialog("提示", "单个按钮", new String[]{"点击"}, new View.OnClickListener[]{new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        }});
        mDialog.show();
    }

    @Override
    protected void processClick(View v) {
        int vId = v.getId();
        if (vId == R.id.bt_dialog) {
            showCustomDialog();
        } else if (vId == R.id.bt_tips) {
            showSingleDialog();
        }
    }
}
