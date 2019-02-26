package com.zhangjie.zjcustomview.view.zj;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.zhangjie.zjcustomview.R;
import com.zhangjie.zjcustomview.tool.CoustomViewTools;
import com.zhangjie.zjcustomview.tool.PxUtils;

import java.util.Date;

/**
 * 京软加载按钮
 */
public class LoadingButton extends View {

    private int DEFAULT_BGCOLOR = Color.parseColor("#0E8DD4");
    private int FOCUS_BGCOLOR = Color.parseColor("#0c6ca1");

    private int DEFAULT_TEXT_COLOR = Color.parseColor("#ffffff");

    private int DEFAULT_CIRCLE_COLOR = Color.parseColor("#ffffff");
    //动画持续时间
    private int ROTATION_ANIMATION_TIME = 2000;
    //sp
    private int DEFAULT_TEXT_SIZE = 12;

    private String DEFAULT_TEXT = "";

    private Paint bgPaint, tvPaint, circlePaint,focusPaint;

    private int width = 0;
    private int height = 0;
    //按钮圆角
    private int DEFAULT_BORDER_RADIUS = 4;

    //小圆半径
    private int mCircleRadius = 3;
    //大圆半径
    private int mRotationRadius = 15;
    //圆形个数
    private int circleCount = 3;


    private int STATE = 0;
    long t1 = 0;//记录上一次单击的时间，初始值为0
    public LoadingButton(Context context) {
        this(context, null);
    }

