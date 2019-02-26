package com.zhangjie.zjcustomview.view;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zhangjie.zjcustomview.R;

/**
 * 花束点赞
 */
public class zan_view extends RelativeLayout {


    float width = 0;
    float height = 0;


    public zan_view(Context context) {
        this(context, null);
    }

    public zan_view(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public zan_view(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


//        addView();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);


    }


//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.gift1);
//
//
//        Log.e("width=", "" + width / 2);
//
//
//        Log.e("width=", "" + height);
//
//
//
//        canvas.drawBitmap(bitmap, ivX - bitmap.getWidth() / 2, ivY - bitmap.getHeight(), new Paint());
//
//    }

    private ValueAnimator getBesselAnimator(final ImageView imageView, int rankWidth, int rankHeight) {
        float point0[] = new float[2];
        point0[0] = rankWidth / 2;
        point0[1] = rankHeight;

        float point1[] = new float[2];
        point1[0] = (float) ((rankWidth) * (0.10)) + (float) (Math.random() * (rankWidth) * (0.8));
        point1[1] = (float) (rankHeight - Math.random() * rankHeight * (0.5));

        float point2[] = new float[2];
        point2[0] = (float) (Math.random() * rankWidth);
        point2[1] = (float) (Math.random() * (rankHeight - point1[1]));

        float point3[] = new float[2];
        point3[0] = (float) (Math.random() * rankWidth);
        point3[1] = 0;

        BesselEvaluator besselEvaluator = new BesselEvaluator(point1, point2);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(besselEvaluator, point0, point3);
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float[] currentPosition = new float[2];
                currentPosition = (float[]) animation.getAnimatedValue();

                imageView.setTranslationX(currentPosition[0]);
                imageView.setTranslationY(currentPosition[1]);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(imageView);
                imageView.setImageBitmap(null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return valueAnimator;
    }


    public class BesselEvaluator implements TypeEvaluator<float[]> {
        private float point1[] = new float[2], point2[] = new float[2];

        public BesselEvaluator(float[] point1, float[] point2) {
            this.point1 = point1;
            this.point2 = point2;
        }

        @Override
        public float[] evaluate(float fraction, float[] point0, float[] point3) {
            float[] currentPosition = new float[2];
            currentPosition[0] = point0[0] * (1 - fraction) * (1 - fraction) * (1 - fraction)
                    + point1[0] * 3 * fraction * (1 - fraction) * (1 - fraction)
                    + point2[0] * 3 * (1 - fraction) * fraction * fraction
                    + point3[0] * fraction * fraction * fraction;
            currentPosition[1] = point0[1] * (1 - fraction) * (1 - fraction) * (1 - fraction)
                    + point1[1] * 3 * fraction * (1 - fraction) * (1 - fraction)
                    + point2[1] * 3 * (1 - fraction) * fraction * fraction
                    + point3[1] * fraction * fraction * fraction;
            return currentPosition;
        }
    }


    public void addViews() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.gift2);
        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(bitmap);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30,
                30);//两个400分别为添加图片的大小

        imageView.setLayoutParams(params);


        addView(imageView);

        imageView.layout(200,200,200,200);
         getBesselAnimator(imageView,(int)width,(int)height).start();


        Log.e("width=", "" + width);


        Log.e("width=", "" + height);

    }
}
