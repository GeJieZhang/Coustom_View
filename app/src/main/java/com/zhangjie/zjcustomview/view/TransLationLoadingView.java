package com.zhangjie.zjcustomview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.widget.LinearLayout;

import com.zhangjie.zjcustomview.MyApplication;
import com.zhangjie.zjcustomview.R;

public class TransLationLoadingView extends LinearLayout {

    private CircleView viewLeft, viewModell, viewRight;

    private float distance = 20;


    public TransLationLoadingView(Context context) {
        this(context, null);
    }

    public TransLationLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransLationLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initCircleView();
        post(new Runnable() {
            @Override
            public void run() {
                // 让布局实例化好之后再去开启动画
                expendAnimation();
            }


        });

    }

    private boolean isOut=false;
    private void expendAnimation() {



        //右-左
        ObjectAnimator leftTranslationAnimator = ObjectAnimator.ofFloat(viewLeft,"translationX",40,0);


        //左-右
        ObjectAnimator rightTranslationAnimator = ObjectAnimator.ofFloat(viewRight,"translationX",-40,0);

        AnimatorSet set = new AnimatorSet();

        set.setInterpolator(new AccelerateInterpolator());
        set.setDuration(250);
        set.playTogether(leftTranslationAnimator,rightTranslationAnimator);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                innerAnimation();
            }
        });
        set.start();





    }
    private void innerAnimation() {



        //右-左
        ObjectAnimator leftTranslationAnimator = ObjectAnimator.ofFloat(viewLeft,"translationX",-20,0);


        //左-右
        ObjectAnimator rightTranslationAnimator = ObjectAnimator.ofFloat(viewRight,"translationX",20,0);

        AnimatorSet set = new AnimatorSet();

        set.setInterpolator(new AccelerateInterpolator());
        set.setDuration(250);
        set.playTogether(leftTranslationAnimator,rightTranslationAnimator);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                expendAnimation();
            }
        });
        set.start();





    }

    private void initCircleView() {

        viewLeft = new CircleView(MyApplication.getContext());
        viewLeft.setColor(getResources().getColor(R.color.colorPrimary));


        viewModell = new CircleView(MyApplication.getContext());
        viewModell.setColor(getResources().getColor(R.color.colorBlack));



        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
        layoutParams.setMargins(20, 0, 20, 0);
        viewModell.setLayoutParams(layoutParams);


        viewRight = new CircleView(MyApplication.getContext());
        viewRight.setColor(getResources().getColor(R.color.colorAccent));
        addView(viewLeft);
        addView(viewModell);
        addView(viewRight);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }
}
