package com.zhangjie.zjcustomview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zhangjie.zjcustomview.R;
import com.zhangjie.zjcustomview.tool.MathUtil;

import java.util.ArrayList;
import java.util.List;

public class LockView extends View {

    private List<List<Point>> pointList = new ArrayList<>();


    private List<Point> foucusList = new ArrayList<>();

    private float width = 0;
    private float height = 0;
    private float poor = 0;//宽高之间的差值


    private float r = 0;//外圆的半径
    private float sr = 10;//小圆的半径
    private float RectR = 0;//矩形的半径

    private Paint normalPiant;//正常状态颜色
    private Paint changePiant;//正常状态颜色


    public LockView(Context context) {
        this(context, null);
    }

    public LockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }

    private void initPaint() {
        normalPiant = new Paint();
        normalPiant.setColor(getResources().getColor(R.color.colorPrimary));
        normalPiant.setStrokeWidth(4);
        normalPiant.setAntiAlias(true);
        normalPiant.setDither(true);
        normalPiant.setStyle(Paint.Style.STROKE);

        changePiant = new Paint();
        changePiant.setColor(getResources().getColor(R.color.colorAccent));
        changePiant.setStrokeWidth(4);
        changePiant.setAntiAlias(true);
        changePiant.setDither(true);
        changePiant.setStyle(Paint.Style.STROKE);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        poor = Math.abs(height - width);

        width = width > height ? height : width;
        height = width > height ? height : width;
        r = width / 6 - 40;
        RectR = width / 6;
        initPoint();


    }

    private void initPoint() {


        for (int i = 0; i < 3; i++) {

            List<Point> list = new ArrayList<>();


            for (int j = 0; j < 3; j++) {

                Point point = new Point(0, RectR + j * 2 * RectR, RectR + i * 2 * RectR + poor / 2, normalPiant);
                list.add(point);


            }


            pointList.add(list);

        }


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        for (int i = 0; i < pointList.size(); i++) {

            for (int j = 0; j < pointList.get(i).size(); j++) {

                Point point = pointList.get(i).get(j);

                canvas.drawCircle(point.getpX(), point.getpY(), r, point.getPaint());
                canvas.drawCircle(point.getpX(), point.getpY(), sr, point.getPaint());


            }
        }



        Log.e("======foucusList",foucusList.size()+"");
        for (int k = 0; k < foucusList.size(); k++) {



                try {
                    drawLine(foucusList.get(k),foucusList.get(k+1),canvas,changePiant);
                }catch (Exception e){
                    e.printStackTrace();
                }




        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                float x = event.getX();
                float y = event.getY();


                checkPointIn(event.getX(), event.getY());




                break;
            case MotionEvent.ACTION_UP:

                cancleCheckPoint();


                break;
        }


        return true;
    }

    private void myDrawLine() {

    }


    private class Point {
        private int colorState = 0;
        private float pX = 0;
        private float pY = 0;

        private Paint paint;

        public Paint getPaint() {
            return paint;
        }

        public void setPaint(Paint paint) {
            this.paint = paint;
        }

        public int getColorState() {
            return colorState;
        }

        public void setColorState(int colorState) {
            this.colorState = colorState;
        }

        public float getpX() {
            return pX;
        }

        public void setpX(float pX) {
            this.pX = pX;
        }

        public float getpY() {
            return pY;
        }

        public void setpY(float pY) {
            this.pY = pY;
        }

        public Point() {
        }


        public Point(int colorState, float pX, float pY, Paint paint) {
            this.colorState = colorState;
            this.pX = pX;
            this.pY = pY;
            this.paint = paint;
        }
    }


//    /**
//     * 获取按下的点
//     * @return 当前按下的点
//     */
//    private val point: Point?
//    get() {
//        for (i in 0..2) {
//            for (point in mPoints[i]) {
//                // for 循环九个点，判断手指位置是否在这个九个点里面
//                if (MathUtil.checkInRound(point!!.centerX.toFloat(), point.centerY.toFloat(),
//                        mDotRadius.toFloat(), mMovingX, mMovingY)) {
//                    return point
//                }
//            }
//        }
//        return null
//    }


    private void checkPointIn(float mMovingX, float mMovingY) {
        for (int i = 0; i < pointList.size(); i++) {

            for (int j = 0; j < pointList.get(i).size(); j++) {

                Point point = pointList.get(i).get(j);


                boolean in = MathUtil.checkInRound(point.getpX(), point.getpY(), r, mMovingX, mMovingY);




                if (in) {
                    point.setPaint(changePiant);
                    foucusList.add(point);

                    for(int k=0;k<foucusList.size();k++){

                        if ( foucusList.get(k).getpX()!=point.getpX()&&foucusList.get(k).getpY()!=point.getpY()){

                        }




                    }





                }


            }
        }

        invalidate();


    }

    private void removeHave() {

    }


    private void cancleCheckPoint() {
        for (int i = 0; i < pointList.size(); i++) {

            for (int j = 0; j < pointList.get(i).size(); j++) {

                Point point = pointList.get(i).get(j);


                point.setPaint(normalPiant);


            }
        }

        foucusList.clear();

        invalidate();
    }


    private void drawLine(Point s, Point e, Canvas canvas, Paint paint) {



        float pointDistance = (float) MathUtil.distance( s.getpX(),  s.getpY(),
                e.getpX(),  e.getpY());

        if (pointDistance>0){
            //Log.e("======pointDistance",pointDistance+"");
            float dx = e.getpX() - s.getpX();
            float dy = e.getpY() - s.getpY();



            float sina=dy/pointDistance;

            float cosia=dx/pointDistance;


            float x=cosia*RectR;
            float y=sina*RectR;


            float startx=s.getpX()+x;
            float starty=s.getpY()+y;

            float endx=e.getpX()-x;
            float endy=e.getpY()-y;


//            Log.e("======px",s.getpX()+"");
//            Log.e("======py",s.getpY()+"");
//
//
//            Log.e("======ex",s.getpX()+"");
//            Log.e("======ey",s.getpY()+"");
//        Log.e("======startx",startx+"");
//        Log.e("======starty",starty+"");
//        Log.e("======endx",endx+"");
//        Log.e("======endy",endy+"");

            canvas.drawLine(startx,  starty, endx, endy, paint);
        }


    }



    private void drawLine2(Point s, Point e, Canvas canvas, Paint paint) {




        canvas.drawLine(s.getpX(),  s.getpY(), e.getpX(), e.getpY(), paint);
    }




}
