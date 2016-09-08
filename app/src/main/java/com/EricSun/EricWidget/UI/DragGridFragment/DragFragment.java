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

package com.EricSun.EricWidget.UI.DragGridFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.EricSun.EricWidget.Framework.Fragment.BaseFragment;
import com.EricSun.EricWidget.R;
import com.EricSun.EricWidget.Widget.DragGridView.DragGridView;
import com.EricSun.EricWidget.Widget.DragGridView.GridViewAdapter;

import java.util.ArrayList;

/**
 * Created by Eric on 16/9/2.
 */
public class DragFragment extends BaseFragment {

    private DragGridView dragView;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        setTitles("可拖动view");
        setTitleTextColor(getResources().getColor(R.color.color_white));
        setTitleBarColor(getResources().getColor(R.color.color_mediumTurquoise));

        View view = inflater.inflate(R.layout.fragment_dragview, null);
        dragView = (DragGridView) view.findViewById(R.id.drag_grid_view);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ArrayList strList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            strList.add("Channel " + i);
        }

        GridViewAdapter adapter = new GridViewAdapter(ct, strList);
        dragView.setAdapter(adapter);
    }

    @Override
    protected void processClick(View v) {

    }
}
