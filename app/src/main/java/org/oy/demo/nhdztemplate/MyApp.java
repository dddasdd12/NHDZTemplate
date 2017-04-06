package org.oy.demo.nhdztemplate;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.alipay.euler.andfix.patch.PatchManager;

import org.oy.chardemo.utilslibrary.exception.ExceptionCashHelper;
import org.oy.demo.nhdztemplate.skin.SkinManager;

/**
 * 全局监听
 * Created by Mro on 2017/3/8.
 */
public class MyApp extends MultiDexApplication {
    public static PatchManager patchManager;
    private PackageInfo packageInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        //设置全局异常 捕获
        ExceptionCashHelper.getInstance().init(this);
        patchManager = new PatchManager(this);
        SkinManager.getInstance().init(this);
        try {
            packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        patchManager.init(packageInfo.versionName);
        patchManager.loadPatch();
    }

    /**
     * 处理65536
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
