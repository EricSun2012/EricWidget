package com.EricSun.EricWidget.UI.BannerFragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.EricSun.EricWidget.Framework.Fragment.BaseFragment;
import com.EricSun.EricWidget.R;
import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;

/**
 * Created by Eric on 16/9/5.
 */
public class BannerFragment extends BaseFragment {

    private ViewPager bannerPager;
    private FixedIndicatorView bannerIndicator;
    private BannerComponent bannerComponent;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        setTitles("轮播图");
        setTitleTextColor(getResources().getColor(R.color.color_white));
        setTitleBarColor(getResources().getColor(R.color.color_mediumTurquoise));
        View view = inflater.inflate(R.layout.fragment_banner, null);
        bannerPager = (ViewPager) view.findViewById(R.id.viewPager_banner);
        bannerIndicator = (FixedIndicatorView) view.findViewById(R.id.banner_indicator);
//        bannerIndicator.setScrollBar(new ColorBar(ct, Color.BLUE, 2, ScrollBar.Gravity.CENTENT_BACKGROUND));
        bannerComponent = new BannerComponent(bannerIndicator, bannerPager, true);
        bannerComponent.setAdapter(adapter);
        bannerComponent.setScrollDuration(800);//MS
        bannerComponent.setAutoPlayTime(3000);//MS
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {
        super.onStart();
        bannerComponent.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        bannerComponent.stopAutoPlay();
    }

    @Override
    protected void processClick(View v) {

    }


    IndicatorViewPager.IndicatorViewPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {
        @Override
        public int getCount() {
            return 5;
        }

        @SuppressLint("NewApi")
        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (null == convertView) {
                convertView = LayoutInflater.from(ct).inflate(R.layout.view_banner_point,container,false);
            }

            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (null == convertView) {
                convertView = new TextView(ct);
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

            TextView contentText = (TextView) convertView;
            contentText.setText("index" + (position + 1));
            return convertView;
        }
    };
}
