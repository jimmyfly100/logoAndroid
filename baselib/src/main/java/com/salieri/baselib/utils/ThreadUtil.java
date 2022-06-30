package com.salieri.baselib.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadUtil {
    private static final ExecutorService pool = Executors.newFixedThreadPool(1, Executors.defaultThreadFactory());
    private static final ExecutorService cacheThread = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
    private static final Handler handler = new Handler(Looper.getMainLooper());

    public static void runOnChildThread(Runnable runnable) {
        pool.submit(runnable);
    }

    public static void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }
}
