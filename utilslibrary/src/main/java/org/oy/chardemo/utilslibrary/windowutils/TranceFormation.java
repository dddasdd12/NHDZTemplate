package org.oy.chardemo.utilslibrary.windowutils;

import android.content.Context;

/**
 * Created by Mro on 2017/3/31.
 */
public class TranceFormation {
    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
