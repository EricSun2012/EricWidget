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

package com.EricSun.EricWidget.UI.ActionSheetFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.EricSun.EricWidget.Framework.Fragment.BaseFragment;
import com.EricSun.EricWidget.R;
import com.EricSun.EricWidget.Widget.PSActionSheet;

/**
 * Created by Eric on 16/8/26.
 */
public class ActionsheetFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_actionsheet, null);

        setTitles("表单");
        setTitleTextColor(getResources().getColor(R.color.color_white));
        setTitleBarColor(getResources().getColor(R.color.color_mediumTurquoise));
        view.findViewById(R.id.bt_operation).setOnClickListener(this);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    private void showActionsheetView() {
        PSActionSheet.showSheet(ct, "卖萌标题党", "热键-要吗?",
                new PSActionSheet.OnActionSheetClickListener() {
                    @Override
                    public void onActionSheetClick() {
                        showToast("<响应要吗>你想要还是想要更多?");
                    }
                }, new String[]{"热键-没良心的", "热键-死鬼"},
                new PSActionSheet.OnActionSheetClickListener[]{
                        new PSActionSheet.OnActionSheetClickListener() {
                            @Override
                            public void onActionSheetClick() {
                                showToast("<响应没良心的>还不快点");
                            }
                        },
                        new PSActionSheet.OnActionSheetClickListener() {
                            @Override
                            public void onActionSheetClick() {
                                showToast("<响应死鬼>快去干活");
                            }
                        }
                });
    }


    @Override
    protected void processClick(View v) {
        int vId = v.getId();
        if (vId == R.id.bt_operation) {
            showActionsheetView();
        }
    }
}
