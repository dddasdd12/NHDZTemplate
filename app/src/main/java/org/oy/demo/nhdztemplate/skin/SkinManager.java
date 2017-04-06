package org.oy.demo.nhdztemplate.skin;

import android.app.Activity;
import android.content.Context;

import org.oy.demo.nhdztemplate.skin.attr.SkinView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 皮肤管理类
 * Created by Mro on 2017/4/4.
 */
public class SkinManager {
    private static SkinManager skinManager = null;
    private Context mContext;
    private SkinResource mSkinResource;
    private Map<Activity, List<SkinView>> mSkinViews = new HashMap<>();

    public static synchronized SkinManager getInstance() {
        if (skinManager == null) {
            skinManager = new SkinManager();
        }
        return skinManager;
    }

    public void init(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    /**
     * 加载更新
     */
    public int loadSkin(String path) {
        // 初始化资源
        mSkinResource = new SkinResource(mContext, path);
        // 改变皮肤
        Set<Activity> keys = mSkinViews.keySet();
        for (Activity key : keys) {
            List<SkinView> skinViews = mSkinViews.get(key);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }
        }
        return 0;
    }

    /**
     * 恢复默认
     */
    public int restoreDefualt() {
        return 0;
    }

    /**
     * 通过activity获取SkinView
     */
    public List<SkinView> getSkinViews(Activity activity) {
        return mSkinViews.get(activity);
    }

    public void register(Activity activity, List<SkinView> skinViews) {
        mSkinViews.put(activity, skinViews);
    }

    /**
     * 获取当前皮肤资源
     */
    public SkinResource getSkinResource() {
        return mSkinResource;
    }
}
