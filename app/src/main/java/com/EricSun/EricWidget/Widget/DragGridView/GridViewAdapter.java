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

package com.EricSun.EricWidget.Widget.DragGridView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by xiaolin on 2015/1/24.
 */
public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> strList;
    private int hidePosition = AdapterView.INVALID_POSITION;

    public GridViewAdapter(Context context, List<String> strList) {
        this.context = context;
        this.strList = strList;
    }

    @Override
    public int getCount() {
        return strList.size();
    }

    @Override
    public String getItem(int position) {
        return strList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView == null) {
            view = new TextView(context);
        } else {
            view = (TextView) convertView;
        }

        //hide时隐藏Text
        if (position != hidePosition) {
            view.setText(strList.get(position));
        } else {
            view.setText("");
        }
        view.setId(position);

        return view;
    }

    public void hideView(int pos) {
        hidePosition = pos;
        notifyDataSetChanged();
    }

    public void showHideView() {
        hidePosition = AdapterView.INVALID_POSITION;
        notifyDataSetChanged();
    }

    public void removeView(int pos) {
        strList.remove(pos);
        notifyDataSetChanged();
    }

    //更新拖动时的gridView
    public void swapView(int draggedPos, int destPos) {
        //从前向后拖动，其他item依次前移
        if (draggedPos < destPos) {
            strList.add(destPos + 1, getItem(draggedPos));
            strList.remove(draggedPos);
        }
        //从后向前拖动，其他item依次后移
        else if (draggedPos > destPos) {
            strList.add(destPos, getItem(draggedPos));
            strList.remove(draggedPos + 1);
        }
        hidePosition = destPos;
        notifyDataSetChanged();
    }
}