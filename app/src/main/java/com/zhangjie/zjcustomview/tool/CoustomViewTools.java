package com.zhangjie.zjcustomview.tool;

import android.graphics.Paint;
import android.graphics.Rect;

import com.zhangjie.zjcustomview.MyApplication;

public class CoustomViewTools {
    //==============================================================================================
    //====================================================================================单位=======
    //==============================================================================================
    /**
     * dp转px
     * @param dp
     * @return
     */
    public static int dip2px(int dp)
    {
        float density = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dp*density+0.5);
    }

    /** px转换dip */
    public static int px2dip(int px) {
        final float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
    /** px转换sp */
    public static int px2sp(int pxValue) {
        final float fontScale = MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
    /** sp转换px */
    public static int sp2px(int spValue) {
        final float fontScale = MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    //==============================================================================================
    //====================================================================================文字=======
    //==============================================================================================

    /**
     * 获取文字基线的位置
     * @param p 画笔
     * @param centerY  文字的中心
     * @return
     */
    public static float getBaseline(float centerY,Paint p) {
        Paint.FontMetrics fontMetrics = p.getFontMetrics();
        return centerY+(fontMetrics.descent - fontMetrics.ascent) / 2 -fontMetrics.descent;
    }


    /**
     * 获取文字的宽度
     * @param mText
     * @param paint
     * @return
     */
    public static float getTextWidth(String mText,Paint paint) {

        //获取文字宽度dx
        Rect rectBounds = new Rect();
        paint.getTextBounds(mText, 0, mText.length(), rectBounds);
        return rectBounds.width();
    }

    /**
     * 获取文字的高度
     * @param mText
     * @param paint
     * @return
     */
    public static float getTextHeight(String mText,Paint paint) {

        //获取文字宽度dx
        Rect rectBounds = new Rect();
        paint.getTextBounds(mText, 0, mText.length(), rectBounds);
        return rectBounds.height();
    }

}
