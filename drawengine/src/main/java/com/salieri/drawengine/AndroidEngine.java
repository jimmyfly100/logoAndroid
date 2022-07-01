package com.salieri.drawengine;

import android.util.Log;

import com.salieri.baselib.core.CoreManager;
import com.salieri.baselib.core.ILogoEngine;
import com.salieri.baselib.task.FUNC;
import com.salieri.baselib.type.NUM;
import com.salieri.baselib.utils.BaseUtil;
import com.salieri.baselib.utils.PrefFuncUtil;
import com.salieri.baselib.utils.ToastUtil;

import java.util.Map;

public class AndroidEngine implements ILogoEngine {
    private double x = 0;
    private double y = 0;
    private double angle = 0;
    private ICanvas canvas = new DefaultCanvas();
    private boolean needDraw = true;

    public AndroidEngine(ICanvas canvas) {
        this.x = canvas.getDefaultX();
        this.y = canvas.getDefaultY();
        this.angle = canvas.getDefaultAngle();
        this.canvas = canvas;
        drawTurtle();
    }

    public void drawTurtle() {
        canvas.drawTurtle((float) x, (float) y, (float) angle);
    }

    @Override
    public void saveAllFunc() {
        for (Map.Entry<String, FUNC.Content> entry : CoreManager.getInstance().getFuncMap().entrySet()) {
            PrefFuncUtil.getInstance().putFunc(entry.getKey(), entry.getValue());
            ToastUtil.show("save Func: " + entry.getKey() + "   code: " + entry.getValue().code.value);

        }
    }

    @Override
    public void loadAllFunc() {
        for (Map.Entry<String, FUNC.Content> entry : PrefFuncUtil.getInstance().getAllFunc().entrySet()) {
            CoreManager.getInstance().registerFunc(entry.getKey(), entry.getValue());
            ToastUtil.show("load Func: " + entry.getKey() + "   code: " + entry.getValue().code.value);
        }
    }

    @Override
    public void FD(NUM value) {
        double lastX = x;
        double lastY = y;
        y -= value.value * Math.cos(angle);
        x += value.value * Math.sin(angle);
        if (needDraw) canvas.drawLine((float) lastX, (float) lastY, (float) x, (float) y);
        print("FD");
//        if (value.value >= 299) Log.d("Salieri timestamp", "" + System.currentTimeMillis());
    }

    @Override
    public void BK(NUM value) {
        double lastX = x;
        double lastY = y;
        y += value.value * Math.cos(angle);
        x -= value.value * Math.sin(angle);
        if (needDraw) canvas.drawLine((float) lastX, (float) lastY, (float) x, (float) y);
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

    @Override
    public void PU() {
        needDraw = false;
    }

    @Override
    public void PD() {
        needDraw = true;
    }

    @Override
    public void SETH(NUM value) {
        angle = BaseUtil.degree2Radius(value.value);
    }

    @Override
    public void SETXY(NUM x, NUM y) {
        this.x = canvas.getDefaultX() + x.value;
        this.y = canvas.getDefaultY() + y.value;
    }

    @Override
    public void PPT() {
        canvas.setXfermodeNormal();
    }

    @Override
    public void PX() {
        canvas.setXfermodeXOR();
    }


    private void print(String func) {
//        Log.d("LogoEngine", "Func " + func + " is called. Current state is: x = " + x + " y = " + y + " angle = " + angle);
    }
}
