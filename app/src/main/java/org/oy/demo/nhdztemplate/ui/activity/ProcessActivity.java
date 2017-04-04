package org.oy.demo.nhdztemplate.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import org.oy.demo.nhdztemplate.R;
import org.oy.demo.nhdztemplate.UserAidl;
import org.oy.demo.nhdztemplate.service.MessageService;
import org.oy.demo.nhdztemplate.views.DefaultNavigationBar;

public class ProcessActivity extends BaseActivity {
    private UserAidl userAidl;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            userAidl = UserAidl.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void beforSetContentView() {

    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_process;
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("进程通信")
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
        startService(new Intent(this, MessageService.class));
        bindService(new Intent(this, MessageService.class), conn, Context.BIND_AUTO_CREATE);
        findViewById(R.id.name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Toast.makeText(ProcessActivity.this, userAidl.getUserName(), Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Toast.makeText(ProcessActivity.this, userAidl.getUserPwd(), Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void FinishLoadXml() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
