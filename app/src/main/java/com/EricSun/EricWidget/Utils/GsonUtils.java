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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GsonUtils {

    public static <T> T changeGsonToBean(Gson gson, Object o, Class<T> cls) {

        String dataStr = gson.toJson(o);

        return gson.fromJson(dataStr, cls);
    }

    public static <T> List<T> changeGsonBeanList(Gson gson, Object o, Class<T> cls) {

        List list = (List) o;

        List<T> retList = new ArrayList<T>();
        if (null == o)
            return retList;
        for (int i = 0; i < list.size(); i++) {
            retList.add(GsonUtils.changeGsonToBean(gson, list.get(i), cls));
        }
        return retList;

    }

}
