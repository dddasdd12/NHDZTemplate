package org.oy.demo.nhdztemplate.views;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import org.oy.chardemo.utilslibrary.dialogutils.DialogViewHelper;

/**
 * Dialog 组装类
 * Created by Mro on 2017/3/9.
 */
public class AlertController {
    private AlertDialog mDialog;
    private Window mWindow;
    private DialogViewHelper viewHelper;

    public AlertController(AlertDialog alertDialog, Window window) {
        this.mDialog = alertDialog;
        this.mWindow = window;
    }

    public AlertDialog getDialog() {
        return mDialog;
    }

    public void setViewHelper(DialogViewHelper viewHelper) {
        this.viewHelper = viewHelper;
    }


    public Window getWindow() {
        return mWindow;
    }

    public void setText(int viewId, CharSequence charSequence) {
        viewHelper.setText(viewId, charSequence);
    }

    public <T extends View> T getView(int viewId) {
        return viewHelper.getView(viewId);
    }

    public void setOnClickLisenter(int viewId, View.OnClickListener onClickListener) {
        viewHelper.setOnClickLisenter(viewId, onClickListener);
    }

    public static class AlertParams {
        public Context mContext;
        public int themeStyleId;
        private DialogViewHelper viewHelper;
        //点击空白是否消失
        public boolean mCanncelable = true;
        //Dialog监听
        public DialogInterface.OnCancelListener mCanncelLisenter;
        //Dialog消失监听
        public DialogInterface.OnDismissListener mDismissLisenter;
        //按键监听
        public DialogInterface.OnKeyListener mKeyLisenter;
        //布局
        public View mView;
        //布局id
        public int viewLayoutId;
        //存放字体修改
        public SparseArray<CharSequence> textArray = new SparseArray<>();
        //存放点击事件
        public SparseArray<View.OnClickListener> lisenterArray = new SparseArray();
        //设置宽度
        public int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int heigh = ViewGroup.LayoutParams.WRAP_CONTENT;
        //动画效果
        public int mAnimation = 0;
        public int mGravity = Gravity.CENTER;

        public AlertParams(Context context, int themeStyleId) {
            this.mContext = context;
            this.themeStyleId = themeStyleId;
        }

        /**
         * 绑定和设置参数
         */
        public void apply(AlertController mAlert) {
            if (viewLayoutId != 0) {
                viewHelper = new DialogViewHelper(mContext, viewLayoutId);
            }
            if (mView != null) {
                viewHelper = new DialogViewHelper();
                viewHelper.setContentView(mView);
            }
            if (viewHelper == null) {
                throw new IllegalArgumentException("未调用setContentView");
            }
            mAlert.getDialog().setContentView(viewHelper.getContentView());
            mAlert.setViewHelper(viewHelper);
            int textSize = textArray.size();
            for (int i = 0; i < textSize; i++) {
                viewHelper.setText(textArray.keyAt(i), textArray.valueAt(i));
            }
            int clickSize = lisenterArray.size();
            for (int y = 0; y < clickSize; y++) {
                viewHelper.setOnClickLisenter(lisenterArray.keyAt(y), lisenterArray.valueAt(y));
            }
            Window window = mAlert.getWindow();
            window.setGravity(mGravity);
            if (mAnimation != 0) {
                window.setWindowAnimations(mAnimation);
            }
            WindowManager.LayoutParams windowLayoutParams = window.getAttributes();
            windowLayoutParams.width = width;
            windowLayoutParams.height = heigh;
            window.setAttributes(windowLayoutParams);
        }

    }
}
