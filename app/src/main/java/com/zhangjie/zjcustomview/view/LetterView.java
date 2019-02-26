package com.zhangjie.zjcustomview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zhangjie.zjcustomview.R;
import com.zhangjie.zjcustomview.tool.CoustomViewTools;

/**
 * 右侧字母选择栏
 */
public class LetterView extends View {


    private float width = 0;
    private float height = 0;
    private float itemHeight = 0;
    private Paint normalPaint = null;
    private Paint changePaint = null;

    private String getLetter = "";


    private String letter[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    public LetterView(Context context) {
        this(context, null);
    }

    public LetterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        normalPaint = new Paint();
        normalPaint.setTextSize(20);
        normalPaint.setColor(getResources().getColor(R.color.colorPrimary));
        normalPaint.setAntiAlias(true);//抗锯齿
        normalPaint.setDither(true);//防抖动

        changePaint = new Paint();
        changePaint.setTextSize(20);
        changePaint.setColor(getResources().getColor(R.color.colorAccent));
        changePaint.setAntiAlias(true);//抗锯齿
        changePaint.setDither(true);//防抖动
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getPaddingLeft() + getPaddingRight() + CoustomViewTools.getTextWidth("A", normalPaint);

        //height=(CoustomViewTools.getTextHeight("A",normalPaint)+getPaddingTop()+getPaddingBottom())*letter.length;
        height = MeasureSpec.getSize(heightMeasureSpec);
        itemHeight = CoustomViewTools.getTextHeight("A", normalPaint) + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension((int) width, (int) height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < letter.length; i++) {
            float centerY = i * itemHeight + itemHeight / 2;

            float startX = width / 2 - CoustomViewTools.getTextWidth(letter[i], normalPaint) / 2;

            if (letter[i].equals(getLetter)){
                canvas.drawText(letter[i], startX, CoustomViewTools.getBaseline(centerY, changePaint), changePaint);
            }else {
                canvas.drawText(letter[i], startX, CoustomViewTools.getBaseline(centerY, normalPaint), normalPaint);
            }

        }


    }

    //private int nowPositon=0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                float y =event.getY();

                if (y>height||y<0){

                  break;
                }
                int i = (int) (y / itemHeight);

                if (i>=letter.length|i<0){
                    break;
                }


                getLetter = letter[i];

                if (onGetLetterListener!=null){
                    onGetLetterListener.getLetter(getLetter);
                }
                invalidate();


                break;
        }


        return true;


    }

    private OnGetLetterListener onGetLetterListener;

    public interface OnGetLetterListener {
        String getLetter(String string);
    }


    public void OnGetLetterListener(OnGetLetterListener onGetLetterListener) {

        this.onGetLetterListener = onGetLetterListener;


    }
}
