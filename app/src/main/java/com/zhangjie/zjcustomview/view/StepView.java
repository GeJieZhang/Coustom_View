package com.zhangjie.zjcustomview.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import com.zhangjie.zjcustomview.R;
import com.zhangjie.zjcustomview.tool.PxUtils;
import com.zhangjie.zjcustomview.tool.TextUtils;


/**
 * 记步View
 */

public class StepView extends View {

    //控件的宽高
    private int viewWidth = 0;
    private int viewHeight = 0;

    private int circleR = 0;

    private int mBorderWidth = 10;
    private int mStepTextSize = 15;

    private String mText = "00000";
    private float maxStep = 10000;
    private float nowStep = 0;
    private float step = 5000;

    private boolean isAnimationStart = false;


    //画笔
    private Paint outPaint = new Paint();
    private Paint inPaint = new Paint();
    private Paint tvPaint = new Paint();

    private ValueAnimator valueAnimator;

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        inPaint();

        initAnimation();
    }

    private void initAnimation() {
        if (valueAnimator == null) {


            valueAnimator = ValueAnimator.ofFloat(0, step);
            valueAnimator.setDuration(2000);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float step = (float) animation.getAnimatedValue();


                    Log.e("=======", step + "");


                    if (nowStep <= step) {
                        nowStep = step;
                        mText = (int) step + "";
                        invalidate();
                    }

                }
            });
        }
    }

    private void inPaint() {
        //画笔颜色
        outPaint.setColor(getResources().getColor(R.color.colorPrimary));
        //画笔宽度
        outPaint.setStrokeWidth(mBorderWidth);
        //圆角画笔
        outPaint.setStrokeCap(Paint.Cap.ROUND);
        // 画笔空心
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setAntiAlias(true);


        //画笔颜色
        inPaint.setColor(getResources().getColor(R.color.colorAccent));
        //画笔宽度
        inPaint.setStrokeWidth(mBorderWidth);
        //圆角画笔
        inPaint.setStrokeCap(Paint.Cap.ROUND);
        // 画笔空心
        inPaint.setStyle(Paint.Style.STROKE);
        inPaint.setAntiAlias(true);


        tvPaint.setColor(getResources().getColor(R.color.colorAccent));
        tvPaint.setTextSize(mStepTextSize);
        tvPaint.setAntiAlias(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //获取view的高度单位px
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);


        //得到高和宽中的最小值构成正方形
        viewWidth = width > height ? height : width;
        viewHeight = width > height ? height : width;

        //确认view的大小
        setMeasuredDimension(viewWidth, viewHeight);

        circleR = viewWidth / 2;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF = new RectF(mBorderWidth / 2, mBorderWidth / 2, circleR * 2 - mBorderWidth / 2, circleR * 2 - mBorderWidth / 2);

        canvas.drawArc(rectF, 135, 270, false, outPaint);

        float percent = nowStep / maxStep;
        canvas.drawArc(rectF, 135, 270 * percent, false, inPaint);

        float dx = TextUtils.getTextWidth(mText,tvPaint);
        float baseLine = PxUtils.getBaseline(tvPaint);
        canvas.drawText(mText, (viewWidth - dx) / 2, viewHeight / 2 + baseLine, tvPaint);


        if (!isAnimationStart) {
            valueAnimator.start();

            isAnimationStart = true;

        }

    }





}
