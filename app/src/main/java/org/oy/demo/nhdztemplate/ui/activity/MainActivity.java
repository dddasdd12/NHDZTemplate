package org.oy.demo.nhdztemplate.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.oy.chardemo.utilslibrary.exception.ExceptionCashHelper;
import org.oy.chardemo.utilslibrary.permissionutils.PermissionFail;
import org.oy.chardemo.utilslibrary.permissionutils.PermissionHelper;
import org.oy.chardemo.utilslibrary.permissionutils.PermissionSuccesd;
import org.oy.demo.nhdztemplate.MyApp;
import org.oy.demo.nhdztemplate.R;
import org.oy.demo.nhdztemplate.views.AlertDialog;
import org.oy.demo.nhdztemplate.views.DefaultNavigationBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private final int requestCode = 200;// 通话权限回调码
    private final int requestCameraCode = 100;// 相册权限回调码


    private Bitmap bitmap;
    private EditText edit;
    private WeakReference<Bitmap> weakReference;

    private Button bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt10;

    @Override
    protected void beforSetContentView() {
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("主页")
                .setLeftText("返回")
                .setRigthText("分享")
                .setRightLisenter(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "分享", Toast.LENGTH_LONG).show();
                    }
                })
                .builder();
//        findViewById(R.id.main_layout).setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void initView() {
        getExceptionFile();
        getWindow().setBackgroundDrawable(null);
        bt1 = (Button) findViewById(R.id.main_bt1);
        bt2 = (Button) findViewById(R.id.main_bt2);
        bt3 = (Button) findViewById(R.id.main_bt3);
        bt4 = (Button) findViewById(R.id.main_bt4);
        bt5 = (Button) findViewById(R.id.main_bt5);
        bt6 = (Button) findViewById(R.id.main_bt6);
        bt7 = (Button) findViewById(R.id.main_bt7);
        bt8 = (Button) findViewById(R.id.main_bt8);
        bt9 = (Button) findViewById(R.id.main_bt9);
        bt10 = (Button) findViewById(R.id.main_bt10);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
        bt9.setOnClickListener(this);
        bt10.setOnClickListener(this);

    }

    @Override
    protected void FinishLoadXml() {

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.main_bt1:
                PermissionHelper.with(MainActivity.this)
                        .requestCode(requestCode)
                        .requestPermission(Manifest.permission.CALL_PHONE).request();
                break;
            case R.id.main_bt2:
                PermissionHelper.with(MainActivity.this)
                        .requestCode(requestCameraCode)
                        .requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .request();
                break;
            case R.id.main_bt3:
                final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialog)
                        .setContentView(R.layout.dialog_item)
                        .setText(R.id.dialog_item_bt, "GO")
                        .fullWith(true).fromBottom(true)
                        .show();
                dialog.setOnClickLisenter(R.id.dialog_item_share1, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "点击了微博分享", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                dialog.setOnClickLisenter(R.id.dialog_item_share2, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "点击了朋友圈分享", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                edit = dialog.getView(R.id.dialog_edit);
                dialog.setOnClickLisenter(R.id.dialog_item_bt, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edit.getText().toString() != null &&
                                !"".equals(edit.getText().toString())) {
                            Toast.makeText(MainActivity.this, edit.getText().toString(), Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.main_bt4:

                break;
            case R.id.main_bt5:
                intent = new Intent(MainActivity.this, PlayVideoOnLineActivity.class);
                startActivity(intent);
                break;
            case R.id.main_bt6:
                intent = new Intent(this, RecyclerViewActivity.class);
                startActivity(intent);
                break;
            case R.id.main_bt7:
                intent = new Intent(this, IndicatorActivity.class);
                startActivity(intent);
                break;
            case R.id.main_bt8:
                intent = new Intent(this, PassworldActivity.class);
                startActivity(intent);
                break;
            case R.id.main_bt9:
                intent = new Intent(this, ProcessActivity.class);
                startActivity(intent);
                break;
            case R.id.main_bt10:
                intent = new Intent(this, UpdateSkinActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case requestCameraCode:
                try {
                    Uri uri = data.getData();
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    weakReference = new WeakReference<>(bitmap);
                    if (weakReference.get() != null) {
                        ((ImageView) findViewById(R.id.main_img)).setImageBitmap(weakReference.get());
                    }
                } catch (NullPointerException ex) {
                    Toast.makeText(this, "未选图片", Toast.LENGTH_LONG).show();
                } catch (Exception nex) {
                    Toast.makeText(this, "获取照片异常了", Toast.LENGTH_LONG).show();

                }
                break;
            default:
                break;
        }
    }

    @PermissionSuccesd(requestCode = requestCode)
    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:10086");
        intent.setData(data);
        startActivity(intent);
    }

    @PermissionFail(requestCode = requestCode)
    private void callPhoneFail() {
        Toast.makeText(this, "拒绝通话", Toast.LENGTH_LONG).show();
    }

    @PermissionSuccesd(requestCode = requestCameraCode)
    private void callAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, requestCameraCode);
    }

    @PermissionFail(requestCode = requestCameraCode)
    private void callAlbumFail() {
        Toast.makeText(this, "启动相册失败", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null) {
            bitmap.recycle();
            weakReference.clear();
        }
    }

    /**
     * 进入主页面获取异常文件
     */
    private void getExceptionFile() {
        File file = ExceptionCashHelper.getInstance().getCrashFile();
        if (file.exists()) {
            //上传服务器
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
                char[] buffer = new char[1024];
                int len = 0;
                while ((len = inputStreamReader.read(buffer)) != -1) {
                    String str = new String(buffer, 0, len);
                    Log.d("TAG", str);
                }
                //上传完毕删除文件
                file.delete();
                RepairApp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 安装修复差分包
     */
    public void RepairApp() {
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.aptach");
        if (fixFile.exists()) {
            //修复bug
            try {
                MyApp.patchManager.addPatch(fixFile.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "修复失败", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);//退到后台 实现和按HOME一样的效果
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
