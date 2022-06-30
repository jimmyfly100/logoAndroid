package com.salieri.baselib.core;

import android.util.Log;

import com.salieri.baselib.type.NUM;
import com.salieri.baselib.utils.BaseUtil;
import com.salieri.baselib.utils.ToastUtil;

public class DefaultEngine implements ILogoEngine {
    private double x = 0;
    private double y = 0;
    private float angle = 0;
    @Override
    public void FD(NUM value) {
        y += value.getValue() * Math.cos(angle);
        x += value.getValue() * Math.sin(angle);
        print("FD");
    }

    @Override
    public void BK(NUM value) {
        y -= value.getValue() * Math.cos(angle);
        x -= value.getValue() * Math.sin(angle);
        print("BK");
    }

    @Override
    public void RT(NUM value) {
        angle += BaseUtil.degree2Radius(value.getValue());
        print("RT");
    }

    @Override
    public void LT(NUM value) {
        angle -= BaseUtil.degree2Radius(value.getValue());
        print("LT");
    }

    @Override
    public void error(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void PU() {

    }

    @Override
    public void PD() {

    }

    @Override
    public void drawTurtle() {

    }

    @Override
    public void saveAllFunc() {

    }

    @Override
    public void loadAllFunc() {

    }

    private void print(String func) {
        Log.d("LogoEngine", "Func " + func + " is called. Current state is: x = " + x + " y = " + y + " angle = " + angle);
    }

}
