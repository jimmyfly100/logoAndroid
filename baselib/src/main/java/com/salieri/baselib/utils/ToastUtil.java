package com.salieri.baselib.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.widget.Toast;

import com.salieri.baselib.core.BaseApplication;

public class ToastUtil {
    public static void show(String text) {
        Toast.makeText(BaseApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }
}
