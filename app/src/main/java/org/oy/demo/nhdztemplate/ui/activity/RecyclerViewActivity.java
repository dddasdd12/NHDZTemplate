package org.oy.demo.nhdztemplate.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.oy.demo.nhdztemplate.R;
import org.oy.demo.nhdztemplate.views.DefaultNavigationBar;

public class RecyclerViewActivity extends BaseActivity {
    private boolean isload = false;
    private RecyclerView recyclerView;


    @Override
    protected void beforSetContentView() {

    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("RecyclerView动画")
                .setLeftText("返回")
                .setLeftLisenter(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RecyclerViewActivity.this.finish();
                    }
                }).builder();
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    }

    @Override
    protected void FinishLoadXml() {
        if (!isload) {
            isload = true;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
