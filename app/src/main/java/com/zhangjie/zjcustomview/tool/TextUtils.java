package com.zhangjie.zjcustomview.tool;

import android.graphics.Paint;
import android.graphics.Rect;

public class TextUtils {

    public static float getTextWidth(String mText,Paint paint) {

        //获取文字宽度dx
        Rect rectBounds = new Rect();
        paint.getTextBounds(mText, 0, mText.length(), rectBounds);
        return rectBounds.width();
    }


    public static float getTextHeight(String mText,Paint paint) {

        //获取文字宽度dx
        Rect rectBounds = new Rect();
        paint.getTextBounds(mText, 0, mText.length(), rectBounds);
        return rectBounds.height();
    }
}
