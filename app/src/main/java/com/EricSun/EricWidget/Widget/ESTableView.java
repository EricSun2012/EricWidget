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

package com.EricSun.EricWidget.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.lang.ref.WeakReference;

/**
 * 仿ios的listview
 */
public class ESTableView extends ListView {

    public final static int SECTION = 0;
    public final static int ITEM = 1;
    public ESTableViewDelegate delegate;
    private WeakReference<ESTableView> listView;
    private BaseAdapter esAdapter = new BaseAdapter() {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (delegate == null) {
                return null;
            }
            int sectionCount = delegate
                    .getESTableViewSectionCount(listView.get());

            for (int i = 0; i < sectionCount; i++) {
                int itemSize = delegate
                        .getESTableViewItemCount(listView.get(), i);
                if (position == 0) {
                    View view = delegate.getESTableViewSectionView(
                            listView.get(), convertView, i, parent);
//                        view.setTag(SECTION);
                    return view;
                }
                position -= 1;
                if (position < itemSize) {
                    View view = delegate.getESTableViewItemView(listView.get(),
                            convertView, i, position, parent);
//                        view.setTag(ITEM);
                    return view;
                }
                position -= itemSize;
            }
            return null;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {

            if (delegate == null) {
                return 0;
            }
            int sectionCount = delegate
                    .getESTableViewSectionCount(listView.get());

            for (int i = 0; i < sectionCount; i++) {
                int itemSize = delegate
                        .getESTableViewItemCount(listView.get(), i);
                if (position == 0) {

                    return 0;
                }
                position -= 1;
                if (position < itemSize) {
                    return 1;
                }
                position -= itemSize;
            }
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getCount() {
            int viewSize = 0;
            if (delegate != null) {
                int sectionCount = delegate
                        .getESTableViewSectionCount(listView.get());
                viewSize += sectionCount;
                for (int i = 0; i < sectionCount; i++) {
                    viewSize += delegate.getESTableViewItemCount(listView.get(),
                            i);
                }
            }
            return viewSize;
        }
    };

    public ESTableView(Context context) {
        this(context, null);
    }

    public ESTableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ESTableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        listView = new WeakReference<ESTableView>(this);
        setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (delegate != null) {

                    int headerCount = listView.get().getHeaderViewsCount();
                    position -= headerCount;

                    int sectionCount = delegate
                            .getESTableViewSectionCount(listView.get());

                    for (int i = 0; i < sectionCount; i++) {
                        int itemSize = delegate.getESTableViewItemCount(
                                listView.get(), i);
                        if (position == 0) {

                            delegate.OnclickedSection(listView.get(), i);
                            return;
                        }
                        position -= 1;
                        if (position < itemSize) {
                            delegate.OnclickedItem(listView.get(), i, position);
                            return;
                        }
                        position -= itemSize;
                    }
                }
            }
        });
    }

    /**
     * 选择显示section
     *
     * @param sectionPosition
     */
    public void setCurrentSection(int sectionPosition) {
        if (delegate == null) {
            return;
        }
        int itemCount = 0;
        for (int i = 0; i < sectionPosition; i++) {
            int itemSize = delegate.getESTableViewItemCount(this, i);
            itemCount += itemSize + 1;
        }
        setSelection(itemCount);
    }

    public void setAdapter() {
        if (getAdapter() == null) {
            setAdapter(esAdapter);
        }
    }

    /**
     * 刷新tableView
     */
    public void refreshTableView() {


        if (esAdapter != null) {
            esAdapter.notifyDataSetChanged();
        }

    }

    public interface ESTableViewDelegate {

        /**
         * 设置item的个数
         *
         * @param tableView
         * @param sectionPosition
         * @return
         */
        abstract public int getESTableViewItemCount(ESTableView tableView,
                                                    int sectionPosition);

        /**
         * 设置item的view
         *
         * @param tableView
         * @param sectionPosition
         * @param position
         * @return
         */
        abstract public View getESTableViewItemView(ESTableView tableView,
                                                    View convertView, int sectionPosition, int position,
                                                    ViewGroup parent);

        /**
         * 设置section的个数
         *
         * @param tableView
         * @return
         */
        abstract public int getESTableViewSectionCount(ESTableView tableView);

        /**
         * 设置section的view
         *
         * @param tableView
         * @param sectionPosition
         * @return
         */
        abstract public View getESTableViewSectionView(ESTableView tableView,
                                                       View convertView, int sectionPosition, ViewGroup parent);

        /**
         * 点击section事件
         *
         * @param tableView
         * @param sectionPosition
         */
        abstract public void OnclickedSection(ESTableView tableView,
                                              int sectionPosition);

        /**
         * 点击item事件
         *
         * @param tableView
         * @param sectionPosition
         * @param position
         */
        abstract public void OnclickedItem(ESTableView tableView,
                                           int sectionPosition, int position);
    }

}
