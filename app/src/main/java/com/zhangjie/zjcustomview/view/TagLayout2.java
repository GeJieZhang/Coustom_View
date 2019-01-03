package com.zhangjie.zjcustomview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TagLayout2 extends ViewGroup {


    int width = 0;
    int height = 0;


    public TagLayout2(Context context) {
        this(context, null);
    }

    public TagLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        int count = getChildCount();


        for (int i = 0; i < count; i++) {

            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);


        }


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


        int count = getChildCount();


        int parentPaddingLeft = getPaddingLeft();
        int parentPaddingRight = getPaddingRight();
        int parentPaddingTop = getPaddingTop();
        int parentPaddingBottom = getPaddingBottom();
        int linWidth = parentPaddingLeft + parentPaddingRight;

        int left = parentPaddingLeft;
        int top = parentPaddingTop;

        for (int i = 0; i < count; i++) {

            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            int childPaddingLeft = childView.getPaddingLeft();
            int childPaddingRight = childView.getPaddingRight();
            int childPaddingTop = childView.getPaddingTop();
            int childPaddingBottom = childView.getPaddingBottom();
            linWidth += childWidth;


            if (i==6){
                Log.e("linWidth=", linWidth + "");
            }
            if (linWidth > width) {
                Log.e("i=", i + "");
                top += childHeight;
                left = parentPaddingLeft;
                linWidth = parentPaddingLeft + parentPaddingRight+childWidth;
            }

//            Log.e("childWidth=", childWidth + "");
//
//            if ((left + childWidth) == 552) {
//                Log.e("linWidth=", linWidth + "");
//                Log.e("childw=", childWidth + "");
//                Log.e("i=", i + "");
//            }


            childView.layout(left, top, left + childWidth, top + childHeight);
            left += childWidth;


        }


    }


}
