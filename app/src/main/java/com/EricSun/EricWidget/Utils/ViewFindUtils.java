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

import android.util.SparseArray;
import android.view.View;

@SuppressWarnings({ "unchecked" })
public class ViewFindUtils
{
	/**
	 * ViewHolder简洁写法,避免适配器中重复定义ViewHolder,减少代码量 用法:
	 * 
	 * <pre>
	 * if (convertView == null)
	 * {
	 * 	convertView = View.inflate(context, R.layout.ad_demo, null);
	 * }
	 * TextView tv_demo = ViewHolderUtils.get(convertView, R.id.tv_demo);
	 * ImageView iv_demo = ViewHolderUtils.get(convertView, R.id.iv_demo);
	 * </pre>
	 */
	public static <T extends View> T hold(View view, int id)
	{
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();

		if (viewHolder == null)
		{
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}

		View childView = viewHolder.get(id);

		if (childView == null)
		{
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}

		return (T) childView;
	}

	/**
	 * 替代findviewById方法
	 */
	public static <T extends View> T find(View view, int id)
	{
		return (T) view.findViewById(id);
	}
}
