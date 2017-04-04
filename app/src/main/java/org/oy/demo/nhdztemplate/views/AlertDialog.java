package org.oy.demo.nhdztemplate.views;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import org.oy.demo.nhdztemplate.R;


/***
 * 自定义万能dialog
 */
public class AlertDialog extends Dialog {

    private AlertController mAlert;

    public AlertDialog(Context context, int themeResId) {
        super(context, themeResId);
        mAlert = new AlertController(this, getWindow());
    }

    public void setText(int viewId, CharSequence charSequence) {
        mAlert.setText(viewId, charSequence);
    }

    public <T extends View> T getView(int viewId) {
        return mAlert.getView(viewId);
    }

    public void setOnClickLisenter(int viewId, View.OnClickListener onClickListener) {
        mAlert.setOnClickLisenter(viewId, onClickListener);
    }

    public static class Builder {
        public final AlertController.AlertParams p;

        public Builder(Context context) {
            this(context, R.style.MyAlertDialog);
        }

        public Builder(Context context, int themeStyleId) {
            p = new AlertController.AlertParams(context, themeStyleId);
        }


        public Builder setContentView(View view) {
            p.mView = view;
            p.viewLayoutId = 0;
            return this;
        }

        public Builder setContentView(int viewId) {
            p.mView = null;
            p.viewLayoutId = viewId;
            return this;
        }

        public Builder setOnCanncelLisenter(OnCancelListener mCanncelLisenter) {
            p.mCanncelLisenter = mCanncelLisenter;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener mDismissListener) {
            p.mDismissLisenter = mDismissListener;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener mKeyListener) {
            p.mKeyLisenter = mKeyListener;
            return this;
        }

        public Builder setText(int viewId, CharSequence text) {
            p.textArray.put(viewId, text);
            return this;
        }

        public Builder setOnClicklisenter(int viewId, View.OnClickListener lisenter) {
            p.lisenterArray.put(viewId, lisenter);
            return this;
        }

        /**
         * 全屏操作
         */
        public Builder fullWith(boolean bool) {
            if (bool) {
                p.width = ViewGroup.LayoutParams.MATCH_PARENT;
            }
            return this;
        }

        public Builder fromBottom(boolean bool) {
            if (bool) {
                p.mAnimation = R.style.Animation;
            }
            p.mGravity = Gravity.BOTTOM;
            return this;
        }

        /**
         * 设置自己想要的动画
         */
        public Builder setDufaultAnimation(int styleAnimation) {
            p.mAnimation = styleAnimation;
            return this;
        }

        public Builder setWidthAndHeight(int width, int heigh) {
            if (width != 0) {
                p.width = width;
            }
            if (heigh != 0) {
                p.heigh = heigh;
            }
            return this;
        }


        public AlertDialog create() {
            AlertDialog dialog = new AlertDialog(p.mContext, p.themeStyleId);
            p.apply(dialog.mAlert);
            dialog.setCancelable(p.mCanncelable);
            dialog.setCanceledOnTouchOutside(p.mCanncelable);
            dialog.setOnCancelListener(p.mCanncelLisenter);
            dialog.setOnDismissListener(p.mDismissLisenter);
            if (p.mKeyLisenter != null) {
                dialog.setOnKeyListener(p.mKeyLisenter);
            }
            return dialog;
        }

        public AlertDialog show() {
            final AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }


    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (listener != null) {
            listener.isDisimiss();
        }
        System.gc();
    }

    private DialogListener listener;

    public void setDialogListener(DialogListener listener) {
        this.listener = listener;
    }

    public interface DialogListener {
        void isDisimiss();
    }
}
