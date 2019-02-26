package com.zhangjie.zjcustomview.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;


/**
 * 上下滑动抽屉布局
 */

public class SlideView extends HorizontalScrollView {

    private View menuView;
    private View contenView;

    private int menuWidth=0;


    public SlideView(Context context) {
        this(context, null);
    }

    public SlideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //xml首次被解析完毕

        ViewGroup containor = (ViewGroup) getChildAt(0);
        int childCount = containor.getChildCount();

        if (childCount != 2) {

            throw new RuntimeException("只能有两个布局");

        }


        menuView = containor.getChildAt(0);
        contenView = containor.getChildAt(1);

        ViewGroup.LayoutParams menuParams = menuView.getLayoutParams();
        menuWidth=getScreenWidth(getContext()) - 100;
        menuParams.width = menuWidth;
        menuView.setLayoutParams(menuParams);



        ViewGroup.LayoutParams contentParams = contenView.getLayoutParams();
        contentParams.width = getScreenWidth(getContext());
        contenView.setLayoutParams(contentParams);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        closeMenu();
    }

    private void closeMenu() {
        smoothScrollTo(menuWidth,0);
    }

    private void openMenu() {
        smoothScrollTo(0,0);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        Log.e("l=", l+"");

        //float scale=l/menuWidth*1f;//1-0
        float scale = 1f * l / menuWidth;// scale 变化是 1 - 0

        float rightScale = 0.7f + 0.3f * scale;
        // 设置右边的缩放,默认是以中心点缩放
        // 设置缩放的中心点位置
        ViewCompat.setPivotX(contenView,0);
        ViewCompat.setPivotY(contenView, contenView.getMeasuredHeight() / 2);
        ViewCompat.setScaleX(contenView,rightScale);
        ViewCompat.setScaleY(contenView, rightScale);




        // 菜单的缩放和透明度
        // 透明度是 半透明到完全透明  0.5f - 1.0f
        float leftAlpha = 0.5f + (1-scale)*0.5f;
        ViewCompat.setAlpha(menuView,leftAlpha);
        // 缩放 0.7f - 1.0f
        float leftScale = 0.7f + (1-scale)*0.3f;
        ViewCompat.setScaleX(menuView,leftScale);
        ViewCompat.setScaleY(menuView, leftScale);

        // 最后一个效果 退出这个按钮刚开始是在右边，安装我们目前的方式永远都是在左边
        // 设置平移，先看一个抽屉效果
        // ViewCompat.setTranslationX(mMenuView,l);
        // 平移 l*0.7f
        ViewCompat.setTranslationX(menuView, 0.25f*l);




    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:

                float srollX=getScrollX();

                if (srollX>=menuWidth/2){
                    closeMenu();

                }else {
                    openMenu();
                }


                return  true;






        }






        return super.onTouchEvent(ev);
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
