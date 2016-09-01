package com.EricSun.EricWidget.UI.RoundProgressFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.EricSun.EricWidget.Framework.Fragment.BaseFragment;
import com.EricSun.EricWidget.R;

/**
 * Created by Eric on 16/9/1.
 */
public class RoundFragment extends BaseFragment {


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_round_progress, null);

        setTitles("圆形进度条");
        setTitleTextColor(getResources().getColor(R.color.color_white));
        setTitleBarColor(getResources().getColor(R.color.color_mediumTurquoise));
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void processClick(View v) {

    }
}
