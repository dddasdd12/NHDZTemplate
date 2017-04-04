package org.oy.chardemo.utilslibrary.passworldedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import org.oy.chardemo.utilslibrary.R;
import org.oy.chardemo.utilslibrary.windowutils.TranceFormation;

/**
 * 密码输入框
 * Created by Mro on 2017/4/1.
 */
public class PassworldEditText extends EditText {
    // 画笔
    private Paint mPaint;
    // 密码框的宽度
    private int passworldItemWidth;
    // 密码个数 
    private int passworldNums;
    // 密码圆点个数 
    private int circularNums = 6;
    // 密码圆点颜色 
    private int circularColor;
    // 密码圆点半径 
    private int circularRadius;
    // 分割线颜色 
    private int diviedingLinesColor;
    // 分割线大小 
    private int diviedingLinesSize;
    // 边框颜色 
    private int bgColor;
    // 边框大小 
    private int bgSize;
    // 边框圆角大小 
    private int bgCorner;

    public PassworldEditText(Context context) {
        this(context, null);
    }

    public PassworldEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        iniTypeArray(context, attrs);
        setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
    }

    private void iniTypeArray(Context context, AttributeSet attrs) {
        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PassworldEditText);
        passworldNums = array.getInteger(R.styleable.PassworldEditText_passworldNums, passworldNums);
        circularNums = array.getInteger(R.styleable.PassworldEditText_circularNums, circularNums);
        circularRadius = (int) array.getDimension(R.styleable.PassworldEditText_circularRadius, TranceFormation.Dp2Px(context, circularRadius));
        diviedingLinesSize = (int) array.getDimension(R.styleable.PassworldEditText_diviedingLinesSize, TranceFormation.Dp2Px(context, diviedingLinesSize));
        bgSize = (int) array.getDimension(R.styleable.PassworldEditText_bgSize, TranceFormation.Dp2Px(context, bgSize));
        bgCorner = (int) array.getDimension(R.styleable.PassworldEditText_bgCorner, bgCorner);
        //获取颜色
        circularColor = array.getColor(R.styleable.PassworldEditText_circularColor, circularColor);
        diviedingLinesColor = array.getColor(R.styleable.PassworldEditText_diviedingLinesColor, diviedingLinesColor);
        bgColor = array.getColor(R.styleable.PassworldEditText_bgColor, bgColor);
        array.recycle();
    }

    private void initPaint() {
        // 初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);// 抗锯齿
        mPaint.setDither(true);// 防抖动
    }


    @Override
    protected void onDraw(Canvas canvas) {
        passworldItemWidth = (getWidth() - bgSize * 2 - (passworldNums - 1) * diviedingLinesSize) / passworldNums;
        // 画背景
        canvasBg(canvas);
        // 画竖线
        drawDividingLines(canvas);
        // 画圆点
        drawCircular(canvas);
        // 输入完成回调结果
        if (mListener != null) {
            String passworld = getText().toString().trim();
            if (passworld.length() >= passworldNums) {
                mListener.inputFinish(passworld);
            }
        }
    }

    private void drawCircular(Canvas canvas) {
        // 画笔实心
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(circularColor);
        // 获取输入
        String text = getText().toString().trim();
        int passworldLenght = text.length();
        for (int i = 0; i < passworldLenght; i++) {
            float cx = bgSize + i + diviedingLinesSize + passworldItemWidth * i + passworldItemWidth / 2;
            float cy = getHeight() / 2;
            canvas.drawCircle(cx, cy, circularRadius, mPaint);
        }
    }


    /**
     * 绘制竖线
     */
    private void drawDividingLines(Canvas canvas) {
        // 设置画笔宽度->线宽
        mPaint.setStrokeWidth(diviedingLinesSize);
        mPaint.setColor(diviedingLinesColor);
        // 循环画线
        for (int i = 0; i < passworldNums - 1; i++) {
            int startX = passworldItemWidth * (i + 1) + diviedingLinesSize;
            int startY = bgSize;
            int endX = startX;
            int endY = getHeight() - bgSize;
            canvas.drawLine(startX, startY, endX, endY, mPaint);
        }
    }

    /**
     * 绘制背景边框
     */
    private void canvasBg(Canvas canvas) {
        // 绘制背景
        // 如果角度没传则绘制Rect 如果传了  则绘制RoundRect
        mPaint.setStrokeWidth(bgSize);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(bgColor);
        RectF rectF = new RectF(bgSize, bgSize, getWidth() - bgSize, getHeight() - bgSize);
        if (bgCorner == 0) {
            canvas.drawRect(rectF, mPaint);
        } else {
            canvas.drawRoundRect(rectF, bgCorner, bgCorner, mPaint);
        }
    }

    public void addNumber(String number) {
        String text = getText().toString().trim();
        if (text.length() >= passworldNums) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(text);
        stringBuilder.append(number);
        setText(stringBuilder.toString());
    }

    public void deleteNum() {
        String text = getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        text = text.substring(0, text.length() - 1);
        setText(text);
    }

    private InputFinishListener mListener;

    public void setInputFinishListener(InputFinishListener mListener) {
        this.mListener = mListener;
    }

    public void empty() {
        setText("");
    }

    public interface InputFinishListener {
        void inputFinish(String passworld);
    }
}
