package org.oy.demo.nhdztemplate.ui.fragment;

import android.app.Activity;
import android.util.Log;

import org.oy.demo.nhdztemplate.R;

/**
 * Created by Mro on 2017/3/31.
 */
public class AmusementFragment extends BaseFragment {
    private Activity activity;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_amusement;
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
        Log.i("====oy", "AmusementFragment  initData()");

    }

    /**
     * 页面加载完毕
     */
    @Override
    protected void loadFinish() {

    }
}
