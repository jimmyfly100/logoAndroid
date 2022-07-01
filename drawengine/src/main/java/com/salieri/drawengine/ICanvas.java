package com.salieri.drawengine;

public interface ICanvas {
    void drawLine(float fromX, float fromY, float toX, float toY);
    void drawTurtle(float x, float y, float angle);
    float getDefaultX();
    float getDefaultY();
    float getDefaultAngle();
    void setXfermodeXOR();
    void setXfermodeNormal();
}
