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
import android.view.MotionEvent;
import android.widget.ListView;

public class TouchListView extends ListView {

    // 滑动距离及坐标 
    private float xDistance, yDistance, xLast, yLast; 
    public TouchListView(Context context){
    	super(context);
    }
  
    public TouchListView(Context context, AttributeSet attrs) {
        super(context, attrs); 
    } 
  
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) { 
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f; 
                xLast = ev.getX(); 
                yLast = ev.getY(); 
                break; 
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX(); 
                final float curY = ev.getY(); 
                  
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX; 
                yLast = curY; 
                  
                if(xDistance > yDistance){ 
                    return false; 
                }   
        } 
  
        return super.onInterceptTouchEvent(ev); 
    } 

}
