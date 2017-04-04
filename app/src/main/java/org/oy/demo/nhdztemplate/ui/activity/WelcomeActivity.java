package org.oy.demo.nhdztemplate.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.oy.demo.nhdztemplate.R;

import java.lang.ref.WeakReference;
import java.util.Timer;

public class WelcomeActivity extends BaseActivity {
    private Handler handler;
    private Runnable runnable;
    private boolean isCheck = false;

    @Override
    protected void beforSetContentView() {

    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        ImageView img = (ImageView) findViewById(R.id.welcome_img);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        WeakReference<ImageView> weakReference = new WeakReference<>(img);
        Glide.with(this).load(R.drawable.welcome).into(weakReference.get());
        findViewById(R.id.welcome_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCheck = true;
                handler.removeCallbacks(runnable);
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void FinishLoadXml() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (!isCheck) {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
        handler.postDelayed(runnable, 4000);
    }
}
