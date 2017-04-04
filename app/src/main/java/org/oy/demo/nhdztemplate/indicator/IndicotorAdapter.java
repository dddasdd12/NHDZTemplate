package org.oy.demo.nhdztemplate.indicator;

import android.view.View;
import android.view.ViewGroup;

public abstract class IndicotorAdapter {
    //显示条数
    public abstract int getCount();

    //当前显示的view
    public abstract View getView(int position, ViewGroup parent);

    // 点亮字体颜色
    public void HightBright(View view) {

    }

    // 重置字体颜色
    public void RestBright(View view) {

    }

    // 添加底部的指示器
    public View getBottomTranckView() {
        return null;
    }

}
