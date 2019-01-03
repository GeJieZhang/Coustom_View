package com.zhangjie.zjcustomview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zhangjie.zjcustomview.R;

import javax.security.auth.login.LoginException;

public class PointView extends View {

    private PointF startPoint,endPoint;
    private float startPointR=10;

    private float endPointR=15;

    private Paint circlePaint,linPoint,bluePaint;



    public PointView(Context context) {
        this(context,null);
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        initPoint();

        initPaint();
    }

    private void initPaint() {
        circlePaint=new Paint();
        circlePaint.setDither(true);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(getResources().getColor(R.color.colorAccent));
        circlePaint.setStyle(Paint.Style.FILL);


        bluePaint=new Paint();
        bluePaint.setDither(true);
        bluePaint.setAntiAlias(true);
        bluePaint.setColor(getResources().getColor(R.color.colorPrimary));
        bluePaint.setStyle(Paint.Style.FILL);




        linPoint=new Paint();
        linPoint.setDither(true);
        linPoint.setAntiAlias(true);
        linPoint.setColor(getResources().getColor(R.color.colorAccent));
        linPoint.setStyle(Paint.Style.STROKE);
    }




    private void initPoint() {
        startPoint=new PointF();
        endPoint=new PointF();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);



        startPoint.x=MeasureSpec.getSize(widthMeasureSpec)/2;
        startPoint.y=MeasureSpec.getSize(heightMeasureSpec)/2;


        endPoint.x=MeasureSpec.getSize(widthMeasureSpec)/2;
        endPoint.y=MeasureSpec.getSize(heightMeasureSpec)/2;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                startPoint.x=event.getX();
                startPoint.y=event.getY();
                endPoint.x=event.getX();
                endPoint.y=event.getY();

                break;

            case MotionEvent.ACTION_MOVE:


                endPoint.x=event.getX();
                endPoint.y=event.getY();





                break;
            case MotionEvent.ACTION_UP:





                break;
        }

        invalidate();


        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        float dx=endPoint.x-startPoint.x;
//        float dy=endPoint.y-endPoint.y;
//
//        float tanA=dy/dx;
        float dy = (endPoint.y-startPoint.y);
        float dx = (endPoint.x-startPoint.x);
        float tanA = dy/dx;
        Log.e("tanA=",""+tanA);
        float arcTanA = (float) Math.atan(tanA);
        float sinA= (float) Math.sin(arcTanA);
        float cosA= (float) Math.cos(arcTanA);



        PointF  p0=new PointF(startPoint.x+startPointR*sinA,startPoint.y-startPointR*cosA);



       PointF  pCenter=new PointF((startPoint.x+endPoint.x)/2,(startPoint.y+endPoint.y)/2);

        PointF  p1=new PointF(endPoint.x+endPointR*sinA,endPoint.y-endPointR*cosA);


        PointF  p2=new PointF(endPoint.x-endPointR*sinA,endPoint.y+endPointR*cosA);

        PointF  p3=new PointF(startPoint.x-startPointR*sinA,startPoint.y+startPointR*cosA);





        Path path=new Path();
        path.moveTo(p0.x,p0.y);
        path.quadTo(pCenter.x,pCenter.y,p1.x,p1.y);
        path.lineTo(p2.x,p2.y);
        path.quadTo(pCenter.x,pCenter.y,p3.x,p3.y);
        path.close();
        canvas.drawPath(path,circlePaint);

        canvas.drawCircle(startPoint.x,startPoint.y,startPointR,circlePaint);
        canvas.drawCircle(endPoint.x,endPoint.y,endPointR,circlePaint);


        canvas.drawCircle(p0.x,p0.y,2,bluePaint);
        canvas.drawCircle(pCenter.x,pCenter.y,2,bluePaint);
        canvas.drawCircle(p1.x,p1.y,2,bluePaint);
        canvas.drawCircle(p2.x,p2.y,2,bluePaint);
        canvas.drawCircle(p3.x,p3.y,2,bluePaint);
    }
}
