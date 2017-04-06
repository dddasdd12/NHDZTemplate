package org.oy.demo.nhdztemplate.ui.activity;

import android.os.Environment;
import android.view.View;
import android.widget.Button;

import org.oy.demo.nhdztemplate.R;
import org.oy.demo.nhdztemplate.skin.SkinManager;
import org.oy.demo.nhdztemplate.views.DefaultNavigationBar;

import java.io.File;

public class UpdateSkinActivity extends BaseSkinActivity implements View.OnClickListener {
    private Button bt1, bt2;
    private String skinPath = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "skin1.apk";

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
        bt2 = (Button) findViewById(R.id.skin_bt2);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skin_bt1:
                SkinManager.getInstance().loadSkin(skinPath);
                break;
            case R.id.skin_bt2:
                SkinManager.getInstance().restoreDefualt();
                break;
        }
    }
}
