package com.EricSun.EricWidget.UI.RefreshFragment;

import android.app.Activity;
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
