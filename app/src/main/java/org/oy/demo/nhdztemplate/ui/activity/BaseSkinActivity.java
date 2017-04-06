package org.oy.demo.nhdztemplate.ui.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import org.oy.demo.nhdztemplate.skin.SkinManager;
import org.oy.demo.nhdztemplate.skin.attr.SkinAttr;
import org.oy.demo.nhdztemplate.skin.attr.SkinView;
import org.oy.demo.nhdztemplate.skin.support.AppSkinCompatInflater;
import org.oy.demo.nhdztemplate.skin.support.SkinAttrSupport;

import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.utils.Log;

public abstract class BaseSkinActivity extends BaseActivity implements LayoutInflaterFactory {
    private AppSkinCompatInflater mAppSkinCompatInflater;
    private SkinView skinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory(layoutInflater, this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        // 创建View
        View view = createView(parent, name, context, attrs);
        // 解析src textcolor backgroud 自定义属性
        if (view != null) {
            List<SkinAttr> SkinAttrs = SkinAttrSupport.getSkinAttrs(context, attrs);
            skinView = new SkinView(view, SkinAttrs);
        }
        // 统一交给SkinManager处理
        managerSkinView(skinView);
        return view;
    }

    /**
     * 统一管理skinview
     */
    protected void managerSkinView(SkinView skinView) {
        List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(this);
        if (skinViews == null) {
            skinViews = new ArrayList<>();
            SkinManager.getInstance().register(this,skinViews);
        }
        skinViews.add(skinView);
    }


    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        final boolean isPre21 = Build.VERSION.SDK_INT < 21;

        if (mAppSkinCompatInflater == null) {
            mAppSkinCompatInflater = new AppSkinCompatInflater();
        }

        // We only want the View to inherit its context if we're running pre-v21
        final boolean inheritContext = isPre21 && shouldInheritContext((ViewParent) parent);

        return mAppSkinCompatInflater.createView(parent, name, context, attrs, inheritContext,
                isPre21, /* Only read android:theme pre-L (L+ handles this anyway) */
                true, /* Read read app:theme as a fallback at all times for legacy reasons */
                VectorEnabledTintResources.shouldBeUsed() /* Only tint wrap the context if enabled */
        );
    }

    /**
     * 系统方法
     */
    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = getWindow().getDecorView();
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }
}
