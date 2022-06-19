package com.salieri.baselib.core;

import android.app.Application;

public class BaseApplication extends Application {
    public BaseApplication () {}
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}
