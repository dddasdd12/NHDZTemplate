package org.oy.demo.nhdztemplate.ui.fragment;

import android.app.Activity;
import android.util.Log;

import org.oy.demo.nhdztemplate.R;

/**
 * Created by Mro on 2017/3/31.
 */
public class PictureFragment extends BaseFragment {
    private Activity activity;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_picture;
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
        Log.i("====oy", "PictureFragment  initData()");
    }

    /**
     * 页面加载完毕
     */
    @Override
    protected void loadFinish() {

    }
}
