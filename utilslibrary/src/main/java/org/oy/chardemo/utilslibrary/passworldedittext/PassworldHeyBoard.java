package org.oy.chardemo.utilslibrary.passworldedittext;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.oy.chardemo.utilslibrary.R;


/**
 * 键盘
 * Created by Mro on 2017/4/3.
 */
public class PassworldHeyBoard extends LinearLayout implements View.OnClickListener {

    public PassworldHeyBoard(Context context) {
        this(context, null);
    }

    public PassworldHeyBoard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PassworldHeyBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.keyboard, this);
        setItemOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view instanceof TextView) {
            if (listener != null) {
                listener.click(((TextView) view).getText().toString().trim());
            }
        }
        if (view instanceof ImageView) {
            if (listener != null) {
                listener.delete();
            }
        }

    }

    public void setItemOnClickListener(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int chilsCount = viewGroup.getChildCount();
            for (int i = 0; i < chilsCount; i++) {
                View childView = viewGroup.getChildAt(i);
                setItemOnClickListener(childView);
            }
        } else {
            view.setOnClickListener(this);
        }
    }

    private KeyBoardListener listener;

    public void setKeyBoardListener(KeyBoardListener listener) {
        this.listener = listener;
    }


    public interface KeyBoardListener {
        void click(String number);

        void delete();
    }
}
