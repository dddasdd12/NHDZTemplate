package org.oy.demo.nhdztemplate.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;

import org.oy.chardemo.utilslibrary.indicator.IndicatorViewGroup;
import org.oy.demo.nhdztemplate.R;

/**
 * ViewPager 指示器
 * Created by Mro on 2017/3/31.
 */
public class TrackIndicatorView extends HorizontalScrollView implements ViewPager.OnPageChangeListener {
    // Adapter设计模式-Adapter
    private IndicotorAdapter mAdapter;
    // 动态添加条目容器
    private IndicatorViewGroup mIndicotorGroup;
    private int tabVisibleNums = 0;// 屏幕可见数量
    private int mItemWidth = 0;
    private ViewPager viewPager;
    // 解决抖动
    private boolean isExecuteScroll = false;
    private int mCurrentPosition = 0;

    public TrackIndicatorView(Context context) {
        this(context, null);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIndicotorGroup = new IndicatorViewGroup(context);
        addView(mIndicotorGroup);
        initAttributeSer(context, attrs);
    }

    private void initAttributeSer(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TrackIndicatorView);
        tabVisibleNums = typedArray.getInteger(R.styleable.TrackIndicatorView_tabVisibleNums, tabVisibleNums);
        typedArray.recycle();
    }

    /**
     * 设置适配器
     */
    public void setAdapter(IndicotorAdapter mAdapter) {
        Log.i("=======aaaa", "setAdapter");
        if (mAdapter == null) {
            new NullPointerException("兄弟 adapter空了!");
        }
        this.mAdapter = mAdapter;
        // 动态添加View 获取有多少条数据(item)
        int itemCount = mAdapter.getCount();
        for (int i = 0; i < itemCount; i++) {
            View itemView = mAdapter.getView(i, this);
            if (viewPager != null) {
                switchItemClick(itemView, i);
            }
            mIndicotorGroup.addItemView(itemView);
        }
        mAdapter.HightBright(mIndicotorGroup.getItemChildAt(0));

    }

    private void switchItemClick(View itemView, final int position) {
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(position, false);
                scrollCurrentIndicator(position);
                mIndicotorGroup.scrollTrackBottom(position);
            }

        });
    }

    /**
     * 设置适配器
     */
    public void setAdapter(IndicotorAdapter mAdapter, ViewPager viewPager) {
        this.viewPager = viewPager;
        if (viewPager == null) {
            new NullPointerException("兄弟 viewPager空了!");
        }
        setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(this);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i("=======aaaa", "onLayout");
        if (changed) {
            // item 宽度
            int width = getWidth();
            mItemWidth = getItemWidth();
            // 制定item的宽度
            for (int i = 0; i < mAdapter.getCount(); i++) {
                View view = mIndicotorGroup.getItemChildAt(i);
                view.getLayoutParams().width = mItemWidth;
            }
            mIndicotorGroup.addBottomTranckView(mAdapter.getBottomTranckView(), mItemWidth);

        }
    }

    /**
     * 获取Item的宽度
     */
    public int getItemWidth() {
        // 有没有指定宽度
        int parentWdith = getWidth();
        if (tabVisibleNums != 0) {
            return parentWdith / tabVisibleNums;
        }
        // 取最大值
        int itemWidth = 0;
        int maxWidth = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            int childWidth = mIndicotorGroup.getItemChildAt(i).getWidth();
            maxWidth = Math.max(maxWidth, childWidth);
        }
        itemWidth = maxWidth;
        int allViewWidth = mAdapter.getCount() * itemWidth;
        // 计算所有条目宽度相加是否大于整屏幕
        // 不足全屏宽度
        if (allViewWidth < parentWdith) {
            itemWidth = parentWdith / mAdapter.getCount();
        }
        return itemWidth;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 滚动时调用
        if (isExecuteScroll) {
            scrollCurrentIndicator(position, positionOffset);
            mIndicotorGroup.scrollTrackBottomView(position, positionOffset);
        }
        // 点击时不执行  防抖动
    }

    private void scrollCurrentIndicator(int position) {
        // 当前总长度
        float totalScroll = position * mItemWidth;
        // 左侧偏移
        int offsetScroll = (getWidth() - mItemWidth) / 2;
        // 最终滚动位置
        final int finalScroll = (int) (totalScroll - offsetScroll);
        smoothScrollTo(finalScroll, 0);

    }

    private void scrollCurrentIndicator(int position, float positionOffset) {
        // 当前总长度
        float totalScroll = (position + positionOffset) * mItemWidth;
        // 左侧偏移
        int offsetScroll = (getWidth() - mItemWidth) / 2;
        // 最终滚动位置
        final int finalScroll = (int) (totalScroll - offsetScroll);
        scrollTo(finalScroll, 0);

    }

    @Override
    public void onPageSelected(int position) {
        // 设置字体颜色
        mAdapter.RestBright(mIndicotorGroup.getItemChildAt(mCurrentPosition));
        mCurrentPosition = position;
        // 重置字体颜色
        mAdapter.HightBright(mIndicotorGroup.getItemChildAt(mCurrentPosition));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 1) {
            isExecuteScroll = true;
        }
        if (state == 0) {
            isExecuteScroll = false;
        }
    }

}
