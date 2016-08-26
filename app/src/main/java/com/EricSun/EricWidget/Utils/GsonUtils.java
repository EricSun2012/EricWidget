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
