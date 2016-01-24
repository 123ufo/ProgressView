package com.ufo.costomview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ufo.costomview.R;

/**
 * 作者：xudiwei
 * <p/>
 * 日期： Administrator on 2014/9/23.
 * <p/>
 * 文本描述：圆形进度控件
 */
public class ProgressView extends View {

    private static final String LOG_TAG = "ScanView";

    private static final int DEF_TEXT_COLOR = 0xffffffff;
    private static final int DEF_SWEEP_COLOR = 0x6600d900;
    private static final int DEF_CIRCLE_COLOR = 0xff009900;

    /**
     * 圆的颜色
     */
    private int circleBgColor;

    /**
     * 进度的颜色
     */
    private int sweepCircleColor;
    /**
     * 文本的颜色
     */
    private int textColor;

    private Paint mPaint;

    private String text = "0";

    private Rect rect = new Rect();

    private int mProgress;

    /**
     * 是否测量文本的高度，一般只测量一次
     */
    private boolean measureTextHeight = true;
    /**
     * 默认文本的大小是当前控件的三分之一
     */
    private static final int defScale = 3;
    /**
     * 文本的高度
     */
    private float textHeight;

    public ProgressView(Context context) {
        super(context);
        initPaint();
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        initAttrs(array, context);
        array.recycle();
        initPaint();
    }

    /**
     * 初始化当前view的属性值
     */
    private void initAttrs(TypedArray array, Context context) {

        int circelBgColorRes = array.getResourceId(R.styleable.ProgressView_circle_bgColor, -1);
        if (circelBgColorRes != -1) {
            circleBgColor = context.getResources().getColor(circelBgColorRes);
        } else {
            circleBgColor = DEF_CIRCLE_COLOR;
        }

        int sweepCircelColorRes = array.getResourceId(R.styleable.ProgressView_sweep_circelColor, -1);
        if (sweepCircelColorRes != -1) {
            sweepCircleColor = context.getResources().getColor(sweepCircelColorRes);
        } else {
            sweepCircleColor = DEF_SWEEP_COLOR;
        }

        int textColorRes = array.getResourceId(R.styleable.ProgressView_text_color, -1);
        if (textColorRes != -1) {
            textColor = context.getResources().getColor(textColorRes);
        } else {
            textColor = DEF_TEXT_COLOR;
        }


    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //控制当前view的宽与高一样大小。取最小的
        Log.d(LOG_TAG, "Height: " + getMeasuredHeight());
        Log.d(LOG_TAG, "Width:   " + getMeasuredWidth());
        getLayoutParams().height = Math.min(getMeasuredHeight(), getMeasuredWidth());
        getLayoutParams().width = Math.min(getMeasuredHeight(), getMeasuredWidth());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int viewHeight = getMeasuredHeight();

        //画圆
        drawCircelBg(canvas, viewHeight);

        //画进度
        drawSweepCircel(canvas, viewHeight);

        //画文字
        drawText(canvas, viewHeight);

    }

    /**
     * 画文字
     *
     * @param canvas
     * @param viewHeight
     */
    private void drawText(Canvas canvas, int viewHeight) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(textColor);
        mPaint.setTextSize(viewHeight / defScale);
        text = String.valueOf(mProgress);
        //文本的宽用measureText来获取比较接近
        float textWidth = mPaint.measureText(text);
        //而文本的高用getTextBounds获取
        if (measureTextHeight) {
            mPaint.getTextBounds(text, 0, text.length(), rect);
            textHeight = rect.height();
            measureTextHeight = false;
        }
        Log.d(LOG_TAG, "textWidth: " + textWidth);
        Log.d(LOG_TAG, "textHeight: " + rect.height());
        canvas.drawText(text, viewHeight / 2 - textWidth / 2, viewHeight / 2 + textHeight / 2, mPaint);
    }

    /**
     * 画圆的背景
     *
     * @param canvas
     * @param viewHeight
     */
    private void drawCircelBg(Canvas canvas, int viewHeight) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(circleBgColor);
        int circleX, circleY, radius;
        circleY = circleX = radius = viewHeight / 2;
        canvas.drawCircle(circleX, circleY, radius, mPaint);
    }


    /***
     * 画扫过（进度）的圆
     *
     * @param canvas
     * @param viewHeight
     */
    private void drawSweepCircel(Canvas canvas, int viewHeight) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(sweepCircleColor);
        RectF rectF = new RectF(0, 0, viewHeight, viewHeight);
        float sweepAngle = (float) (360 / 100.0 * mProgress);
        canvas.drawArc(rectF, -90, sweepAngle, true, mPaint);

    }

    /**
     * 进度 从0到100
     *
     * @param progress
     */
    public void setProgress(int progress) {
        if (progress <= 0) {
            this.mProgress = 0;
        } else if (progress >= 100) {
            this.mProgress = 100;
        } else {
            this.mProgress = progress;
        }
//        invalidate();
        postInvalidate();
    }

    public int getCurrentProgress() {
        return this.mProgress;
    }


    public int getSweepCircleColor() {
        return sweepCircleColor;
    }

    public void setSweepCircleColor(int sweepCircleColor) {
        this.sweepCircleColor = sweepCircleColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getCircleBgColor() {
        return circleBgColor;
    }

    public void setCircleBgColor(int circleBgColor) {
        this.circleBgColor = circleBgColor;
    }
}


















