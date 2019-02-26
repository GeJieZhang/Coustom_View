package com.zhangjie.zjcustomview.view.zj;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.renderscript.Sampler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.zhangjie.zjcustomview.R;

//https://www.jianshu.com/p/babac4f5ea5a
public class CMTextView extends android.support.v7.widget.AppCompatTextView {

    private int height = 0;
    private int width = 0;
    private Paint cmPaint, mOutPaint;

    private String mCmText = "";
    //进度条的宽度
    private int progressSize = 0;

    private int circleStrokeWidth = 4;

    private int halfCircleStrokeWidth = circleStrokeWidth / 2;

    private int mPadding = 4;

    private float value = 1f;

    private ValueAnimator valueAnimator;

    private Shader scanShader; // 扫描渲染shader

    private float centerX = 0;
    private float centerY = 0;
    //是否显示进度条
    private boolean isShowProgress = true;

    public CMTextView(Context context) {
        this(context, null);
    }

    public CMTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CMTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mCmText = (String) getText();
        initPaint();


        initAnimat();

    }


    private void initAnimat() {


        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

    }

    private void initPaint() {
        cmPaint = new Paint();
        cmPaint.setColor(getTextColors().getDefaultColor());
        cmPaint.setAntiAlias(true);
        cmPaint.setDither(true);
        cmPaint.setTextSize(getTextSize());


        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeWidth(circleStrokeWidth);
        mOutPaint.setColor(Color.RED);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutPaint.setStyle(Paint.Style.STROKE);// 画笔空心
    }

    /**
     * View 的 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        Rect bounds = new Rect();
        cmPaint.getTextBounds(mCmText, 0, mCmText.length(), bounds);
        height = bounds.height() + getPaddingBottom() + getPaddingTop();
        width = bounds.width() + getPaddingLeft() + getPaddingRight();
        progressSize = bounds.height();


        if (isShowProgress){
            setMeasuredDimension(width + progressSize + halfCircleStrokeWidth + mPadding, height + halfCircleStrokeWidth * 2);
        }else {
            setMeasuredDimension(width,height);
        }
    }


    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {




        /**
         * 绘制文字
         */
        //super.onDraw(canvas);
        //计算基线
        Paint.FontMetricsInt fontMetricsInt = cmPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        int x = getPaddingLeft();
        // x: 开始的位置  y：基线
        canvas.drawText(mCmText, x, baseLine, cmPaint);

        /**
         * 绘制进度条
         */

        if (isShowProgress) {
            if (value == 1) {
                valueAnimator.start();
            }

            canvas.save();
            Rect bounds = new Rect();
            cmPaint.getTextBounds(mCmText, 0, mCmText.length(), bounds);
            float left = getPaddingLeft() + bounds.width() + mPadding;
            float top = getPaddingTop() + halfCircleStrokeWidth;
            float right = left + progressSize;
            float bottom = bounds.height() + getPaddingTop() + getPaddingBottom() + halfCircleStrokeWidth;
            centerX = (left + progressSize / 2);
            centerY = top + progressSize / 2;

            RectF rectF = new RectF(left, top
                    , right, bottom);
            canvas.rotate(value * 360, rectF.left + (rectF.right - rectF.left) / 2, rectF.top + (rectF.bottom - rectF.top) / 2); //以矩形中心点旋转图形
            scanShader = new SweepGradient(centerX, centerY,
                    new int[]{Color.TRANSPARENT, getTextColors().getDefaultColor()}, null);
            mOutPaint.setShader(scanShader); // 设置着色器
            // 研究研究
            canvas.drawArc(rectF, 0, 360, false, mOutPaint);

            canvas.restore();
        }
    }


    private void setIsShowProgress(boolean isShow) {
        isShowProgress = isShow;
    }


}