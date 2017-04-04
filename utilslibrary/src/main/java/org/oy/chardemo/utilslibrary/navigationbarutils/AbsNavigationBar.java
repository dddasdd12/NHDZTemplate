package org.oy.chardemo.utilslibrary.navigationbarutils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * build 基类
 * Created by Mro on 2017/3/12.
 */
public class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INavigationBar {
    private P mParams;
    private View navigationView;

    public P getParams() {
        return mParams;
    }


    /**
     * 控件赋值文本
     */
    public void setText(int viewId, String str) {
        TextView tv = findViewById(viewId);
        if (!TextUtils.isEmpty(str)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(str);
        }
    }

    /**
     * 绑定控件监听
     */
    public void setOnClickLisenter(int viewId, View.OnClickListener listener) {
        findViewById(viewId).setOnClickListener(listener);
    }

    /**
     * 设置图片
     */
    public void setIcon(int imgViewId, int iconId) {
        ImageView img = findViewById(imgViewId);
        if (iconId != 0) {
            img.setVisibility(View.VISIBLE);
            img.setImageResource(iconId);
        }
    }

    /**
     * 初始化控件
     */
    public <T extends View> T findViewById(int viewId) {
        return (T) navigationView.findViewById(viewId);
    }


    /**
     * 初始化基类
     */
    public AbsNavigationBar(P mParams) {
        this.mParams = mParams;
        createAndBindView();
    }

    /**
     * 绑定和创建View
     */
    private void createAndBindView() {
        // 创建NavigationView
        if (mParams.viewGroup == null) {
            ViewGroup activityRoot = (ViewGroup) ((Activity) mParams.context)
                    .findViewById(android.R.id.content);
            mParams.viewGroup = (ViewGroup) activityRoot.getChildAt(0);
        }
        navigationView = LayoutInflater.from(mParams.context)
                .inflate(bindLayoutId(), mParams.viewGroup, false);
        // 添加NavigationView
        mParams.viewGroup.addView(navigationView, 0);
        aplayView();
    }

    @Override
    public int bindLayoutId() {
        return 0;
    }

    @Override
    public void aplayView() {

    }

    public abstract static class Builder {
        AbsNavigationParams p;

        public Builder(Context context, ViewGroup viewGroup) {
            p = new AbsNavigationParams(context, viewGroup);
        }

        public abstract AbsNavigationBar builder();

        public static class AbsNavigationParams {
            public Context context;
            public ViewGroup viewGroup;

            public AbsNavigationParams(Context context, ViewGroup viewGroup) {
                this.context = context;
                this.viewGroup = viewGroup;

            }

        }
    }
}
