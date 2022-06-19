package com.salieri.drawengine;

import android.util.Log;

import com.salieri.baselib.core.ILogoEngine;
import com.salieri.baselib.type.NUM;
import com.salieri.baselib.utils.BaseUtil;
import com.salieri.baselib.utils.ToastUtil;

public class AndroidEngine implements ILogoEngine {
    private float x = 0;
    private float y = 0;
    private float angle = 0;
    private ICanvas canvas = new DefaultCanvas();

    public AndroidEngine(ICanvas canvas) {
        this.canvas = canvas;
    }

    public AndroidEngine(float x, float y, float angle, ICanvas canvas) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.canvas = canvas;
    }

    @Override
    public void FD(NUM value) {
        float lastX = x;
        float lastY = y;
        y -= value.value * Math.cos(angle);
        x += value.value * Math.sin(angle);
        canvas.drawLine(lastX, lastY, x, y);
        print("FD");
    }

    @Override
    public void BK(NUM value) {
        float lastX = x;
        float lastY = y;
        y += value.value * Math.cos(angle);
        x -= value.value * Math.sin(angle);
        canvas.drawLine(lastX, lastY, x, y);
        print("BK");
    }

    @Override
    public void RT(NUM value) {
        angle += BaseUtil.degree2Radius(value.value);
        print("RT");
    }

    @Override
    public void LT(NUM value) {
        angle -= BaseUtil.degree2Radius(value.value);
        print("LT");
    }

    @Override
    public void error(String msg) {
        ToastUtil.show(msg);
    }


    private void print(String func) {
        Log.d("LogoEngine", "Func " + func + " is called. Current state is: x = " + x + " y = " + y + " angle = " + angle);
    }
}
