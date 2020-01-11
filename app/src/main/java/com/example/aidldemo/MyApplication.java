package com.example.aidldemo;

import android.app.Application;

import com.example.aidldemo.utils.IPCUtils;

public class MyApplication extends Application {

    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        IPCUtils.getInstance().bindService();
    }

    public static Application getApplication(){
        return mApplication;
    }
}
