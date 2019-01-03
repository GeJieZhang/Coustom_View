package com.zhangjie.zjcustomview.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.zhangjie.zjcustomview.R;
import com.zhangjie.zjcustomview.tool.PxUtils;
import com.zhangjie.zjcustomview.tool.TextUtils;


public class ChangeText extends android.support.v7.widget.AppCompatTextView {
    private int viewWidth = 0;
    private int viewHeight = 0;


    private int changeColor;
    private int normalColor;


    private int textWidth = 0;


    private Paint changePaint;

    private Paint normalPaint;


    private int theWay = 0;

    private ValueAnimator valueAnimator;

    public ChangeText(Context context) {
        this(context, null);
    }

    public ChangeText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChangeText);
        changeColor = typedArray.getColor(R.styleable.ChangeText_changeColor, getResources().getColor(R.color.colorAccent));
        normalColor = typedArray.getColor(R.styleable.ChangeText_normalColor, getResources().getColor(R.color.colorPrimary));
        typedArray.recycle();

        initPaint();

        initAnimation();
    }

    private void initPaint() {

        normalPaint = new Paint();
        normalPaint.setColor(normalColor);
        normalPaint.setAntiAlias(true);
        normalPaint.setDither(true);
        normalPaint.setTextSize(getTextSize());


        changePaint = new Paint();
        changePaint.setColor(changeColor);
        changePaint.setAntiAlias(true);
        changePaint.setDither(true);
        changePaint.setTextSize(getTextSize());


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        // 2.给的是wrap_content 需要计算
        if (widthMode == MeasureSpec.AT_MOST) {


            viewWidth = (int) TextUtils.getTextWidth(getText().toString(), normalPaint) + getPaddingLeft() + getPaddingRight();

        }


        if (heightMode == MeasureSpec.AT_MOST) {
            viewHeight = (int) TextUtils.getTextHeight(getText().toString(), normalPaint) + getPaddingTop() + getPaddingBottom();
        }
        textWidth = (int) TextUtils.getTextWidth(getText().toString(), normalPaint);
        // 设置控件的宽高
        setMeasuredDimension(viewWidth, viewHeight);

    }


    private float lien = 0;
    private float lien2 = 0;

    private boolean isStart = false;

    @Override
    protected void onDraw(Canvas canvas) {

        if (theWay == 0) {
            drawText(canvas, 0, lien, changePaint);

            drawText(canvas, lien, textWidth, normalPaint);
        } else {
            drawText(canvas, 0, lien2, normalPaint);

            drawText(canvas, lien2, textWidth, changePaint);
        }


    }

    private void drawText(Canvas canvas, float start, float end, Paint paint) {
        canvas.save();
        RectF rect = new RectF(start, 0, end, getHeight());
        canvas.clipRect(rect);
        float startX = TextUtils.getTextWidth(getText().toString(), paint);
        float baseLine = PxUtils.getBaseline(paint);
        canvas.drawText(getText().toString(), (viewWidth - startX) / 2, viewHeight / 2 + baseLine, paint);
        canvas.restore();
    }


    private void initAnimation() {
        if (valueAnimator == null) {


            valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(2000);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float step = (float) animation.getAnimatedValue();

                    lien = (textWidth * step);
                    lien2 = Math.abs((1 - step) * textWidth);


                    invalidate();


                }
            });
        }
    }




    public void start(int way){

        theWay = way;
        valueAnimator.start();
    }




}
