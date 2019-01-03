package com.zhangjie.zjcustomview.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhangjie.zjcustomview.R;

public class ZLoadingButton extends FrameLayout {

    private Button button;

    private ProgressBar progressBar;

    private Context context;

    private  int width=0;
    private  int height=0;

    public ZLoadingButton(@NonNull Context context) {
        this(context,null);
    }

    public ZLoadingButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZLoadingButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        this.context=context;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=MeasureSpec.getSize(widthMeasureSpec);
        height=MeasureSpec.getSize(heightMeasureSpec);
        button=new Button(context);
        progressBar=new ProgressBar(context);
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(width,height);


        button.setLayoutParams(layoutParams);
        //button.setBackgroundResource(R.drawable.shape_red_all);
        button.setTextColor(getResources().getColor(R.color.colorWidth));
        button.setText( Builder.params.text);



        progressBar.setLayoutParams(layoutParams);
        FrameLayout.LayoutParams layoutParams1=new FrameLayout.LayoutParams(width,height);
        FrameLayout.LayoutParams layoutParams2=new FrameLayout.LayoutParams(width/2,height/2);
        ImageView imageView =new ImageView(context);
        imageView.setBackgroundResource(R.mipmap.ic_launcher);
        imageView.setLayoutParams(layoutParams1);

        ImageView imageView2 =new ImageView(context);
        imageView2.setBackgroundResource(R.mipmap.gift1);
        imageView2.setLayoutParams(layoutParams2);





        addView(imageView);
        addView(imageView2);
//        addView(button);


    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


//        View view1=getChildAt(0);
//        View view2=getChildAt(1);
//        view1.layout(0,0,width,height);
//        view2.layout(0,0,width/2,height);


    }

    public static Builder getInstance(Context con){



        return new Builder(con);
    }




    public static class Builder{

        public static Params params;
        public Context context;

        public Builder(Context con) {

            this.context=con;
            params=new Params();



        }



        public Builder setText(String str){
            params.text=str;
            return this;
        }

        public Params getParams() {
            return params;
        }

        public ZLoadingButton builder(){

            return new ZLoadingButton(context);

        }

    }


    private static class Params{

        private String text="";

    }






}
