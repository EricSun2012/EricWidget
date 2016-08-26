package com.EricSun.EricWidget.UI.PieViewFragment;

import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.EricSun.EricWidget.Framework.Fragment.BaseFragment;
import com.EricSun.EricWidget.R;
import com.EricSun.EricWidget.Widget.PieChartView;

/**
 * Created by Eric on 16/8/26.
 */
public class PieFragment extends BaseFragment {
    private PieChartView normalPie;
    private PieChartView smallPie;
    private PieChartView largePie;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_pieview, null);
        TextView title = (TextView) view.findViewById(R.id.txt_title);
        title.setText("饼图");
        normalPie = (PieChartView) view.findViewById(R.id.pieview_normal);
        smallPie = (PieChartView) view.findViewById(R.id.pieview_small);
        largePie = (PieChartView) view.findViewById(R.id.pieview_large);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        SparseIntArray dataRes = new SparseIntArray();
        dataRes.put(getResources().getColor(R.color.color_tomato), 28);
        dataRes.put(getResources().getColor(R.color.color_goldenrod), 132);
        dataRes.put(getResources().getColor(R.color.color_lavender), 56);
        dataRes.put(getResources().getColor(R.color.color_skyBlue), 75);
        dataRes.put(getResources().getColor(R.color.color_thistle), 37);

        normalPie.setData(dataRes, true);

        smallPie.setData(dataRes, false);

        largePie.setData(dataRes);
        largePie.beginLoad();
    }

    @Override
    protected void processClick(View v) {

    }
}
