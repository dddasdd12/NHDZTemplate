package org.oy.chardemo.utilslibrary.permissionutils;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;

import java.util.List;

public class PermissionHelper {
    private Object object;
    private int requestCode = 0;
    private String[] requestPermissions;
    //以什么方式传参数 传什么参数

    //以什么样的方式接收

    /**
     * 直接传参数
     */
    public static void requestPermission(Activity activity, int requestCode, String[] permissions) {
        PermissionHelper.with(activity)
                .requestCode(requestCode)
                .requestPermission(permissions).request();
    }

    /**
     * 链式传参
     */
    public PermissionHelper(Object object) {
        this.object = object;
    }

    public static PermissionHelper with(Activity activity) {
        return new PermissionHelper(activity);
    }

    public static PermissionHelper with(Fragment fragment) {
        return new PermissionHelper(fragment);
    }

    /**
     * 接收成功表示标记码
     */
    public PermissionHelper requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    /**
     * 接收需要申请的权限
     */
    public PermissionHelper requestPermission(String... requestPermissions) {
        this.requestPermissions = requestPermissions;
        return this;
    }


    /***
     * 拼装参数完成后调用
     */
    public void request() {
        //判断当前版本是否为6.0以上
        if (!PermissionUtils.isOverMarshmallow()) {
            //直接调用权限功能
            PermissionUtils.executeSuccessMethod(object, requestCode);
        } else {
            //是否申请权限
            List<String> denicedPermission = PermissionUtils.getDeniedPermission(object, requestPermissions);
            if (denicedPermission.size() == 0) {//全部授予过
                PermissionUtils.executeSuccessMethod(object, requestCode);
            } else {
                //申请未允许的权限 结果在BaseActivity 中回调
                ActivityCompat.requestPermissions(PermissionUtils.getActivity(object),
                        denicedPermission.toArray(new String[denicedPermission.size()]), requestCode);
            }
        }
    }

    /***
     * 处理权限回调 BaseActivity 调用
     */
    public static void requestPermissionResult(Object object, int requestCode, String[] permissions) {
        List<String> denicedPermission = PermissionUtils.getDeniedPermission(object, permissions);
        //权限全部允许
        if (denicedPermission.size() == 0) {
            PermissionUtils.executeSuccessMethod(object, requestCode);
        } else {
            //不同意其中一些权限
            PermissionUtils.executeFailMethod(object, requestCode);
        }

    }
}
