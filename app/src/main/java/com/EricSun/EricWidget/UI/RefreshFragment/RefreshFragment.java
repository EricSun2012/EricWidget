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

package com.EricSun.EricWidget.UI.RefreshFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.EricSun.EricWidget.Framework.Fragment.BaseFragment;
import com.EricSun.EricWidget.R;
import com.EricSun.EricWidget.Widget.ESTableView;
import com.EricSun.EricWidget.Widget.PullToRefreshTableView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by Eric on 16/9/7.
 */
public class RefreshFragment extends BaseFragment implements ESTableView.ESTableViewDelegate {

    private PullToRefreshTableView refreshView;
    private int count = 0;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        setTitles("刷新控件");
        setTitleTextColor(getResources().getColor(R.color.color_white));
        setTitleBarColor(getResources().getColor(R.color.color_mediumTurquoise));

        View view = inflater.inflate(R.layout.fragment_refresh, null);
        refreshView = (PullToRefreshTableView) view;
        refreshView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshView.getRefreshableView().delegate = this;

        refreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ESTableView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ESTableView> refreshView1) {
                setDataRequest(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ESTableView> refreshView1) {
                setDataRequest(false);

            }
        });
        return view;
    }

    private void setDataRequest(final boolean refresh) {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (refresh) {
                    count = 5;
                } else {
                    count += 2;
                }
                refreshView.getRefreshableView().refreshTableView();

                refreshView.onRefreshComplete();
            }
        }, 2000);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void processClick(View v) {

    }

    @Override
    public int getESTableViewItemCount(ESTableView tableView, int sectionPosition) {
        return count;
    }

    @Override
    public View getESTableViewItemView(ESTableView tableView, View convertView, int sectionPosition, int position, ViewGroup parent) {
        if (null == convertView) {
            convertView = new TextView(ct);
        }
        TextView title = (TextView) convertView;
        title.setText("index =" + (position + 1));
        return convertView;
    }

    @Override
    public int getESTableViewSectionCount(ESTableView tableView) {
        return 1;
    }

    @Override
    public View getESTableViewSectionView(ESTableView tableView, View convertView, int sectionPosition, ViewGroup parent) {
        if (null == convertView) {
            convertView = new View(ct);
        }
        return convertView;
    }

    @Override
    public void OnclickedSection(ESTableView tableView, int sectionPosition) {

    }

    @Override
    public void OnclickedItem(ESTableView tableView, int sectionPosition, int position) {

    }
}
