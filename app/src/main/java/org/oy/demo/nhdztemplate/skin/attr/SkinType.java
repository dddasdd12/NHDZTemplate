package org.oy.demo.nhdztemplate.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.oy.demo.nhdztemplate.skin.SkinManager;
import org.oy.demo.nhdztemplate.skin.SkinResource;

/**
 * Created by Mro on 2017/4/4.
 */
public enum SkinType {
    TEXT_COLOR("textColor") {
        @Override
        public void skin(View mView, String mResourceName) {
            SkinResource mSkinResource = getSkinResource();
            ColorStateList color = mSkinResource.getColorByName(mResourceName);
            if (color == null) {
                return;
            }
            TextView textView = (TextView) mView;
            textView.setTextColor(color);
        }
    }, BACKGROUND("background") {
        @Override
        public void skin(View mView, String mResourceName) {
            // 背景可能市图片 也可能市颜色
            SkinResource mSkinResource = getSkinResource();
            Drawable drawable = mSkinResource.getDrawableByName(mResourceName);
            View view =  mView;
            if (drawable != null) {
                view.setBackgroundDrawable(drawable);
                return;
            }
            ColorStateList color = mSkinResource.getColorByName(mResourceName);
            if (color != null) {
                mView.setBackgroundColor(color.getDefaultColor());
            }
        }
    }, SRC("src") {
        @Override
        public void skin(View mView, String mResourceName) {
            SkinResource mSkinResource = getSkinResource();
            Drawable drawable = mSkinResource.getDrawableByName(mResourceName);
            ImageView imageView = (ImageView) mView;
            if (drawable != null) {
                imageView.setBackgroundDrawable(drawable);
            }
        }
    };
    private String mResourceName;

    SkinType(String mResourceName) {
        this.mResourceName = mResourceName;
    }

    public abstract void skin(View mView, String mResourceName);

    public String getResourceName() {
        return mResourceName;
    }

    public SkinResource getSkinResource() {
        return SkinManager.getInstance().getSkinResource();
    }

}
