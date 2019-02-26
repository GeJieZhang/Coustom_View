package com.zhangjie.zjcustomview.view.dongnao;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * 雷达效果
 */

public class RadarGradientView extends View {


    public RadarGradientView(Context context) {

       this(context,null);
    }

    public RadarGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RadarGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        // 画圆用到的paint
        mPaintCircle = new Paint();
        mPaintCircle.setStyle(Paint.Style.STROKE); // 描边
        mPaintCircle.setStrokeWidth(1); // 宽度
        mPaintCircle.setAlpha(100); // 透明度
        mPaintCircle.setAntiAlias(true); // 抗锯齿
        mPaintCircle.setColor(Color.parseColor("#B0C4DE")); // 设置颜色 亮钢兰色

        // 扫描用到的paint
        mPaintRadar = new Paint();
        mPaintRadar.setStyle(Paint.Style.FILL_AND_STROKE); // 填充
        mPaintRadar.setAntiAlias(true); // 抗锯齿

        post(run);
    }




    private int mWidth, mHeight;

    //五个圆
    private float[] pots = {0.05f, 0.1f, 0.15f, 0.2f, 0.25f};

    private Shader scanShader; // 扫描渲染shader
    private Matrix matrix = new Matrix(); // 旋转需要的矩阵
    private int scanSpeed = 5; // 扫描速度
    private int scanAngle; // 扫描旋转的角度

    private Paint mPaintCircle; // 画圆用到的paint
    private Paint mPaintRadar; // 扫描用到的paint



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画布的旋转变换 需要调用save() 和 restore()
        canvas.save();
        canvas.concat(matrix);
        for (int i = 0; i < pots.length; i++) {
            canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth * pots[i], mPaintCircle);
        }




        scanShader = new SweepGradient(mWidth / 2, mHeight / 2,
                new int[]{Color.TRANSPARENT, Color.parseColor("#84B5CA")}, null);
        mPaintRadar.setShader(scanShader); // 设置着色器

        //canvas.setMatrix(matrix);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth * pots[4], mPaintRadar);

        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 取屏幕的宽高是为了把雷达放在屏幕的中间
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mWidth = mHeight = Math.min(mWidth, mHeight);
    }

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            scanAngle = (scanAngle + scanSpeed) % 360; // 旋转角度 对360取余
            matrix.postRotate(scanSpeed, mWidth / 2, mHeight / 2); // 旋转矩阵
            matrix.postTranslate(0,5);
            invalidate(); // 通知view重绘
            postDelayed(run, 50); // 调用自身 重复绘制
        }
    };

}
