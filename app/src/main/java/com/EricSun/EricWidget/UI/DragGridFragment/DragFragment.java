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
        setTitles("网络进度");
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
