package com.zhangjie.zjcustomview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ChangeTab extends LinearLayout {
    public ChangeTab(Context context) {
        this(context,null);
    }

    public ChangeTab(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ChangeTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }





    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);





    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        
    }
}
