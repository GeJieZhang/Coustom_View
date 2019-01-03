package com.zhangjie.zjcustomview;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public  class MyApplication extends Application {
    private static MyApplication sInstance = null;


    public MyApplication() {
        sInstance = this;
    }



    public static MyApplication getInstance() {
        if (sInstance == null) {
            sInstance = new MyApplication();
        }
        return sInstance;
    }

    public static Context getContext() {
        return sInstance.getApplicationContext();
    }
}
