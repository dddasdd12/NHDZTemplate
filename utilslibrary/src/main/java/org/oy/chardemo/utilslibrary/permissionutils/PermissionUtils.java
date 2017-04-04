package org.oy.chardemo.utilslibrary.permissionutils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class PermissionUtils {
    public PermissionUtils() {
        throw new UnsupportedOperationException("不能实例化");
    }

    /**
     * 手机版本是否是6.0以上
     */
    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 查找需要调用的方法
     */
    public static void executeSuccessMethod(Object object, int requestCode) {
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            PermissionSuccesd permissionSuccess = method.getAnnotation(PermissionSuccesd.class);
            if (permissionSuccess != null) {
                int methodCode = permissionSuccess.requestCode();
                if (methodCode == requestCode) {
                    executeMethod(object, method);
                }
            }

        }
    }

    /**
     * 执行失败的方法
     */
    public static void executeFailMethod(Object object, int requestCode) {
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            PermissionFail permissionFail = method.getAnnotation(PermissionFail.class);
            if (permissionFail != null) {
                int methodCode = permissionFail.requestCode();
                if (methodCode == requestCode) {
                    executeMethod(object, method);
                }
            }

        }
    }

    /**
     * 调用方法
     */
    private static void executeMethod(Object object, Method method) {
        try {
            //允许执行私有方法
            method.setAccessible(true);
            //执行方法
            method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 判断是否已申请过权限
     */
    public static List<String> getDeniedPermission(Object object, String[] requestPermissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String questPermission : requestPermissions) {
            if (ContextCompat.checkSelfPermission((getActivity(object)),
                    questPermission) == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(questPermission);
            }
        }
        return deniedPermissions;
    }

    /**
     * 获取上下文
     */
    public static Activity getActivity(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        }
        if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        }
        return null;
    }

}
