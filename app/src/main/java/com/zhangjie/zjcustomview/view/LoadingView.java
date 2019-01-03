package com.zhangjie.zjcustomview.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import com.zhangjie.zjcustomview.R;

import static android.support.constraint.Constraints.TAG;


public class LoadingView extends View {


    private float width = 0;
    private float height = 0;
    private float centerX = 0;//中心点X坐标
    private float centerY = 0;//中心点Y轴坐标

    private float circle_line_diatance = 200;//圆形到线条的距离


    private float linWidth=50;//阴影的宽度
    private float linNormalWidth=50;//阴影的宽度

    private float circleX = 0;
    private float circleY = 0;


    private float normalCircleY = 0;
    private float normalStateCircleY = 0;
    private float lineX = 0;
    private float lineY = 0;


    private float r = 15;

    private Paint circlePaint1;//圆形
    private Paint circlePaint2;//矩形
    private Paint circlePaint3;//三角形
    private Paint circlePaint4;//阴影
    private boolean animatorNormal = false;

    private boolean isFinish = true;

    private ValueAnimator valueAnimator;


    private int type = 0;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        circlePaint1 = new Paint();
        circlePaint1.setColor(getResources().getColor(R.color.colorAccent));
        circlePaint1.setAntiAlias(true);
        circlePaint1.setDither(true);
        circlePaint1.setStyle(Paint.Style.FILL);

        circlePaint2 = new Paint();
        circlePaint2.setColor(getResources().getColor(R.color.colorPrimary));
        circlePaint2.setAntiAlias(true);
        circlePaint2.setDither(true);
        circlePaint2.setStyle(Paint.Style.FILL);


        circlePaint3 = new Paint();
        circlePaint3.setColor(getResources().getColor(R.color.colorCustom));
        circlePaint3.setAntiAlias(true);
        circlePaint3.setDither(true);
        circlePaint3.setStyle(Paint.Style.FILL);


        circlePaint4 = new Paint();
        circlePaint4.setColor(getResources().getColor(R.color.colorBlack));
        circlePaint4.setAntiAlias(true);
        circlePaint4.setDither(true);
        circlePaint4.setStyle(Paint.Style.FILL);
        circlePaint4.setStrokeCap(Paint.Cap.ROUND);

        valueAnimator = ObjectAnimator.ofFloat(1);
        // valueAnimator.setFloatValues(100);
        valueAnimator.setDuration(1000);

        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                double value = (float) animation.getAnimatedValue();

                isFinish = false;


                if (animatorNormal) {

                    //向上
                    double sode=0.3+value*0.7;
                    linWidth= (float) (sode*linNormalWidth);

                    //float newValue=1-value;
                    circleY = (float) (normalCircleY - circle_line_diatance * value);

                    Log.e("centerY=", "" + circleY);
                    invalidate();
//                   Log.e("value=",""+value);
                    if ((int) value == 1) {

                        animatorNormal = false;
                        isFinish = true;
                        normalCircleY = circleY;

                    } else {

                        animatorNormal = true;

                    }

                } else {
                    double sode=0.3+(1-value)*0.7;

                    linWidth= (float) (sode*linNormalWidth);

                    //向下
                    circleY = (float) (normalCircleY + circle_line_diatance * value);
//                   Log.e("value=",""+value);

                    Log.e("centerY=", "" + circleY);

                    invalidate();
                    if ((int) value == 1) {

                        animatorNormal = true;
                        isFinish = true;
                        normalCircleY = circleY;
                    } else {

                        animatorNormal = false;

                    }


                }


                //invalidate();

            }


        });

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        Log.e(TAG, "onMeasure:width:" + width);

        Log.e(TAG, "onMeasure:height:" + height);

        centerX = width / 2;
        centerY = height / 2;
        circleX = centerX;
        circleY = centerY - circle_line_diatance / 2;
        normalCircleY = circleY;
        normalStateCircleY=circleY;
        lineX = centerX;
        lineY = centerY + circle_line_diatance / 2;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (type) {
            case 0:

                drawCircle(canvas);
                break;
            case 1:

                drawRect(canvas);
                break;
            case 2:

                drawSanJiao(canvas);
                break;
        }

        canvas.drawLine(circleX-linWidth/2,normalStateCircleY+r+circle_line_diatance,circleX+linWidth/2,normalStateCircleY+r+circle_line_diatance,circlePaint4);


        if (isFinish) {

            switch (type) {
                case 0:

                    if (count >= 2) {
                        type = 1;
                        count = 0;
                    }


                    break;
                case 1:
                    if (count >= 2) {
                        type = 2;
                        count = 0;
                    }
                    break;
                case 2:
                    if (count >= 2) {
                        type = 0;
                        count = 0;
                    }

                    break;
            }

            count++;

            valueAnimator.start();


            Log.e("onDraw=====","start");
        }


    }

    private int count = 0;

    private void drawCircle(Canvas canvas) {
        //绘制圆形
        canvas.drawCircle(circleX, circleY, r, circlePaint1);
    }

    private void drawRect(Canvas canvas) {
        //绘制矩形
        RectF rectF = new RectF(circleX - r, circleY - r, circleX + r, circleY + r);
        canvas.drawRect(rectF, circlePaint2);
    }

    private void drawSanJiao(Canvas canvas) {
        //绘制三角形
        Path path = new Path();
        path.moveTo(circleX, circleY - r / 5 * 4);// 此点为多边形的起点


        path.lineTo(circleX + r, circleY + r / 4 * 3);

        path.lineTo(circleX - r, circleY + r / 4 * 3);

        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, circlePaint3);
    }
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == View.VISIBLE){
            //开始某些任务
            valueAnimator.start();

            Log.e("onWindowVianged=====","start");
        }
        else if(visibility == INVISIBLE || visibility == GONE){

            //停止某些任务
            valueAnimator.cancel();

            Log.e("onWindowVed=====","cancel");
        }
    }

}
