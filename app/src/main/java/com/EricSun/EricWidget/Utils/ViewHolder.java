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

package com.EricSun.EricWidget.Utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 通用的ViewHolder
 * Created by Administrator on 2016/1/18 0018.
 */
public class ViewHolder {

    private View convertView;
    private SparseArray<View> views;

    private ViewHolder(Context context, ViewGroup parent, int layoutId) {
        this.views = new SparseArray<>();
        convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        convertView.setTag(this);
    }

    /**
     * 获取ViewHolder实例
     *
     * @param context     上下文
     * @param convertView item的根view
     * @param parent      父类容器
     * @param layoutId    item布局资源文件id
     * @return ViewHolder实例
     */
    public static ViewHolder getHolder(Context context, View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId);
        }
        return (ViewHolder) convertView.getTag();
    }

    /**
     * 获取View
     *
     * @param id  view的id
     * @param <T> View的实际类型
     * @return
     */
    public <T extends View> T get(int id) {
        View childView = views.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            views.put(id, childView);
        }
        return (T) childView;
    }

    /**
     * 获取item布局的根View
     *
     * @return
     */
    public View getConvertView() {
        return convertView;
    }
}