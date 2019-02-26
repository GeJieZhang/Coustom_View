package com.zhangjie.zjcustomview.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import com.zhangjie.zjcustomview.MyApplication;
import com.zhangjie.zjcustomview.R;

/**
 * 评分控件
 */

public class StartTabView extends View {

    private int count = 5;//星星的数量
    private float viewWidth = 0;
    private float viewHeight = 0;
    private int changeStart = 0;
    private int normalStart = 0;
    private Bitmap changeBitmap, normalBitmap = null;

    private float Panding = 10;


    private float actionLeft = 0;


    public StartTabView(Context context) {
        this(context, null);
    }

    public StartTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StartTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StartTabView);
        changeStart = typedArray.getResourceId(R.styleable.StartTabView_changeStart, R.mipmap.iv_start_focus);
        normalStart = typedArray.getResourceId(R.styleable.StartTabView_normalStart, R.mipmap.iv_start_normal);
        typedArray.recycle();
        changeBitmap = BitmapFactory.decodeResource(getResources(), changeStart);
        normalBitmap = BitmapFactory.decodeResource(getResources(), normalStart);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = (normalBitmap.getWidth() + Panding) * count;
        viewHeight = normalBitmap.getHeight() + Panding;
        Log.e("viewHeight" + "=", viewHeight + "");
        Log.e("viewWidth" + "=", viewWidth + "");
        setMeasuredDimension((int) viewWidth, (int) viewHeight);


    }


    private float gradeLeftA=0;
    private float gradeLeftB=0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        for (int i = 0; i < count; i++) {


            float left = (2 * i + 1) * Panding / 2 + normalBitmap.getWidth() * i;
            float top = Panding / 2;


            Log.e("left" + i + "=", left + "");

            Log.e("top" + i + "=", top + "");


            if (actionLeft >= left) {
                gradeLeftA=left;
                gradeLeftB=left+ normalBitmap.getWidth()+Panding;

                canvas.drawBitmap(changeBitmap, left, top, null);
            } else {
                canvas.drawBitmap(normalBitmap, left, top, null);
            }


        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            //case MotionEvent.ACTION_UP:


                actionLeft=event.getX();

                if (actionLeft<=gradeLeftB&&actionLeft>=gradeLeftA){

                    Toast.makeText(MyApplication.getContext(),"不用重新绘制",Toast.LENGTH_SHORT);

                }else {
                    invalidate();
                }




                break;

        }
        return true;
    }
}
