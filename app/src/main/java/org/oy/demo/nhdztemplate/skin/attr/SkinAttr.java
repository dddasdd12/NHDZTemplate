package org.oy.demo.nhdztemplate.skin.attr;

import android.view.View;

/**
 * Created by Mro on 2017/4/4.
 */
public class SkinAttr {
    private String mResourceName;
    private SkinType mType;

    public SkinAttr(String resourceName, SkinType skinType) {
        this.mResourceName = resourceName;
        this.mType = skinType;
    }


    public void skin(View mView) {
        mType.skin(mView, mResourceName);
    }
}
