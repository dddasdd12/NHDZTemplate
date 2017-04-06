package org.oy.demo.nhdztemplate.skin.attr;

import android.view.View;

import java.util.List;

/**
 * Created by Mro on 2017/4/4.
 */
public class SkinView {
    private View mView;
    private List<SkinAttr> mAttrs;// 可能包含多个

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mView = view;
        this.mAttrs = skinAttrs;
    }

    public void skin() {
        for (SkinAttr attr : mAttrs) {
            attr.skin(mView);
        }
    }
}
