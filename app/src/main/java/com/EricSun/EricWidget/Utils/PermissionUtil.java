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
