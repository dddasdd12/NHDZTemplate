package org.oy.demo.nhdztemplate.ui.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.oy.demo.nhdztemplate.R;
import org.oy.demo.nhdztemplate.ui.fragment.PlayVedioFragment;
import org.oy.demo.nhdztemplate.views.DefaultNavigationBar;
import org.oy.chardemo.utilslibrary.windowutils.WindowUtils;

public class PlayVideoOnLineActivity extends BaseSkinActivity {
    private boolean isFirestLoad = false;
    private Configuration configuration;
    private LinearLayout.LayoutParams params;
    private android.support.v4.app.FragmentManager fm;
    private PlayVedioFragment fragment;
    private FrameLayout flayout;

    //  .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)  去除底部虚拟按键
    @Override
    protected void beforSetContentView() {
        fm = getSupportFragmentManager();
        fragment = new PlayVedioFragment();
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_playvideoonline;
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("视频播放")
                .setLeftText("返回")
                .setLeftLisenter(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PlayVideoOnLineActivity.this.finish();
                    }
                })
                .builder();
    }

    @Override
    protected void initView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    protected void FinishLoadXml() {
        if (!isFirestLoad) {
            isFirestLoad = true;
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            flayout = (FrameLayout) findViewById(R.id.playvideo_flayout);
            params.height = WindowUtils.getWindowHeith(getApplicationContext()) / 3;
            flayout.setLayoutParams(params);
            configuration = getResources().getConfiguration();
            fm.beginTransaction().replace(R.id.playvideo_flayout, fragment).commit();
        }
    }

    @Override
    public Object getSystemService(String name) {
        if (Context.AUDIO_SERVICE.equals(name)) {
            return getApplicationContext().getSystemService(name);
        }
        return super.getSystemService(name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setContentView(R.layout.view_null);
        System.gc();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // 全屏则缩小
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                finish();
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 切屏设置播放器大小
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        } else {
            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            params.height = WindowUtils.getWindowHeith(getApplicationContext()) / 3;
        }
        flayout.setLayoutParams(params);
        super.onConfigurationChanged(newConfig);
    }

}
