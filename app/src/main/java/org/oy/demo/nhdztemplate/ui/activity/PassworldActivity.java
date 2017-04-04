package org.oy.demo.nhdztemplate.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.oy.chardemo.utilslibrary.passworldedittext.PassworldEditText;
import org.oy.chardemo.utilslibrary.passworldedittext.PassworldHeyBoard;
import org.oy.demo.nhdztemplate.R;
import org.oy.demo.nhdztemplate.views.AlertDialog;

public class PassworldActivity extends BaseActivity implements PassworldHeyBoard.KeyBoardListener,
        PassworldEditText.InputFinishListener, View.OnClickListener, AlertDialog.DialogListener {

    private Button bt;
    private PassworldEditText mPassworldEditText;
    private PassworldHeyBoard mPassworldHeyBoard;
    private AlertDialog dialog;

    @Override
    protected void beforSetContentView() {

    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_passworld;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        bt = (Button) findViewById(R.id.pay_bt);
        bt.setOnClickListener(this);
        dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.passworld_dialog)
                .fullWith(true)
                .fromBottom(true).create();
        dialog.setDialogListener(this);
        mPassworldEditText = dialog.getView(R.id.passworld_edit);
        mPassworldHeyBoard = dialog.getView(R.id.keyboard);
        mPassworldEditText.setEnabled(false);
        mPassworldHeyBoard.setKeyBoardListener(this);
        mPassworldEditText.setInputFinishListener(this);
    }

    @Override
    protected void FinishLoadXml() {

    }

    @Override
    public void click(String number) {
        mPassworldEditText.addNumber(number);
    }

    @Override
    public void delete() {
        mPassworldEditText.deleteNum();
    }

    @Override
    public void inputFinish(String passworld) {
        Toast.makeText(this, passworld, Toast.LENGTH_SHORT).show();
//        mPassworldEditText.empty();
        dialog.dismiss();
    }

    @Override
    public void onClick(View view) {
        dialog.show();
    }

    @Override
    public void isDisimiss() {
        mPassworldEditText.empty();
    }
}
