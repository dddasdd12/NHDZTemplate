package org.oy.demo.nhdztemplate.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.oy.chardemo.utilslibrary.adapter.ViewPageAdapter;
import org.oy.demo.nhdztemplate.R;
import org.oy.demo.nhdztemplate.indicator.IndicotorAdapter;
import org.oy.demo.nhdztemplate.indicator.TrackIndicatorView;
import org.oy.demo.nhdztemplate.ui.fragment.AmusementFragment;
import org.oy.demo.nhdztemplate.ui.fragment.FinanceFragment;
import org.oy.demo.nhdztemplate.ui.fragment.FunnyFragment;
import org.oy.demo.nhdztemplate.ui.fragment.HeadLinesFragment;
import org.oy.demo.nhdztemplate.ui.fragment.LocalFragment;
import org.oy.demo.nhdztemplate.ui.fragment.NewsFragment;
import org.oy.demo.nhdztemplate.ui.fragment.PictureFragment;
import org.oy.demo.nhdztemplate.ui.fragment.SportFragment;
import org.oy.demo.nhdztemplate.ui.fragment.TechnologyFragment;
import org.oy.demo.nhdztemplate.ui.fragment.VideoFragment;
import org.oy.demo.nhdztemplate.ui.fragment.WorldFragment;
import org.oy.demo.nhdztemplate.views.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class IndicatorActivity extends BaseActivity {
    private String[] datas = {"头条", "新闻", "视频", "娱乐", "体育", "财经", "科技", "搞笑", "图片", "地方", "社会"};
    private TrackIndicatorView indicotorView;
    private ViewPager viewPager;
    private List<Fragment> fragments;

    @Override
    protected void beforSetContentView() {
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_view_pager;
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("IndicatorView")
                .setLeftText("返回")
                .setLeftLisenter(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).builder();
    }

    @Override
    protected void initView() {
        indicotorView = (TrackIndicatorView) findViewById(R.id.indicotor);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        initFragment();
        initDatas();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HeadLinesFragment());
        fragments.add(new NewsFragment());
        fragments.add(new VideoFragment());
        fragments.add(new AmusementFragment());
        fragments.add(new SportFragment());
        fragments.add(new FinanceFragment());
        fragments.add(new TechnologyFragment());
        fragments.add(new FunnyFragment());
        fragments.add(new PictureFragment());
        fragments.add(new LocalFragment());
        fragments.add(new WorldFragment());
    }

    @Override
    protected void FinishLoadXml() {

    }

    private void initDatas() {
        IndicotorAdapter mAdapter = new IndicotorAdapter() {
            @Override
            public int getCount() {
                return datas.length;
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                TextView textView = new TextView(IndicatorActivity.this);
                textView.setTextSize(16);
                textView.setPadding(0, 20, 0, 20);
                textView.setGravity(Gravity.CENTER);
                textView.setText(datas[position]);
                textView.setTextColor(ContextCompat.getColor(IndicatorActivity.this, R.color.black));
                return textView;
            }


            @Override
            public void HightBright(View view) {
                TextView textView = (TextView) view;
                textView.setTextColor(ContextCompat.getColor(IndicatorActivity.this, R.color.colorAccent));

            }

            @Override
            public void RestBright(View view) {
                TextView textView = (TextView) view;
                textView.setTextColor(ContextCompat.getColor(IndicatorActivity.this, R.color.black));
            }

            @Override
            public View getBottomTranckView() {
                View view = new View(IndicatorActivity.this);
                view.setBackgroundColor(ContextCompat.getColor(IndicatorActivity.this, R.color.colorAccent));
                view.setLayoutParams(new ViewGroup.LayoutParams(88, 8));
                return view;
            }
        };
        viewPager.setAdapter(new ViewPageAdapter(getSupportFragmentManager(), fragments));
        viewPager.setCurrentItem(0);
        indicotorView.setAdapter(mAdapter, viewPager);
    }

}
