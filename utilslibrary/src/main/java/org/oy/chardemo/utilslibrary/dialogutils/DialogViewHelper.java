package org.oy.chardemo.utilslibrary.dialogutils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * dialog view的辅助处理类
 * Created by Mro on 2017/3/9.
 */
public class DialogViewHelper {
    private View mContextView = null;
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper(Context mContext, int viewLayoutId) {
        this();
        mContextView = LayoutInflater.from(mContext).inflate(viewLayoutId, null);
    }

    public DialogViewHelper() {
        mViews = new SparseArray<>();
    }

    public void setContentView(View contentView) {
        this.mContextView = contentView;
    }

    public void setText(int viewId, CharSequence charSequence) {
        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(charSequence);
        }
    }

    public void setOnClickLisenter(int viewId, View.OnClickListener onClickListener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(onClickListener);
        }
    }

    public View getContentView() {
        return mContextView;
    }


    public <T extends View> T getView(int viewId) {
        WeakReference<View> view = mViews.get(viewId);
        View v = null;
        if (view != null) {
            v = view.get();
        }
        if (v == null) {
            v = mContextView.findViewById(viewId);
            if (view != null) {
                mViews.put(viewId, new WeakReference<>(v));
            }
        }
        return (T) v;
    }
}
