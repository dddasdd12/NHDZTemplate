package org.oy.demo.nhdztemplate.ui.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.oy.chardemo.utilslibrary.permissionutils.PermissionHelper;

public abstract class BaseActivity extends FragmentActivity {

    public BaseActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforSetContentView();
        setContentView(setContentViewId());
        initTitle();
        initView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            FinishLoadXml();
        }
    }


    /**
     * 权限申请回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.requestPermissionResult(this, requestCode, permissions);
    }

    /**
     * setContentView前执行 比如：设置状态栏
     */
    protected void beforSetContentView() {
    }

    ;

    /**
     * 加载布局
     */
    protected abstract int setContentViewId();

    /**
     * 初始化头部 NavigationBar
     */
    protected abstract void initTitle();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * XML加载完成 比如：测量控件大小
     */
    protected void FinishLoadXml() {

    }


    /**
     * 获取网络状态
     */
    public boolean isNetWorker() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 获取网络类型 如：WIFI 2G 3G 4G
     */
    public int ConnectMode() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        int connectType = networkInfo.getType();
        /*if (connectType == ConnectivityManager.TYPE_WIFI) {
            return 1;
        } else {
            return 0;
        }*/
        return connectType;
    }


}
