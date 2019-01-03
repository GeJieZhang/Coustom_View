package com.zhangjie.zjcustomview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TagLayout extends ViewGroup {


    private List<List<ViewList>> listView = new ArrayList<>();

    private float width=0;
    private float height=0;


    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width=MeasureSpec.getSize(widthMeasureSpec);

        height=0;

        int parentPaddingLeft=getPaddingLeft();
        int parentPaddingTop=getPaddingTop();
        int parentPaddingBottom=getPaddingBottom();
        int count = getChildCount();



        float linWidth=0;
        List<ViewList> lists=new ArrayList<>();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            float childWidth = childView.getWidth();
            float childHeight = childView.getHeight();
            float childPaddingLeft =childView.getPaddingLeft();
            float childPaddingRight = childView.getPaddingRight();
            float childPaddingTop =childView.getPaddingTop();
            float childPaddingBottom =childView.getPaddingBottom();

            linWidth+=childWidth+childPaddingLeft+childPaddingRight;

            if (linWidth>width){
                linWidth=childWidth+childPaddingLeft+childPaddingRight;
                listView.add(lists);
                lists.clear();

                height+=childHeight+childPaddingTop+childPaddingBottom;

            }else {



                ViewList viewList=new ViewList();
                viewList.setLeft(parentPaddingLeft);
                viewList.setTop(parentPaddingTop+height);
                viewList.setRight(linWidth-childPaddingRight);
                viewList.setBottom(parentPaddingTop+height+childHeight+parentPaddingBottom);
                viewList.setView(childView);
                lists.add(viewList);

                if (i==(count-1)){
                    linWidth=childWidth+childPaddingLeft+childPaddingRight;
                    listView.add(lists);
                    //lists.clear();

                    height+=childHeight+childPaddingTop+childPaddingBottom;
                }
            }



        }


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


        List<List<ViewList>> listView=this.listView;

        List<ViewList> c=listView.get(0);
       for (int i=0;i<listView.size();i++){


           for (int j=0;j<listView.get(i).size();j++){
              View view=listView.get(i).get(j).getView();

               float left=listView.get(i).get(j).getLeft();
               float top=listView.get(i).get(j).getTop();
               float right=listView.get(i).get(j).getRight();
               float bottom=listView.get(i).get(j).getBottom();
               view.layout((int)left,(int)top,(int)right,(int)bottom);


           }


       }



    }


    public class ViewList {
        private View view;
        private float left;
        private float top;
        private float right;
        private float bottom;

        public ViewList(View view, float left, float top, float right, float bottom) {
            this.view = view;
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        public ViewList() {
        }

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }

        public float getLeft() {
            return left;
        }

        public void setLeft(float left) {
            this.left = left;
        }

        public float getTop() {
            return top;
        }

        public void setTop(float top) {
            this.top = top;
        }

        public float getRight() {
            return right;
        }

        public void setRight(float right) {
            this.right = right;
        }

        public float getBottom() {
            return bottom;
        }

        public void setBottom(float bottom) {
            this.bottom = bottom;
        }
    }


}
