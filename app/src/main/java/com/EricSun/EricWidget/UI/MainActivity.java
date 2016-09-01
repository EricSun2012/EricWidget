package com.EricSun.EricWidget.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.EricSun.EricWidget.UI.ActionSheetFragment.ActionsheetFragment;
import com.EricSun.EricWidget.UI.CustomDialogFragment.CustomDialogFragment;
import com.EricSun.EricWidget.UI.PieViewFragment.PieFragment;
import com.EricSun.EricWidget.UI.RoundProgressFragment.RoundFragment;
import com.EricSun.EricWidget.Utils.ViewUtils;
import com.jiechic.library.android.widget.MultiStateView;
import com.EricSun.EricWidget.Framework.Activity.BaseActivity;
import com.EricSun.EricWidget.R;
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

        tableView = (ESTableView) view.findViewById(R.id.tableView);
        stateView = (MultiStateView) view.findViewById(R.id.stateView);
        setTitles("控件模板");
        setTitleTextColor(getResources().getColor(R.color.color_white));


        setStatusBarAndTitleBarColor(getResources().getColor(R.color.color_mediumTurquoise));
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


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(getApplicationContext(), "再按一次 退出程序",
//                        Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                AppManager.getAppManager().finishAllActivity();
//                System.exit(0);
//            }
//            return true;
//
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }

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
            case 0: {//轮播图
            }
            break;
            case 1: {//饼状图
                PieFragment mFragment = new PieFragment();
                addFragment(mFragment, "PieFragment", true);
            }
            break;
            case 2: {//自定义Dialog
                CustomDialogFragment mFragment = new CustomDialogFragment();
                addFragment(mFragment, "CustomDialogFragment", true);
            }
            break;
            case 3: {//ActionSheet
                ActionsheetFragment mFragment = new ActionsheetFragment();
                addFragment(mFragment, "ActionsheetFragment", true);


            }
            break;
            case 4: {//圆形进度条
                RoundFragment mFragment = new RoundFragment();
                addFragment(mFragment, "RoundFragment", true);

            }
            break;
            case 5: {//
            }
            break;
            case 6: {//
            }
            break;
        }
    }
}
