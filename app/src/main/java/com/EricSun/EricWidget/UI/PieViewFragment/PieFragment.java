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

        setTitles("饼图");
        setTitleTextColor(getResources().getColor(R.color.color_white));
        setTitleBarColor(getResources().getColor(R.color.color_mediumTurquoise));

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
