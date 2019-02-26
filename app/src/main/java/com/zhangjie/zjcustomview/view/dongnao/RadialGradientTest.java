package com.zhangjie.zjcustomview.view.dongnao;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zhangjie.zjcustomview.R;

/**
 * 水波纹效果
 * @author tony
 *
 */

public class RadialGradientTest extends View {

    Shader mBitmapShader = null;
    Bitmap mBitmapPn = null;
    Paint mPaint = null;
    Shader mRadialGradient = null;
    Canvas mCanvas = null;
    ShapeDrawable mShapeDrawable = null;

    private int maxR=1000;
    private int nowR=20;
    public RadialGradientTest(Context context) {
        this(context,null);


    }

    public RadialGradientTest(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RadialGradientTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 初始化工作
        Bitmap bitmapTemp = ((BitmapDrawable) getResources().getDrawable(
                R.drawable.xyjy2)).getBitmap();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        // 创建与当前使用的设备窗口大小一致的图片
        mBitmapPn = Bitmap.createScaledBitmap(bitmapTemp, dm.widthPixels,
                dm.heightPixels, true);
        // 创建BitmapShader object
        mBitmapShader = new BitmapShader(mBitmapPn, Shader.TileMode.REPEAT,
                Shader.TileMode.MIRROR);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        // 将图片裁剪为椭圆型
        // 创建ShapeDrawable object，并定义形状为椭圆
        mShapeDrawable = new ShapeDrawable();// OvalShape:椭圆
        // 设置要绘制的椭圆形的东西为ShapeDrawable图片
        mShapeDrawable.getPaint().setShader(mBitmapShader);
        // 设置显示区域
        mShapeDrawable.setBounds(0, 0, mBitmapPn.getWidth(),
                mBitmapPn.getHeight());
        // 绘制ShapeDrawable
        mShapeDrawable.draw(canvas);
        if (mRadialGradient != null) {
            mPaint.setShader(mRadialGradient);
            if (mValue<1){
                canvas.drawCircle(x, y,nowR, mPaint);
            }

        }

    }


    private float x=0;
    private float y=0;

    private float mValue=0;
    // @覆写触摸屏事件
    public boolean onTouchEvent(MotionEvent event) {


        x=event.getX();
        y=event.getY();
        // @设置alpha通道（透明度）
        mPaint.setAlpha(400);
        mRadialGradient = new RadialGradient(x, y, 48,
                new int[] { Color.WHITE, Color.TRANSPARENT },null, Shader.TileMode.REPEAT  );
        // @重绘


        ValueAnimator valueAnimator= ValueAnimator.ofFloat(0f,1f);
        valueAnimator.setDuration(700);
        valueAnimator.setInterpolator(new FastOutLinearInInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                mValue=value;

                Log.e("=====","结果："+value);

                nowR= (int) (maxR*value);

                postInvalidate();
            }
        });

        valueAnimator.start();

        return true;
    }

}