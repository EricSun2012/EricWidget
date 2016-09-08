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


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * Created by Eric on 16/8/3.
 */
public class PermissionUtil {

    public static final int REQUEST_CODE = 1;
    private String[] mPermissions;
    private Object mHandler;


    public static void requestPermissions(Activity activity, String[] permissions) {
        requestPermissions(activity, REQUEST_CODE, permissions);
    }

    public static void requestPermissions(Fragment fragment, String[] permissions) {

    }

    @TargetApi(Build.VERSION_CODES.M)
    private static void requestPermissions(Object object, int requestCode, String[] permissions) {

        if (object instanceof Activity) {
            ((Activity) object).requestPermissions(permissions, requestCode);
//            ((Activity) object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
        } else if (object instanceof Fragment) {
//            ((Fragment) object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
        } else {
            throw new IllegalArgumentException(object.getClass().getName() + " is not supported");
        }

    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        System.out.println("PermissionUtil onRequestPermissionsResult involk");
    }


}
