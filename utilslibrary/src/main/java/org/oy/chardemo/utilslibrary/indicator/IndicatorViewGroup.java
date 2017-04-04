package org.oy.chardemo.utilslibrary.indicator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by Mro on 2017/3/31.
 */
public class IndicatorViewGroup extends FrameLayout {
    // 动态添加条目容器
    private LinearLayout mIndicotorGroup;
    private View mBottomTrackView;
    private int width;// 条目宽度
    private FrameLayout.LayoutParams params;
    private int bottonTrackLeftMargin;

    public IndicatorViewGroup(Context context) {
        this(context, null);
    }

    public IndicatorViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIndicotorGroup = new LinearLayout(context);
        addView(mIndicotorGroup);
    }

    public void addItemView(View itemView) {
        mIndicotorGroup.addView(itemView);
    }

    public View getItemChildAt(int position) {
        return mIndicotorGroup.getChildAt(position);
    }


    /**
     * 添加底部跟踪指示器
     */
    public void addBottomTranckView(View bottomTranckView, int width) {
        if (bottomTranckView == null) {
            return;
        }
        this.width = width;
        this.mBottomTrackView = bottomTranckView;
        // 添加底部跟踪view
        addView(mBottomTrackView);
        // 设置到指示器底部
        params = (LayoutParams) mBottomTrackView.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        int trackWidth = params.width;
        if (params.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            trackWidth = width;
        }
        if (trackWidth > width) {
            trackWidth = width;
        }
        // 设置宽度
        params.width = trackWidth;
        bottonTrackLeftMargin = (width - trackWidth) / 2;
        params.leftMargin = bottonTrackLeftMargin;
    }

    /**
     * 底部红线滚动
     */
    public void scrollTrackBottomView(int position, float positionOffset) {
        int leftMargin = (int) ((position + positionOffset) * width);
        params.leftMargin = leftMargin + bottonTrackLeftMargin;
        mBottomTrackView.setLayoutParams(params);
    }

    public void scrollTrackBottom(int position) {
        if (mBottomTrackView == null) {
            return;
        }
        int leftMargin = position * width + bottonTrackLeftMargin;
        int currentLeftMargin = params.leftMargin;
        int distance = leftMargin - currentLeftMargin;
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(currentLeftMargin,
                leftMargin).setDuration((long) (Math.abs(distance) * 0.2f));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // 不断的回调此方法 不断的设置leftmargin
                float currentLeftMargin = (float) valueAnimator.getAnimatedValue();
                params.leftMargin = (int) currentLeftMargin;
                mBottomTrackView.setLayoutParams(params);
            }
        });
        // 差值器 然底部线到达目标指示器处越来越慢
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.start();
    }
}