    public LoadingButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        initPaint();


//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                switch (STATE) {
//
//                    case 0: {
//
//                        STATE = 1;
//
//                        invalidate();
//
//                        break;
//                    }
//                }
//
//            }
//        });


    }

    private void initAttr(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton);

        DEFAULT_BGCOLOR = typedArray.getColor(R.styleable.LoadingButton_bgColor, DEFAULT_BGCOLOR);
        FOCUS_BGCOLOR = typedArray.getColor(R.styleable.LoadingButton_bgFocusColor, FOCUS_BGCOLOR);
        DEFAULT_TEXT_COLOR = typedArray.getColor(R.styleable.LoadingButton_textColor, DEFAULT_TEXT_COLOR);
        DEFAULT_TEXT_SIZE = PxUtils.sp2px(typedArray.getDimensionPixelSize(R.styleable.LoadingButton_textSize, DEFAULT_TEXT_SIZE));
        DEFAULT_BORDER_RADIUS=PxUtils.dip2px(typedArray.getDimensionPixelSize(R.styleable.LoadingButton_borderRadius, DEFAULT_BORDER_RADIUS));
        DEFAULT_TEXT = typedArray.getString(R.styleable.LoadingButton_text);

        typedArray.recycle();
    }


    private void initPaint() {
        bgPaint = new Paint();
        bgPaint.setColor(DEFAULT_BGCOLOR);
        bgPaint.setAntiAlias(true);
        bgPaint.setDither(true);
        bgPaint.setStyle(Paint.Style.FILL);


        focusPaint = new Paint();
        focusPaint.setColor(FOCUS_BGCOLOR);
        focusPaint.setAntiAlias(true);
        focusPaint.setDither(true);
        focusPaint.setStyle(Paint.Style.FILL);

        tvPaint = new Paint();
        tvPaint.setColor(DEFAULT_TEXT_COLOR);
        tvPaint.setAntiAlias(true);
        tvPaint.setDither(true);
        tvPaint.setStyle(Paint.Style.FILL);
        tvPaint.setTextSize(DEFAULT_TEXT_SIZE);

        circlePaint = new Paint();
        circlePaint.setColor(DEFAULT_CIRCLE_COLOR);
        circlePaint.setAntiAlias(true);
        circlePaint.setDither(true);
        circlePaint.setStyle(Paint.Style.FILL);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);
        mRotationRadius=height/4;

    }

    private RotationState mLoadingState;
    private NormalState mNormalState;

    private Canvas canvas;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.canvas = canvas;


        switch (STATE) {
            case 0: {

                if (mNormalState == null) {
                    mNormalState = new NormalState();
                }
                mNormalState.draw(canvas);

                break;
            }

            case 1: {


                if (mLoadingState == null) {
                    mLoadingState = new RotationState();
                }


                mLoadingState.draw(canvas);

                break;
            }
        }


    }


    private float mCurrentRotationAngle = 0;


    public class NormalState extends LoadingState {

        @Override
        public void draw(Canvas canvas) {
            RectF bgRectf = new RectF(0, 0, width, height);

            canvas.drawRoundRect(bgRectf, DEFAULT_BORDER_RADIUS, DEFAULT_BORDER_RADIUS, bgPaint);
            float startX = width / 2 - CoustomViewTools.getTextWidth(DEFAULT_TEXT, tvPaint) / 2;
            canvas.drawText(DEFAULT_TEXT, startX, CoustomViewTools.getBaseline(height / 2, tvPaint), tvPaint);
        }
    }


    /**
     * 旋转动画
     */
    public class RotationState extends LoadingState {
        private ValueAnimator mAnimator;

        public RotationState() {

            // 搞一个变量不断的去改变 打算采用属性动画 旋转的是 0 - 360
            mAnimator = ObjectAnimator.ofFloat(0f, 2 * (float) Math.PI);
            mAnimator.setDuration(ROTATION_ANIMATION_TIME);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotationAngle = (float) animation.getAnimatedValue();

                    // 重新绘制
                    invalidate();
                }
            });
            // 线性差值器
            mAnimator.setInterpolator(new LinearInterpolator());
            // 不断反复执行
            mAnimator.setRepeatCount(-1);
            mAnimator.start();

        }

        @Override
        public void draw(Canvas canvas) {
            setClickable(false);
            RectF bgRectf = new RectF(0, 0, width, height);

            canvas.drawRoundRect(bgRectf, DEFAULT_BORDER_RADIUS, DEFAULT_BORDER_RADIUS, focusPaint);


            // 画六个圆  每份角度
            double percentAngle = Math.PI * 2 / circleCount;
            for (int i = 0; i < circleCount; i++) {

                // 当前的角度 = 初始角度 + 旋转的角度
                double currentAngle = percentAngle * i + mCurrentRotationAngle;
                int cx = (int) (width / 2 + mRotationRadius * Math.cos(currentAngle));
                int cy = (int) (height / 2 + mRotationRadius * Math.sin(currentAngle));
                canvas.drawCircle(cx, cy, mCircleRadius, circlePaint);
            }
        }

        /**
         * 取消动画
         */
        public void cancel() {
            mAnimator.cancel();
        }
    }


    public abstract class LoadingState {
        public abstract void draw(Canvas canvas);
    }


    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == View.VISIBLE) {
            //开始某些任务


            switch (STATE) {

                case 1: {


                    invalidate();
                    break;
                }
            }


        } else if (visibility == INVISIBLE || visibility == GONE) {

            //停止某些任务

            switch (STATE) {

                case 1: {


                    if (mLoadingState != null) {
                        mLoadingState.cancel();
                        mLoadingState=null;
                    }


                    break;
                }
            }
        }
    }


    protected boolean isCover() {
        boolean cover = false;
        Rect rect = new Rect();
        cover = getGlobalVisibleRect(rect);
        if (cover) {
            if (rect.width() >= getMeasuredWidth() && rect.height() >= getMeasuredHeight()) {
                return !cover;
            }
        }
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()){
            case MotionEvent.ACTION_UP:{




                if(t1==0){//第一次单击，初始化为本次单击的时间
                    t1= (new Date()).getTime();
                }else{
                    long curTime = (new Date()).getTime();//本地单击的时间
                    //System.out.println("两次单击间隔时间："+(curTime-t1));//计算本地和上次的时间差
                    if(curTime-t1>1*1000){
                        //间隔5秒允许点击，可以根据需要修改间隔时间
                        t1 = curTime;//当前单击事件变为上次时间
                        switch (STATE) {

                            case 0: {

                                STATE = 1;

                                invalidate();

                                break;
                            }
                        }

                    }else {
                        //提示不要频繁点击

                        Toast.makeText(getContext(),"操作频繁",Toast.LENGTH_SHORT).show();
                    }
                }



                break;
            }
        }

        return super.onTouchEvent(event);

    }



    public void stopLoading(){

        switch (STATE) {

            case 1: {

                STATE = 0;
                setClickable(true);
                invalidate();

                break;
            }
        }

    }
}
