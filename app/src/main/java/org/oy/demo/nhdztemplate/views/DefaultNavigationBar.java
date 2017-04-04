package org.oy.demo.nhdztemplate.views;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.oy.chardemo.utilslibrary.navigationbarutils.AbsNavigationBar;
import org.oy.demo.nhdztemplate.R;

/**
 * NavigationBar基类
 * Created by Mro on 2017/3/12.
 */
public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationParams> {

    public DefaultNavigationBar(DefaultNavigationBar.Builder.DefaultNavigationParams mParams) {
        super(mParams);
    }


    @Override
    public int bindLayoutId() {
        return R.layout.actionbar_title;
    }

    /**
     * 绑定参数&监听
     */
    @Override
    public void aplayView() {
        super.aplayView();
        setText(R.id.actionbar_tv1, getParams().leftText);
        setText(R.id.actionbar_tv2, getParams().mTitle);
        setText(R.id.actionbar_tv3, getParams().rightText);
        setIcon(R.id.actionbar_img, getParams().iconId);
        setOnClickLisenter(R.id.actionbar_tv1, getParams().leftLisenter);
        setOnClickLisenter(R.id.actionbar_tv3, getParams().rightLisenter);
        setOnClickLisenter(R.id.actionbar_img, getParams().rightLisenter);
    }


    /**
     * 功能参数赋值
     */
    public static class Builder extends AbsNavigationBar.Builder {
        DefaultNavigationParams params;

        public Builder(Context context, ViewGroup viewGroup) {
            super(context, viewGroup);
            params = new DefaultNavigationParams(context, viewGroup);
        }

        public Builder(Context context) {
            super(context, null);
            params = new DefaultNavigationParams(context, null);
        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar navigationBar = new DefaultNavigationBar(params);
            return navigationBar;
        }

        // 放置所有效果
        public Builder setTitle(String title) {
            params.mTitle = title;
            return this;
        }

        public Builder setLeftText(String title) {
            params.leftText = title;
            return this;
        }

        public Builder setLefiForBack() {
            params.leftLisenter = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((Activity) params.context).finish();
                }
            };
            return this;
        }

        // 放置所有效果
        public Builder setRigthText(String text) {
            params.rightText = text;
            return this;
        }

        public Builder setRightIcon(int iconId) {
            params.iconId = iconId;
            return this;
        }

        public Builder setLeftLisenter(View.OnClickListener lisenter) {
            params.leftLisenter = lisenter;
            return this;
        }

        public Builder setRightLisenter(View.OnClickListener lisenter) {
            params.rightLisenter = lisenter;
            return this;
        }

        /**
         * 保存参数值
         */
        public static class DefaultNavigationParams extends
                AbsNavigationBar.Builder.AbsNavigationParams {
            public String mTitle;
            public String leftText;
            public String rightText;
            public int iconId;
            public View.OnClickListener leftLisenter = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((Activity) context).finish();
                }
            };
            public View.OnClickListener rightLisenter;

            // 设置所有效果
            DefaultNavigationParams(Context context, ViewGroup viewGroup) {
                super(context, viewGroup);
            }
        }
    }

}
