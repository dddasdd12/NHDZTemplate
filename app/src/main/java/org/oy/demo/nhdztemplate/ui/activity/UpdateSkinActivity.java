package org.oy.demo.nhdztemplate.ui.activity;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import org.oy.demo.nhdztemplate.R;
import org.oy.demo.nhdztemplate.views.DefaultNavigationBar;

import java.io.File;
import java.lang.reflect.Method;

public class UpdateSkinActivity extends BaseActivity implements View.OnClickListener {
    private Button bt1;
    private String skinName = "skin1.skin";

    @Override
    protected int setContentViewId() {
        return R.layout.activity_update_skin;
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("插件换肤")
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
        bt1 = (Button) findViewById(R.id.skin_bt1);
        bt1.setOnClickListener(this);
        initManager();
    }

    private int initManager() {
        Resources superRes = getResources();
        int id = 0;
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getMethod("addAssetPath", String.class);
            method.invoke(assetManager, Environment.getExternalStorageDirectory().getAbsolutePath() +
                    File.separator + skinName);
            Resources resources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
            id = resources.getIdentifier("", "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skin_bt1:
                setContentView(initManager());
                break;
        }
    }
}
