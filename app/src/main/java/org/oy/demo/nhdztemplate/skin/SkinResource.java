package org.oy.demo.nhdztemplate.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 皮肤的资源类
 * Created by Mro on 2017/4/4.
 */
public class SkinResource {
    private Resources mSkinResource;//获取资源
    private String packageName;// 包名

    public SkinResource(Context context, String skinPath) {

        Method method = null;
        try {
            Resources resources = context.getResources();
            AssetManager assetManager = AssetManager.class.newInstance();
            method = AssetManager.class.getMethod("addAssetPath", String.class);
            method.invoke(assetManager, Environment.getExternalStorageDirectory().getAbsolutePath() +
                    File.separator + skinPath);
            mSkinResource = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
            // 获取包名
            Log.i("=====>oy", "skinPath:" + skinPath);
            File file = new File(skinPath);
            Log.i("=====>oy", "file:" + file.exists());
            packageName = context.getPackageManager().getPackageArchiveInfo(skinPath,
                    PackageManager.GET_ACTIVITIES).applicationInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过名字获取Drawable
     */
    public Drawable getDrawableByName(String resName) {
        try {
            int resId = mSkinResource.getIdentifier(resName, "drawable", packageName);
            Log.i("======>oy", "resId:" + resId + "  resName:" + resName + "  packageName:" + packageName);
            return mSkinResource.getDrawable(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过名字获取Color
     */
    public ColorStateList getColorByName(String resName) {
        try {
            int colorId = mSkinResource.getIdentifier(resName, "color", packageName);
            return mSkinResource.getColorStateList(colorId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
