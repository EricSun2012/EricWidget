package com.EricSun.EricWidget.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jiechic.library.android.widget.MultiStateView;
import com.EricSun.EricWidget.Framework.Activity.BaseActivity;
import com.EricSun.EricWidget.R;
import com.EricSun.EricWidget.Utils.AppManager;
import com.EricSun.EricWidget.Utils.ViewHolder;
import com.EricSun.EricWidget.Widget.ESTableView;

/**
 * Created by Eric on 16/8/25.
 */
public class MainActivity extends BaseActivity implements ESTableView.ESTableViewDelegate {
    private long exitTime;//连按两次返回键退出app间隔时间


    private ESTableView tableView;
    private MultiStateView stateView;

    String[] mData = new String[]{"轮播图", "饼状图", "自定义Dialog", "ActionSheet", "圆形进度条", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};


    @Override
    protected View loadView() {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.layout_main_activity, null);
        setRootViewId(R.id.rootView);

        tableView = (ESTableView) view.findViewById(R.id.tableView);
        stateView = (MultiStateView) view.findViewById(R.id.stateView);
        TextView title = (TextView) view.findViewById(R.id.txt_title);
        title.setText("控件模板");

        stateView.setState(MultiStateView.ContentState.LOADING);
        tableView.delegate = this;
        tableView.setAdapter();
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2400);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            stateView.setState(MultiStateView.ContentState.CONTENT);
                            tableView.refreshTableView();
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次 退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.getAppManager().finishAllActivity();
        }

    }

    @Override
    protected void processClick(View v) {

    }

    @Override
    public int getESTableViewItemCount(ESTableView tableView, int sectionPosition) {

        return mData.length;
    }

    @Override
    public View getESTableViewItemView(ESTableView tableView, View convertView, int sectionPosition, int position, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getHolder(ct, convertView, parent, R.layout.item_main);
        TextView contentView = holder.get(R.id.txt_content);

        contentView.setText(mData[position]);
        return holder.getConvertView();
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
        switch (position) {
            case 0: {
            }
            break;
            case 1: {
            }
            break;
            case 2: {
            }
            break;
            case 3: {
            }
            break;
            case 4: {
            }
            break;
            case 5: {
            }
            break;
            case 6: {
            }
            break;
        }
    }
}
