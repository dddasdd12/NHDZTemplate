package org.oy.demo.nhdztemplate.ui.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.oy.demo.nhdztemplate.R;

/**
 * Created by Mro on 2017/3/31.
 */
public class NewsFragment extends BaseFragment {
    private Activity activity;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        if (activity == null) {
            activity = getActivity();
        }
    }

    /**
     * 初始化数据(网络请求数据)
     */
    @Override
    protected void initData() {
        Log.i("====oy", "NewsFragment  initData()");
    }

    /**
     * 页面加载完毕
     */
    @Override
    protected void loadFinish() {

    }
}
